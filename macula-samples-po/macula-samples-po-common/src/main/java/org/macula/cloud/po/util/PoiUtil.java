package org.macula.cloud.po.util;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * excel读写工具类 */
@Slf4j
public class PoiUtil {
    private final static String EXCEL2003 = "xls";
    private final static String EXCEL2007 = "xlsx";


    /**
     * 物料信息上传excel文件转化实体类
     * @param file
     * @param cls
     * @return
     * @throws Exception
     */
    public static <T> List<T> readMaterialExcel(MultipartFile file, Class<T> cls){
        try {
            return readExcel(file, cls);
        }catch(Exception e) {
            throw new RuntimeException("导入失败！请注意Excel单元格数据格式！\r\n"+e.getMessage());
        }
    }

    /**
     * 上传excel文件转化实体类
     * @param file
     * @param cls
     * @return
     * @throws Exception
     */
    public static <T> List<T> readExcel(MultipartFile file, Class<T> cls) {
        String fileName = file.getOriginalFilename();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new CustomException("上传文件格式不正确:" + fileName) ;
        }
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return readExcel(is, cls, fileName);
    }

    /**
     * 本地加载excel文件转化实体类
     * @param filePath
     * @param cls
     * @return
     * @throws Exception
     */
    public static <T> List<T> readExcel(String filePath, Class<T> cls) throws Exception {
        File file = new File(filePath);
        String fileName = file.getName();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new CustomException("读取文件格式不正确:" + fileName) ;
        }
        InputStream is = new FileInputStream(filePath);
        return readExcel(is, cls, fileName);
    }


    /**
     * excel文件流转化实体类
     * @param is
     * @param cls
     * @param fileName
     * @return
     */
    public static <T> List<T> readExcel(InputStream is, Class<T> cls, String fileName) {
        List<T> dataList = new ArrayList<>();
        Workbook workbook = null;
        try {

            if (fileName.endsWith(EXCEL2007)) {
//                FileInputStream is = new FileInputStream(new File(path));
                workbook = new XSSFWorkbook(is);
            }
            if (fileName.endsWith(EXCEL2003)) {
//                FileInputStream is = new FileInputStream(new File(path));
                workbook = new HSSFWorkbook(is);
            }
            if (workbook != null) {
                //类映射  注解 value-->bean columns
                Map<String, List<Field>> classMap = new HashMap<>();
                List<Field> fields = Stream.of(cls.getDeclaredFields()).collect(Collectors.toList());
                fields.forEach(
                        field -> {
                            ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                            if (annotation != null) {
                                String value = annotation.value();
                                if (StringUtils.isBlank(value)) {
                                    return;//return起到的作用和continue是相同的 语法
                                }
                                if (!classMap.containsKey(value)) {
                                    classMap.put(value, new ArrayList<>());
                                }
                                field.setAccessible(true);
                                classMap.get(value).add(field);
                            }
                        }
                );
                //索引-->columns
                Map<Integer, List<Field>> reflectionMap = new HashMap<>(16);
                //默认读取第一个sheet
                Sheet sheet = workbook.getSheetAt(0);

                boolean firstRow = true;
                for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    //首行  提取注解
                    if (firstRow) {
                        for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                            Cell cell = row.getCell(j);
                            String cellValue = getCellValue(cell);
                            if (classMap.containsKey(cellValue)) {
                                reflectionMap.put(j, classMap.get(cellValue));
                            }
                        }
                        firstRow = false;
                    } else {
                        //忽略空白行
                        if (row == null) {
                            continue;
                        }
                        //判断是否为空白行
                        Boolean allBlank = PoiUtil.isEmptyRow(row, classMap.size());
                        if (allBlank) {
                            continue;
                        }
                        T t = cls.newInstance();
                        // for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                        for (int j = 0; j < classMap.size(); j++) {
                            if (reflectionMap.containsKey(j)) {
                                Cell cell = row.getCell(j);
                                String cellValue = getCellValue(cell);
                                if (StringUtils.isNotBlank(cellValue)) {
                                    allBlank = false;
                                }
                                List<Field> fieldList = reflectionMap.get(j);
                                for (Field x : fieldList) {
                                    ExcelColumn annotation = x.getAnnotation(ExcelColumn.class);
                                    String columnName = annotation.value();
                                    // 空白列需要根据注解抛出异常
                                    if (StringUtils.isBlank(cellValue)) {
                                        boolean required = annotation.required();
                                        columnName = annotation.value();
                                        if (required) {
                                            throw new Exception(String.format("%s不能为空，位于第%s行%s列",annotation.value(), i + 1, j + 1));
                                        }
                                    }
                                    try {
                                        handleField(t, cellValue, x);
                                    } catch (Exception e) {
                                        log.error(String.format("reflect field:%s value:%s exception!", columnName, cellValue), e);
                                        throw new Exception(String.format("字段:%s 转换异常 转化值为%s，位于第%s行%s列", columnName,cellValue, i + 1, j + 1));
                                    }
                                }
                            }
                        }
                        if (!allBlank) {
                            dataList.add(t);
                        } else {
                            log.warn(String.format("row:%s is blank ignore!", i + 1));
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(String.format("parse excel exception!"), e);
            throw new CustomException(e.getMessage());
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    log.error(String.format("parse excel exception!"), e);
                }
            }
        }
        return dataList;
    }

    private static <T> void handleField(T t, String value, Field field) throws Exception {
        Class<?> type = field.getType();
        if (type == null || type == void.class || StringUtils.isBlank(value)) {
            return;
        }
        if (type == Object.class) {
            field.set(t, value);
            //数字类型
        } else if (type.getSuperclass() == null || type.getSuperclass() == Number.class) {
            if (type == int.class || type == Integer.class) {
                field.set(t, NumberUtils.toInt(value));
            } else if (type == long.class || type == Long.class) {
                field.set(t, NumberUtils.toLong(value));
            } else if (type == byte.class || type == Byte.class) {
                field.set(t, NumberUtils.toByte(value));
            } else if (type == short.class || type == Short.class) {
                field.set(t, NumberUtils.toShort(value));
            } else if (type == double.class || type == Double.class) {
                field.set(t, NumberUtils.toDouble(value));
            } else if (type == float.class || type == Float.class) {
                field.set(t, NumberUtils.toFloat(value));
            } else if (type == char.class || type == Character.class) {
                field.set(t, CharUtils.toChar(value));
            } else if (type == boolean.class) {
                field.set(t, BooleanUtils.toBoolean(value));
            } else if (type == BigDecimal.class) {
                field.set(t, new BigDecimal(value));
            }
        } else if (type == Boolean.class) {
            field.set(t, BooleanUtils.toBoolean(value));
        } else if (type == Date.class) {
            //
            Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            Pattern compile = Pattern.compile("\\d{4}/\\d{2}/\\d{2}");
            Pattern newest = Pattern.compile("\\d{4}\\d{2}\\d{2} \\d{2}:\\d{2}:\\d{2}");
            Matcher matcher = pattern.matcher(value);
            if( matcher.matches() ){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                field.set(t, sdf.parse(value));
            } else if (compile.matcher(value).matches()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                field.set(t, sdf.parse(value));
            } else if (newest.matcher(value).matches()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                field.set(t, sdf.parse(value));
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
                field.set(t, sdf.parse(value));
            }
        } else if (type == String.class) {
            field.set(t, value);
        } else {
            Constructor<?> constructor = type.getConstructor(String.class);
            field.set(t, constructor.newInstance(value));
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                return HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();
            } else {
                return new BigDecimal(cell.getNumericCellValue()).toString();
            }
        } else if (cell.getCellType() == CellType.STRING) {
            return StringUtils.trimToEmpty(cell.getStringCellValue());
        } else if (cell.getCellType() == CellType.FORMULA) {
            return StringUtils.trimToEmpty(cell.getCellFormula());
        } else if (cell.getCellType() == CellType.BLANK) {
            return "";
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.ERROR) {
            return "ERROR";
        } else {
            return cell.toString().trim();
        }

    }


    /**
     * 浏览器下载excel
     * @param fileName
     * @param wb
     * @param response
     */

    private static  void  buildExcelDocument(String fileName, Workbook wb, HttpServletResponse response){
        try {
            response.reset();
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            response.flushBuffer();
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成excel文件
     *
     * @param path 生成excel路径
     * @param wb
     */
    private static void buildExcelFile(String path, Workbook wb) {

        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {
            wb.write(new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 橱柜特殊单导出物料详情Excel
     */
    public static <T> void materialWriteExcel(HttpServletResponse response, List<T> dataList, Class<T> cls, String name) {
        Field[] fields = cls.getDeclaredFields();
        List<Field> fieldList = Arrays.stream(fields)
                .filter(field -> {
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (annotation != null && annotation.col() > 0) {
                        field.setAccessible(true);
                        return true;
                    }
                    return false;
                }).sorted(Comparator.comparing(field -> {
                    int col = 0;
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (annotation != null) {
                        col = annotation.col();
                    }
                    return col;
                })).collect(Collectors.toList());

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Sheet1");
        AtomicInteger ai = new AtomicInteger();
        {
            Row row = sheet.createRow(ai.getAndIncrement());
            AtomicInteger aj = new AtomicInteger();
            //写入头部
            fieldList.forEach(field -> {
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                String columnName = "";
                if (annotation != null) {
                    columnName = annotation.value();
                }
                Cell cell = row.createCell(aj.getAndIncrement());
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                Font font = wb.createFont();
                font.setBold(true);
                cellStyle.setFont(font);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(columnName);

            });
        }
        // 设置导出Excel的各列单元格的数据格式
        for(int i=1;i<fieldList.size()+1;i++) {
            if(i==10||i==13||i==19||i==20){
                /** 设置默认[第10列有效期、13列交货期、19列生效日期、20列失效日期]格式为日期格式yyyy-MM-dd */
                sheet.setDefaultColumnStyle(i - 1, createCss(wb, "yyyy-MM-dd HH:mm:ss"));
                sheet.setColumnWidth(i-1,20 * 256);
            }
            else if(i==11) {
                /** 设置默认[第11列单价]格式为0.00 */
                sheet.setDefaultColumnStyle(i - 1, createCss(wb, "#,#0.00"));
            }
            else {/** 设置默认其余列格式为文本格式 */
                sheet.setDefaultColumnStyle(i - 1, createCss(wb, "@"));
                if(i==2||i==5||i==6||i==22||i==23) { //编码类和地址的单元格宽度设置
                    sheet.setColumnWidth(i-1,15 * 256);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(dataList)) {
            dataList.forEach(t -> {
                Row row1 = sheet.createRow(ai.getAndIncrement());

                AtomicInteger aj = new AtomicInteger();
                fieldList.forEach(field -> {
                    Class<?> type = field.getType();
                    Object value = "";
                    try {
                        value = field.get(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Cell cell = row1.createCell(aj.getAndIncrement());

                    if (value != null) {
                        try {
                            // 设置导出Excel的各列单元格的数据格式(数据不为空的行)
                            if (aj.get() == 10 || aj.get() == 13 || aj.get() == 19 || aj.get() == 20) {
                                /** 设置默认[第10列有效期、13列交货期、19列生效日期、20列失效日期]格式为日期格式yyyy-MM-dd*/
                                cell.setCellStyle(createCss(wb, "yyyy-MM-dd HH:mm:ss"));
                                String format = DateFormatUtils.format((Date) value, "yyyy-MM-dd HH:mm:ss");
                                cell.setCellValue(format);
                            } else if (aj.get() == 11) { /** 设置默认[第11列单价]格式为0.00 */
                                cell.setCellStyle(createCss(wb, "#,#0.00"));
                                cell.setCellValue((double) value);
                            } else { /** 设置默认其余列格式为文本格式 */
                                cell.setCellStyle(createCss(wb, "@"));
                                cell.setCellValue(value.toString());
                            }
                        }catch (Exception e){
                            throw new RuntimeException("后台数据格式转换出错：\r"+e.getMessage());
                        }
                    }
                });

            });
        }
        //冻结窗格
        wb.getSheet("Sheet1").createFreezePane(0, 1, 0, 1);
        String excelName = name + DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmss")+".xlsx";
        //浏览器下载excel
        buildExcelDocument(excelName, wb, response);
    }

    /**
     * 创建格式
     */
    public static CellStyle createCss(Workbook wb, String format){
        CellStyle cssCell = wb.createCellStyle();
        DataFormat textFormat = wb.createDataFormat();
        cssCell.setDataFormat(textFormat.getFormat(format));
        cssCell.setAlignment(HorizontalAlignment.CENTER);
        return cssCell;
    }

    public static <T> void writeExcel(HttpServletResponse response, List<T> dataList, Class<T> cls, String name) {
        Field[] fields = cls.getDeclaredFields();
        List<Field> fieldList = Arrays.stream(fields)
                .filter(field -> {
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (annotation != null && annotation.col() > 0) {
                        field.setAccessible(true);
                        return true;
                    }
                    return false;
                }).sorted(Comparator.comparing(field -> {
                    int col = 0;
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (annotation != null) {
                        col = annotation.col();
                    }
                    return col;
                })).collect(Collectors.toList());

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Sheet1");
        AtomicInteger ai = new AtomicInteger();
        {
            Row row = sheet.createRow(ai.getAndIncrement());
            AtomicInteger aj = new AtomicInteger();
            //写入头部
            fieldList.forEach(field -> {
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                String columnName = "";
                if (annotation != null) {
                    columnName = annotation.value();
                    if (field.getType() == Date.class) {
                        sheet.setColumnWidth(aj.get(), 20 * 256);
                    }
                }
                Cell cell = row.createCell(aj.getAndIncrement());

                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                Font font = wb.createFont();
                font.setBold(true);
                cellStyle.setFont(font);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(columnName);

            });
        }
        if (CollectionUtils.isNotEmpty(dataList)) {
            //保留2位小数CellStyle
            CellStyle priceType = wb.createCellStyle();
            DataFormat dataFormat = wb.createDataFormat();
            short priceShort = dataFormat.getFormat("#,#0.00");
            priceType.setDataFormat(priceShort);
            //结束

            dataList.forEach(t -> {
                Row row1 = sheet.createRow(ai.getAndIncrement());
                AtomicInteger aj = new AtomicInteger();
                fieldList.forEach(field -> {
                    Class<?> type = field.getType();
                    Object value = "";
                    try {
                        value = field.get(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Cell cell = row1.createCell(aj.getAndIncrement());
                    if (value != null) {
                        if (type == Date.class) {

                            JsonFormat jsonFormat = field.getAnnotation(JsonFormat.class);
                            String pattern = "yyyy-MM-dd HH:mm:ss";
                            if (jsonFormat != null) {
                                pattern = jsonFormat.pattern();
                            }
                            String format = DateFormatUtils.format((Date) value, pattern);
                            cell.setCellValue(format);
                        } else if (type.getSuperclass() == Number.class) {
                            cell.setCellType(CellType.NUMERIC);

                            if (type == int.class || type == Integer.class) {
                                cell.setCellValue((int) value);
                            } else if (type == long.class || type == Long.class) {
                                cell.setCellValue((long) value);
                            } else if (type == byte.class || type == Byte.class) {
                                cell.setCellValue((byte) value);
                            } else if (type == short.class || type == Short.class) {
                                cell.setCellValue((short) value);
                            } else if (type == double.class || type == Double.class) {
                                cell.setCellValue((double) value);
                                cell.setCellStyle(priceType);
                            } else if (type == float.class || type == Float.class) {
                                cell.setCellValue((float) value);
                            } else if (type == char.class || type == Character.class) {
                                cell.setCellValue((char) value);
                            } else if (type == boolean.class) {
                                cell.setCellValue((boolean) value);
                            } else if (type == BigDecimal.class) {
                                cell.setCellValue(value.toString());
                            }

                        } else {
                            cell.setCellValue(value.toString());
                        }
                    }
                });
            });
        }
        //冻结窗格
        wb.getSheet("Sheet1").createFreezePane(0, 1, 0, 1);
        String excelName = name + DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmss")+".xlsx";
        //浏览器下载excel
        buildExcelDocument(excelName, wb, response);
    }

    public static Boolean isEmptyRow(Row row, int num) {
        Boolean re = true;
        for (int j = 0; j < num; j++) {
            Cell cell = row.getCell(j);
            String cellValue = getCellValue(cell);
            if (StringUtils.isNotBlank(cellValue)) {
                re = false;
                break;
            }
        }
        return re;
    }
}
