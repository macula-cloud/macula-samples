package org.macula.cloud.po.gbss.vo;

import java.math.BigDecimal;

/**
 * <p>
 * <b>ProductPriceInfo</b> 是产品价格对象
 * </p>
 *
 
 
 
 *
 */
public class ProductPriceInfo {
	private String rowCode;

	private String Id;//主要用于界面顺序的显示

	private String currencyCode;

	private BigDecimal leastQty;

	private String priceGroupType;

	private String productCode;

	private BigDecimal productPoint;

	private BigDecimal productPrice;

	private BigDecimal productRetailPrice;

	private String productSaleType;

	private String productFullName;

	private String productShortName;

	private String listFullName;

	private String unitMeasure;

	private BigDecimal unitWeight;

	private String productType;
	/**产品限时可销售库存*/
	private BigDecimal thisSoQty;

	private BigDecimal productPriceMoney;

	private BigDecimal productTotalMoney;//总金额

	private String productAttr;//产品属性

	private String showProductPrice;

	private String showTotalPoint;

	private BigDecimal poAppQty;

	private int directSale = 0;
	/**判断产品是否需要跟踪单品码*/
	private boolean isNeedEpc;
	/**条形码*/
	private String productEanCode;
	/**GBSS显示包装颜色**/
	private String gbssShowColor;
	/**GBSS显示产品顺序**/
	private BigDecimal gbssShowOrder;

	private boolean isEpc;

	/***包装箱内数量***/
	private BigDecimal packageQty;

	/**
	 * add by bjc_yangjc "计划销售" 2015-9-14
	 * 产品限购标志
	 */
	private String stockLimitFlag = "N";

	public String getStockLimitFlag() {
		return stockLimitFlag;
	}

	public void setStockLimitFlag(String stockLimitFlag) {
		this.stockLimitFlag = stockLimitFlag;
	}

	public BigDecimal getPoAppQty() {
		return poAppQty;
	}

	public void setPoAppQty(BigDecimal poAppQty) {
		this.poAppQty = poAppQty;
	}

	public String getShowProductPrice() {
		return showProductPrice;
	}

	public void setShowProductPrice(String showProductPrice) {
		this.showProductPrice = showProductPrice;
	}

	public String getShowTotalPoint() {
		return showTotalPoint;
	}

	public void setShowTotalPoint(String showTotalPoint) {
		this.showTotalPoint = showTotalPoint;
	}

	public String getShowTotalPrice() {
		return showTotalPrice;
	}

	public void setShowTotalPrice(String showTotalPrice) {
		this.showTotalPrice = showTotalPrice;
	}

	private String showTotalPrice;

	/**
	 * @return the productTotalMoney
	 */
	public BigDecimal getProductTotalMoney() {
		return productTotalMoney;
	}

	/**
	 * @param productTotalMoney the productTotalMoney to set
	 */
	public void setProductTotalMoney(BigDecimal productTotalMoney) {
		this.productTotalMoney = productTotalMoney;
	}

	/**
	 * @return the productTotalPoint
	 */
	public BigDecimal getProductTotalPoint() {
		return productTotalPoint;
	}

	/**
	 * @param productTotalPoint the productTotalPoint to set
	 */
	public void setProductTotalPoint(BigDecimal productTotalPoint) {
		this.productTotalPoint = productTotalPoint;
	}

	private BigDecimal productTotalPoint;//总点数
	/**
	 * 是否属于BOM产品
	 * 0 不是 1是
	 */
	private boolean bom;
	private String productListType;
	/**产品1级类型**/
	private String productParentListType;

	/**
	 * @return the productListType
	 */
	public String getProductListType() {
		return productListType;
	}

	/**
	 * @param productListType the productListType to set
	 */
	public void setProductListType(String productListType) {
		this.productListType = productListType;
	}

	public String getProductParentListType() {
		return productParentListType;
	}

	public void setProductParentListType(String productParentListType) {
		this.productParentListType = productParentListType;
	}

	public ProductPriceInfo() {
	}

