/**
 * 
 */
package org.macula.cloud.po.gbss.type;

/**
 * <p>
 * <b>TypeSapPostingFlag</b> SAP记账标志
 * </p>
 *
 
 
 
 *
 */

public class TypeSapPostingFlag {
	/** N：未记帐 */
	public final static String NOPAY = "N";

	/** Y：记帐 */
	public final static String PAY = "Y";
	/** E：上传出错 */
	public final static String ERROR = "E";
	/** S：处理成功 */
	public final static String success = "S";

	/** 16.2.7 SAP上传销售单据信息——SAP_DAILY_UPL_PO 的同步类型及同步装备 **/
	/** A：新增的 */
	public final static String SYN_TYPE_A = "A";
	/** U：跟新的 */
	public final static String SYN_TYPE_U = "U";
	/** D：删除的 */
	public final static String SYN_TYPE_D = "D";

	/** 0：初态 */
	public final static String SYN_STATUS_0 = "0";
	/** 1：同步 */
	public final static String SYN_STATUS_1 = "1";
}
