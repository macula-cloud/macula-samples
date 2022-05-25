/**
 * 
 */
package org.macula.cloud.po.gbss.repository;

import org.springframework.data.repository.NoRepositoryBean;

/**
 * <p>
 * <b>DsBaseRepositoryCustom</b> 是公共的repository的自定义接口
 * </p>
 * 
 
 
 
 * 
 */
@NoRepositoryBean
public interface DsBaseRepositoryCustom {
	/**
	 * 根据序列名称查询序列值
	 * 
	 * @param SeqName
	 *            序列名称
	 * @return
	 */
	public long getSequenceNo(String SeqName);

	/**
	 * 判断当前时间是否在16:00以前还是以后  以前为0  以后为1
	 * @return
	 */
	public int queryDeliveryPlanTime();

	/**
	 * 根据序列名称,长度查询序列值
	 * 
	 * @param seqName
	 * @param seqLen
	 * @return
	 */
	public String getSequenceNoString(String seqName, int seqLen);

	/**
	 * 获得文件号
	 * 
	 * @param branchNo
	 * @param period
	 * @param head
	 * @param type
	 * @return
	 */
	// public long generatNextDocNo(String branchNo, String period, String head,
	// String type);

	/**
	 * 查询sequence是否存在
	 * @param seqName
	 * @return
	 */
	public boolean checkSeqExist(String seqName);

}