	/**
	 * @param currencyCode
	 * @param leastQty
	 * @param priceGroupType
	 * @param productCode
	 * @param productPoint
	 * @param productPrice
	 * @param productRetailPrice
	 * @param productSaleType
	 * @param productFullName
	 * @param listFullName
	 * @param unitMeasure
	 * @param productType
	 * @param thisSoQty
	 * @param productPriceMoney
	 * @param bom
	 * @param productListType
	 */
	public ProductPriceInfo(String currencyCode, BigDecimal leastQty, String priceGroupType, String productCode, BigDecimal productPoint,
			BigDecimal productPrice, BigDecimal productRetailPrice, String productSaleType, String productFullName, String listFullName,
			String unitMeasure, String productType, BigDecimal thisSoQty, BigDecimal productPriceMoney, boolean bom, String productListType) {
		super();
		this.currencyCode = currencyCode;
		this.leastQty = leastQty;
		this.priceGroupType = priceGroupType;
		this.productCode = productCode;
		this.productPoint = productPoint;
		this.productPrice = productPrice;
		this.productRetailPrice = productRetailPrice;
		this.productSaleType = productSaleType;
		this.productFullName = productFullName;
		this.listFullName = listFullName;
		this.unitMeasure = unitMeasure;
		this.productType = productType;
		this.thisSoQty = thisSoQty;
		this.productPriceMoney = productPriceMoney;
		this.bom = bom;
		this.productListType = productListType;
	}

	public ProductPriceInfo(String currencyCode, BigDecimal leastQty, String priceGroupType, String productCode, BigDecimal productPoint,
			BigDecimal productPrice, BigDecimal productRetailPrice, String productSaleType, String productFullName, String listFullName,
			String productType, String productShortName, String unitMeasure, boolean bom, String productListType, int directSale,
			BigDecimal unitWeight) {
		super();
		this.currencyCode = currencyCode;
		this.leastQty = leastQty;
		this.priceGroupType = priceGroupType;
		this.productCode = productCode;
		this.productPoint = productPoint;
		this.productPrice = productPrice;
		this.productRetailPrice = productRetailPrice;
		this.productSaleType = productSaleType;
		this.productFullName = productFullName;
		this.listFullName = listFullName;
		this.unitMeasure = unitMeasure;
		this.productType = productType;
		this.productShortName = productShortName;
		this.bom = bom;
		this.productListType = productListType;
		this.directSale = directSale;
		this.unitWeight = unitWeight;
	}

	public ProductPriceInfo(String currencyCode, BigDecimal leastQty, String priceGroupType, String productCode, BigDecimal productPoint,
			BigDecimal productPrice, BigDecimal productRetailPrice, String productSaleType, String productFullName, String listFullName,
			String productType, String productShortName, String unitMeasure, boolean bom, String productListType, int directSale,
			String productEanCode) {
		super();
		this.currencyCode = currencyCode;
		this.leastQty = leastQty;
		this.priceGroupType = priceGroupType;
		this.productCode = productCode;
		this.productPoint = productPoint;
		this.productPrice = productPrice;
		this.productRetailPrice = productRetailPrice;
		this.productSaleType = productSaleType;
		this.productFullName = productFullName;
		this.listFullName = listFullName;
		this.unitMeasure = unitMeasure;
		this.productType = productType;
		this.productShortName = productShortName;
		this.bom = bom;
		this.productListType = productListType;
		this.directSale = directSale;
		this.productEanCode = productEanCode;
	}

