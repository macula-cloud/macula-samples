package org.macula.cloud.po.util;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {

    /**
     * 表头
     * @return 表头读取/写入
     */
    String value() default "";

    /**
     * 列 0 导出时不导出
     * @return 0
     */
    int col() default 0;

    /**
     * 是否为必填字段
     * @return true
     */
    boolean required() default true;
}