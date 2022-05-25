package org.macula.cloud.po.sap.model;

import java.math.BigDecimal;
import java.util.Date;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

@BapiStructure
public class SapOrderDeliverInfo {

	/**运单号*/
	@Parameter("TRANNOSAP")
	private String tranNoSap;
	/**P单号*/
	@Parameter("PURCH_NO")
	private String purchNo;
	/**销售订单项目号*/
	@Parameter("POSNR")
	private String posnr;
	/**物料号 */
	@Parameter("MATNR")
	private String matnr;
	/**批次号*/
	@Parameter("CHARG")
	private String charg;
	/**仓库*/
	@Parameter("P_PLANT")
	private String pPlant;
	/**库区*/
	@Parameter("LGORT")
	private String lgort;
	/**物料数量*/
	@Parameter("KWMENG")
	private BigDecimal kwmeng;
	/**出库数量（按销售单位）*/
	@Parameter("LFIMG")
	private BigDecimal lfimg;
	/**销售单位 */
	@Parameter("VRKME")
	private String vrkme;
	/**实物出库日期 */
	@Parameter("BLDAT")
	private Date bldat;
	/**实物出库时间 */
	@Parameter("CTIME")
	private Date ctime;

	public String getTranNoSap() {
		return tranNoSap;
	}

	public void setTranNoSap(String tranNoSap) {
		this.tranNoSap = tranNoSap;
	}

	public String getPurchNo() {
		return purchNo;
	}

	public void setPurchNo(String purchNo) {
		this.purchNo = purchNo;
	}

	public String getPosnr() {
		return posnr;
	}

	public void setPosnr(String posnr) {
		this.posnr = posnr;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getCharg() {
		return charg;
	}

	public void setCharg(String charg) {
		this.charg = charg;
	}

	public String getpPlant() {
		return pPlant;
	}

	public void setpPlant(String pPlant) {
		this.pPlant = pPlant;
	}

	public String getLgort() {
		return lgort;
	}

	public void setLgort(String lgort) {
		this.lgort = lgort;
	}

	public BigDecimal getKwmeng() {
		return kwmeng;
	}

	public void setKwmeng(BigDecimal kwmeng) {
		this.kwmeng = kwmeng;
	}

	public BigDecimal getLfimg() {
		return lfimg;
	}

	public void setLfimg(BigDecimal lfimg) {
		this.lfimg = lfimg;
	}

	public String getVrkme() {
		return vrkme;
	}

	public void setVrkme(String vrkme) {
		this.vrkme = vrkme;
	}

	public Date getBldat() {
		return bldat;
	}

	public void setBldat(Date bldat) {
		this.bldat = bldat;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

}