	/**
	 * 新增销售单与办理推荐业务
	 * @param currencyCode
	 * @param leastQty
	 * @param priceGroupType
	 * @param productCode
	 * @param productPoint
	 * @param productPrice
	 * @param productRetailPrice
	 * @param productSaleType
	 * @param productFullName
	 * @param listFullName
	 * @param productType
	 * @param productShortName
	 * @param unitMeasure
	 * @param bom
	 * @param productListType
	 * @param directSale
	 * @param productEanCode
	 * @param balanceQty
	 */
	public ProductPriceInfo(String currencyCode, BigDecimal leastQty, String priceGroupType, String productCode, BigDecimal productPoint,
			BigDecimal productPrice, BigDecimal productRetailPrice, String productSaleType, String productFullName, String listFullName,
			String productType, String productShortName, String unitMeasure, boolean bom, String productListType, int directSale,
			String productEanCode, BigDecimal balanceQty, String gbssShowColor, BigDecimal gbssShowOrder) {
		super();
		this.currencyCode = currencyCode;
		this.leastQty = leastQty;
		this.priceGroupType = priceGroupType;
		this.productCode = productCode;
		this.productPoint = productPoint;
		this.productPrice = productPrice;
		this.productRetailPrice = productRetailPrice;
		this.productSaleType = productSaleType;
		this.productFullName = productFullName;
		this.listFullName = listFullName;
		this.unitMeasure = unitMeasure;
		this.productType = productType;
		this.productShortName = productShortName;
		this.bom = bom;
		this.productListType = productListType;
		this.directSale = directSale;
		this.productEanCode = productEanCode;
		this.thisSoQty = balanceQty;
		this.gbssShowColor = gbssShowColor;
		this.gbssShowOrder = gbssShowOrder;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	/**
	 * return the productShortName
	 * @return
	 */
	public String getProductShortName() {
		return productShortName;
	}

	/**
	 * @param productShortName the productShortName to set
	 */
	public void setProductShortName(String productShortName) {
		this.productShortName = productShortName;
	}

	/**
	 * @param currencyCode
	 * @param leastQty
	 * @param priceGroupType
	 * @param productCode
	 * @param productPoint
	 * @param productPrice
	 * @param productRetailPrice
	 * @param productSaleType
	 * @param productFullName
	 * @param listFullName
	 * @param unitMeasure
	 * @param productType
	 * @param thisSoQty
	 * @param productPriceMoney
	 */
	public ProductPriceInfo(String currencyCode, BigDecimal leastQty, String priceGroupType, String productCode, BigDecimal productPoint,
			BigDecimal productPrice, BigDecimal productRetailPrice, String productSaleType, String productFullName, String listFullName,
			String productType, BigDecimal thisSoQty) {
		super();
		this.currencyCode = currencyCode;
		this.leastQty = leastQty;
		this.priceGroupType = priceGroupType;
		this.productCode = productCode;
		this.productPoint = productPoint;
		this.productPrice = productPrice;
		this.productRetailPrice = productRetailPrice;
		this.productSaleType = productSaleType;
		this.productFullName = productFullName;
		this.listFullName = listFullName;
		this.productType = productType;
		this.thisSoQty = thisSoQty;
	}

	public ProductPriceInfo(String currencyCode, BigDecimal leastQty, String priceGroupType, String productCode, BigDecimal productPoint,
			BigDecimal productPrice, BigDecimal productRetailPrice, String productSaleType, String productFullName, String listFullName,
			String productType, String unitMeasure, boolean bom, String productListType) {
		super();
		this.currencyCode = currencyCode;
		this.leastQty = leastQty;
		this.priceGroupType = priceGroupType;
		this.productCode = productCode;
		this.productPoint = productPoint;
		this.productPrice = productPrice;
		this.productRetailPrice = productRetailPrice;
		this.productSaleType = productSaleType;
		this.productFullName = productFullName;
		this.listFullName = listFullName;
		this.productType = productType;
		this.unitMeasure = unitMeasure;
		this.bom = bom;
		this.productListType = productListType;
	}

	public ProductPriceInfo(String currencyCode, BigDecimal leastQty, String priceGroupType, String productCode, BigDecimal productPoint,
			BigDecimal productPrice, BigDecimal productRetailPrice, String productSaleType, String productFullName, String listFullName,
			String productType, String unitMeasure, boolean bom, int directSale, BigDecimal unitWeight) {
		super();
		this.currencyCode = currencyCode;
		this.leastQty = leastQty;
		this.priceGroupType = priceGroupType;
		this.productCode = productCode;
		this.productPoint = productPoint;
		this.productPrice = productPrice;
		this.productRetailPrice = productRetailPrice;
		this.productSaleType = productSaleType;
		this.productFullName = productFullName;
		this.listFullName = listFullName;
		this.productType = productType;
		this.unitMeasure = unitMeasure;
		this.bom = bom;
		this.directSale = directSale;
		this.unitWeight = unitWeight;
	}

	public ProductPriceInfo(String productCode, String productFullName, String listFullName, String productType, String unitMeasure, boolean bom) {
		super();
		this.leastQty = BigDecimal.ZERO;
		this.productCode = productCode;
		this.productPoint = BigDecimal.ZERO;
		this.productPrice = BigDecimal.ZERO;
		this.productRetailPrice = BigDecimal.ZERO;
		this.productFullName = productFullName;
		this.listFullName = listFullName;
		this.productType = productType;
		this.unitMeasure = unitMeasure;
		this.bom = bom;
	}

	public ProductPriceInfo(String productCode, String productFullName, BigDecimal productPoint, BigDecimal productPrice,
			BigDecimal productRetailPrice) {
		//add by bjc_zhangguan
		super();
		this.productCode = productCode;
		this.productFullName = productFullName;
		this.productPoint = productPoint;
		this.productPrice = productPrice;
		this.productRetailPrice = productRetailPrice;

	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setProductPriceMoney(BigDecimal productPriceMoney) {
		this.productPriceMoney = productPriceMoney;
	}

	/**
	 * @return the leastQty
	 */
	public BigDecimal getProductPriceMoney() {
		return this.productPriceMoney;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the leastQty
	 */
	public BigDecimal getLeastQty() {
		return leastQty;
	}

	/**
	 * @param leastQty the leastQty to set
	 */
	public void setLeastQty(BigDecimal leastQty) {
		this.leastQty = leastQty;
	}

	/**
	 * @return the priceGroupType
	 */
	public String getPriceGroupType() {
		return priceGroupType;
	}

	/**
	 * @param priceGroupType the priceGroupType to set
	 */
	public void setPriceGroupType(String priceGroupType) {
		this.priceGroupType = priceGroupType;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the productPoint
	 */
	public BigDecimal getProductPoint() {
		return productPoint;
	}

	/**
	 * @param productPoint the productPoint to set
	 */
	public void setProductPoint(BigDecimal productPoint) {
		this.productPoint = productPoint;
	}

	/**
	 * @return the productPrice
	 */
	public BigDecimal getProductPrice() {
		return productPrice;
	}

	/**
	 * @param productPrice the productPrice to set
	 */
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	/**
	 * @return the productRetailPrice
	 */
	public BigDecimal getProductRetailPrice() {
		return productRetailPrice;
	}

	/**
	 * @param productRetailPrice the productRetailPrice to set
	 */
	public void setProductRetailPrice(BigDecimal productRetailPrice) {
		this.productRetailPrice = productRetailPrice;
	}

	/**
	 * @return the productSaleType
	 */
	public String getProductSaleType() {
		return productSaleType;
	}

	/**
	 * @param productSaleType the productSaleType to set
	 */
	public void setProductSaleType(String productSaleType) {
		this.productSaleType = productSaleType;
	}

	/**
	 * @return the productFullName
	 */
	public String getProductFullName() {
		return productFullName;
	}

	/**
	 * @param productFullName the productFullName to set
	 */
	public void setProductFullName(String productFullName) {
		this.productFullName = productFullName;
	}

	/**
	 * @return the listFullName
	 */
	public String getListFullName() {
		return listFullName;
	}

	/**
	 * @param listFullName the listFullName to set
	 */
	public void setListFullName(String listFullName) {
		this.listFullName = listFullName;
	}

	/**
	 * @return the unitMeasure
	 */
	public String getUnitMeasure() {
		return unitMeasure;
	}

	/**
	 * @param unitMeasure the unitMeasure to set
	 */
	public void setUnitMeasure(String unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

	public BigDecimal getUnitWeight() {
		return unitWeight;
	}

	public void setUnitWeight(BigDecimal unitWeight) {
		this.unitWeight = unitWeight;
	}

	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @return the thisSoQty
	 */
	public BigDecimal getThisSoQty() {
		return thisSoQty;
	}

	/**
	 * @param thisSoQty the thisSoQty to set
	 */
	public void setThisSoQty(BigDecimal thisSoQty) {
		this.thisSoQty = thisSoQty;
	}

	/**
	 * @return the bom
	 */
	public boolean isBom() {
		return bom;
	}

	/**
	 * @param bom the bom to set
	 */
	public void setBom(boolean bom) {
		this.bom = bom;
	}

	public String getProductAttr() {
		return productAttr;
	}

	public void setProductAttr(String productAttr) {
		this.productAttr = productAttr;
	}

	/**
	 * @param productCode
	 * @param productFullName
	 * @param listFullName
	 * @param unitMeasure
	 * @param bom
	 * @param productListType
	 */
	public ProductPriceInfo(String productCode, String productFullName, String listFullName, String unitMeasure, String productType) {
		super();
		this.productCode = productCode;
		this.productFullName = productFullName;
		this.listFullName = listFullName;
		this.unitMeasure = unitMeasure;
		this.productType = productType;
	}

	public ProductPriceInfo(String productCode, String productFullName, String unitMeasure, BigDecimal thisSoQty) {
		super();
		this.productCode = productCode;
		this.productFullName = productFullName;
		this.unitMeasure = unitMeasure;
		this.thisSoQty = thisSoQty;
	}

	/**
	 * @return the rowCode
	 */
	public String getRowCode() {
		return rowCode;
	}

	/**
	 * @param rowCode
	 *            the rowCode to set
	 */
	public void setRowCode(String rowCode) {
		this.rowCode = rowCode;
	}

	public ProductPriceInfo(String productCode, String productFullName, String unitMeasure, BigDecimal poAppQty, BigDecimal thisSoQty,
			BigDecimal leastQty) {
		super();
		this.productCode = productCode;
		this.productFullName = productFullName;
		this.unitMeasure = unitMeasure;
		this.poAppQty = poAppQty;
		this.thisSoQty = thisSoQty;
		this.leastQty = leastQty;
	}

	public int getDirectSale() {
		return directSale;
	}

	public void setDirectSale(int directSale) {
		this.directSale = directSale;
	}

	public boolean isNeedEpc() {
		return isNeedEpc;
	}

	public void setNeedEpc(boolean isNeedEpc) {
		this.isNeedEpc = isNeedEpc;
	}

	public String getProductEanCode() {
		return productEanCode;
	}

	public void setProductEanCode(String productEanCode) {
		this.productEanCode = productEanCode;
	}

	public String getGbssShowColor() {
		return gbssShowColor;
	}

	public void setGbssShowColor(String gbssShowColor) {
		this.gbssShowColor = gbssShowColor;
	}

	public BigDecimal getGbssShowOrder() {
		return gbssShowOrder;
	}

	public void setGbssShowOrder(BigDecimal gbssShowOrder) {
		this.gbssShowOrder = gbssShowOrder;
	}

	public boolean isEpc() {
		return isEpc;
	}

	public void setEpc(boolean isEpc) {
		this.isEpc = isEpc;
	}

	public BigDecimal getPackageQty() {
		return packageQty;
	}

	public void setPackageQty(BigDecimal packageQty) {
		this.packageQty = packageQty;
	}

	public ProductPriceInfo(String productCode, String productFullName, BigDecimal productPoint, BigDecimal productPrice,
			BigDecimal productRetailPrice, String productType) {
		//add by zdm
		super();
		this.productCode = productCode;
		this.productFullName = productFullName;
		this.productPoint = productPoint;
		this.productPrice = productPrice;
		this.productRetailPrice = productRetailPrice;
		this.productType = productType;

	}
}
