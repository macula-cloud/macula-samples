package org.macula.cloud.po.sap.model;

import java.math.BigDecimal;
import java.util.Date;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

@BapiStructure
public class EtData {

	@Parameter("VBTYP")
	private String vbtyp;

	@Parameter("BSTKD")
	private String bstkd;

	@Parameter("VBELN")
	private String vbeln;

	@Parameter("BSTZD")
	private String bstzd;

	@Parameter("KUNNR")
	private String kunnr;

	@Parameter("AUDAT")
	private Date audat;

	@Parameter("POSNR")
	private String posnr;

	@Parameter("MATNR")
	private String matnr;

	@Parameter("KWMENG")
	private BigDecimal kwmeng;

	@Parameter("VRKME")
	private String vrkme;

	@Parameter("CHARG")
	private String charg;

	@Parameter("GBSTA")
	private String gbsta;

	@Parameter("GBSTA_BEZ")
	private String gbstaBez;

	@Parameter("ABGRU")
	private String abgru;

	@Parameter("BEZEI")
	private String bezei;

	@Parameter("LFSTA")
	private String lfsta;

	@Parameter("LFSTA_BEZ")
	private String lfstaBez;

	@Parameter("ERNAM")
	private String ernam;

	@Parameter("LGORT")
	private String lgort;

	@Parameter("TRANNOSAP")
	private String trannosap;

	public String getVbtyp() {
		return vbtyp;
	}

	public void setVbtyp(String vbtyp) {
		this.vbtyp = vbtyp;
	}

	public String getBstkd() {
		return bstkd;
	}

	public void setBstkd(String bstkd) {
		this.bstkd = bstkd;
	}

	public String getVbeln() {
		return vbeln;
	}

	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}

	public String getBstzd() {
		return bstzd;
	}

	public void setBstzd(String bstzd) {
		this.bstzd = bstzd;
	}

	public String getKunnr() {
		return kunnr;
	}

	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}

	public Date getAudat() {
		return audat;
	}

	public void setAudat(Date audat) {
		this.audat = audat;
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

	public BigDecimal getKwmeng() {
		return kwmeng;
	}

	public void setKwmeng(BigDecimal kwmeng) {
		this.kwmeng = kwmeng;
	}

	public String getVrkme() {
		return vrkme;
	}

	public void setVrkme(String vrkme) {
		this.vrkme = vrkme;
	}

	public String getCharg() {
		return charg;
	}

	public void setCharg(String charg) {
		this.charg = charg;
	}

	public String getGbsta() {
		return gbsta;
	}

	public void setGbsta(String gbsta) {
		this.gbsta = gbsta;
	}

	public String getGbstaBez() {
		return gbstaBez;
	}

	public void setGbstaBez(String gbstaBez) {
		this.gbstaBez = gbstaBez;
	}

	public String getAbgru() {
		return abgru;
	}

	public void setAbgru(String abgru) {
		this.abgru = abgru;
	}

	public String getBezei() {
		return bezei;
	}

	public void setBezei(String bezei) {
		this.bezei = bezei;
	}

	public String getLfsta() {
		return lfsta;
	}

	public void setLfsta(String lfsta) {
		this.lfsta = lfsta;
	}

	public String getLfstaBez() {
		return lfstaBez;
	}

	public void setLfstaBez(String lfstaBez) {
		this.lfstaBez = lfstaBez;
	}

	public String getErnam() {
		return ernam;
	}

	public void setErnam(String ernam) {
		this.ernam = ernam;
	}

	/**
	 * @return the lgort
	 */
	public String getLgort() {
		return lgort;
	}

	/**
	 * @param lgort the lgort to set
	 */
	public void setLgort(String lgort) {
		this.lgort = lgort;
	}

	/**
	 * @return the trannosap
	 */
	public String getTrannosap() {
		return trannosap;
	}

	/**
	 * @param trannosap the trannosap to set
	 */
	public void setTrannosap(String trannosap) {
		this.trannosap = trannosap;
	}
}
