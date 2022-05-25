package org.macula.cloud.po.gbss.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * <b>DsBaseRepositoryImpl</b> 是公共的repository的自定义接口实现
 * </p>
 * 
 
 
 
 * 
 */
public class DsBaseRepositoryImpl implements DsBaseRepositoryCustom {
	@Autowired
	protected EntityManager entityManager;

	@Override
	public String getSequenceNoString(String seqName, int seqLen) {
		StringBuilder lpad = new StringBuilder("");
		for (int i = 0; i < seqLen; i++) {
			lpad.append("0");
		}
		StringBuffer hql = new StringBuffer();
		hql.append("select lpad(" + seqName + ".nextval," + seqLen + ",'" + lpad.toString().trim() + "') NO from dual");
		Query query = entityManager.createNativeQuery(hql.toString());
		String value = query.getSingleResult().toString();
		return value;
	}

	@Override
	public long getSequenceNo(String SeqName) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select ");
		hql.append(SeqName.trim());
		hql.append(".nextVal as tranNo from dual  ");
		Query query = entityManager.createNativeQuery(hql.toString());
		String value = query.getSingleResult().toString();
		return Long.valueOf(value);
	}

	@Override
	public int queryDeliveryPlanTime() {
		StringBuffer hql = new StringBuffer();
		hql.append(
				"select case when to_char(sysdate, 'yyyymmdd')||(select para_value from sys_param_info  where para_code = 'DELIVERY_CLOSE_TIME') < to_char(sysdate, 'yyyymmddhh24:mi') ");
		hql.append(" then 1 else 0 end v from dual");
		Query query = entityManager.createNativeQuery(hql.toString());
		String value = query.getSingleResult().toString();
		return Integer.parseInt(value);
	}

	@Override
	public boolean checkSeqExist(String seqName) {
		boolean flag = false;
		String sql = "select count(sequence_name) from sys.all_sequences where  " + " sequence_name='" + seqName.trim() + "'";
		Query query = entityManager.createNativeQuery(sql);
		String value = query.getSingleResult().toString();
		if (Integer.parseInt(value) > 0) {
			flag = true;
		}
		return flag;
	}

}