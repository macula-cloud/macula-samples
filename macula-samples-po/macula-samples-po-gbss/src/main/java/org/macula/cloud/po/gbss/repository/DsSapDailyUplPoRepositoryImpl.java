package org.macula.cloud.po.gbss.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import org.macula.cloud.po.domain.SapDailyUplPo;
import org.macula.cloud.po.domain.SapDailyUplPoV2;
import org.macula.cloud.po.gbss.domain.PosDeliveryMaster;

/**
 * <p>
 * <b>DsSapDailyUplPoRepositoryImpl</b> 是
 * </p>
 *
 
 
 
 *
 */
/**
 * <p>
 * <b>DsSapDailyUplPoRepositoryImpl</b> is
 * </p>
 *
 
 
 
 */
public class DsSapDailyUplPoRepositoryImpl implements DsSapDailyUplPoRepositoryCustom {

	protected EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	@Override
	public List<SapDailyUplPo> findBySynTypeAndSynStatusAccordingToNum(int maxQueryNum) {
		String nativeLock = " from SapDailyUplPo t where t.synType='A' and t.synStatus='0'  ";
		Query q = entityManager.createQuery(nativeLock);
		if (maxQueryNum > 0) {
			q.setFirstResult(0);
			q.setMaxResults(maxQueryNum);
		}
		q.setLockMode(LockModeType.PESSIMISTIC_READ);// 加悲观锁
		@SuppressWarnings("unchecked")
		List<SapDailyUplPo> listLock = q.getResultList();
		if (listLock != null && listLock.size() > 0) {
			for (int i = 0; i < listLock.size(); i++) {
				SapDailyUplPo obj = listLock.get(i);
				obj.setSynStatus("1");// 会自动更新，有update语句打印出来
			}
		}
		return listLock;
	}

	/* (non-Javadoc)
	 
	 */
	@Override
	public List<SapDailyUplPo> findBySynTypeAndSynStatusAccordingToNumAndDateFlag(int maxQueryNum, String dateFlag) {
		String nativeLock = "select t from SapDailyUplPo t, PoMaster p where t.poNo = p.poNo and to_char(p.poDate,'yyyy-MM-dd') <= '" + dateFlag
				+ "' and t.synType='A' and t.synStatus='0'";
		Query q = entityManager.createQuery(nativeLock);
		if (maxQueryNum > 0) {
			q.setFirstResult(0);
			q.setMaxResults(maxQueryNum);
		}
		q.setLockMode(LockModeType.PESSIMISTIC_READ);// 加悲观锁
		@SuppressWarnings("unchecked")
		List<SapDailyUplPo> listLock = q.getResultList();
		if (listLock != null && listLock.size() > 0) {
			for (int i = 0; i < listLock.size(); i++) {
				SapDailyUplPo obj = listLock.get(i);
				obj.setSynStatus("1");// 会自动更新，有update语句打印出来
			}
		}
		return listLock;
	}

	/* (non-Javadoc)
	 
	 */
	@Override
	public List<SapDailyUplPoV2> getBySynTypeAndSynStatusAccordingToNum(int maxQueryNum) {
		String nativeLock = " from SapDailyUplPoV2 t where t.synType='A' and t.synStatus='0'";
		Query q = entityManager.createQuery(nativeLock);
		if (maxQueryNum > 0) {
			q.setFirstResult(0);
			q.setMaxResults(maxQueryNum);
		}
		q.setLockMode(LockModeType.PESSIMISTIC_READ);// 加悲观锁
		@SuppressWarnings("unchecked")
		List<SapDailyUplPoV2> listLock = q.getResultList();
		if (listLock != null && listLock.size() > 0) {
			for (int i = 0; i < listLock.size(); i++) {
				SapDailyUplPoV2 obj = listLock.get(i);
				obj.setSynStatus("1");// 会自动更新，有update语句打印出来
			}
		}

		return listLock;
	}

	/* (non-Javadoc)
	 
	 */
	@Override
	public List<SapDailyUplPoV2> getPosRevokeAndReturnOrderToNum(int maxQueryNum) {
		String nativeLock = " from SapDailyUplPoV2 t where t.synType='A' and t.synStatus='0' and t.poDocType not in ('G301','G302','G303','G304')";
		Query q = entityManager.createQuery(nativeLock);
		if (maxQueryNum > 0) {
			q.setFirstResult(0);
			q.setMaxResults(maxQueryNum);
		}
		q.setLockMode(LockModeType.PESSIMISTIC_READ);// 加悲观锁
		@SuppressWarnings("unchecked")
		List<SapDailyUplPoV2> listLock = q.getResultList();
		if (listLock != null && listLock.size() > 0) {
			for (int i = 0; i < listLock.size(); i++) {
				SapDailyUplPoV2 obj = listLock.get(i);
				obj.setSynStatus("1");// 会自动更新，有update语句打印出来
			}
		}

		return listLock;
	}

	/* (non-Javadoc)
	 
	 */
	@Override
	public List<SapDailyUplPoV2> getPosRevokeAndReturnOrderToNumAndDateFlag(int maxQueryNum, String dateFlag) {
		String nativeLock = "select t from SapDailyUplPoV2 t, PoMaster p where t.poNo = p.poNo and to_char(p.poDate,'yyyy-MM-dd') <= '" + dateFlag
				+ "' and t.synType='A' and t.synStatus='0' and t.poDocType not in ('G301','G302','G303','G304')";
		Query q = entityManager.createQuery(nativeLock);
		if (maxQueryNum > 0) {
			q.setFirstResult(0);
			q.setMaxResults(maxQueryNum);
		}
		q.setLockMode(LockModeType.PESSIMISTIC_READ);// 加悲观锁
		@SuppressWarnings("unchecked")
		List<SapDailyUplPoV2> listLock = q.getResultList();
		if (listLock != null && listLock.size() > 0) {
			for (int i = 0; i < listLock.size(); i++) {
				SapDailyUplPoV2 obj = listLock.get(i);
				obj.setSynStatus("1");// 会自动更新，有update语句打印出来
			}
		}

		return listLock;
	}

	@Override
	public List<SapDailyUplPoV2> getBySynTypeAndSynStatusAndpoStoreCode(String poStoreCode, int maxQueryNum) {
		String nativeLock = " from SapDailyUplPoV2 t where t.synType = 'A' and t.synStatus = '0' " + " and t.poStoreNo = '" + poStoreCode
				+ "' and months_between(sysdate,t.createdDate) <= 2";
		//		// 只查询正向销售单
		//		String nativeLock = " from SapDailyUplPoV2 t where t.synType = 'A' and t.synStatus = '0' "
		//				+ " and t.poStoreNo = '" + poStoreCode + "' and months_between(sysdate,t.createdDate) <= 2"
		//				+ " and t.poDocType in ('G301','G302','G303','G304')";
		Query q = entityManager.createQuery(nativeLock);
		if (maxQueryNum > 0) {
			q.setFirstResult(0);
			q.setMaxResults(maxQueryNum);
		}
		q.setLockMode(LockModeType.PESSIMISTIC_READ);// 加悲观锁
		@SuppressWarnings("unchecked")
		List<SapDailyUplPoV2> listLock = q.getResultList();
		if (listLock != null && listLock.size() > 0) {
			for (int i = 0; i < listLock.size(); i++) {
				SapDailyUplPoV2 obj = listLock.get(i);
				obj.setSynStatus("1");// 会自动更新，有update语句打印出来
			}
		}

		return listLock;
	}

	@Override
	public List<SapDailyUplPoV2> getBySynTypeAndSynStatusAccordingToNumAndDateFlag(String poStoreCode, int maxQueryNum, String dateFlag) {
		String nativeLock = "select t from SapDailyUplPoV2 t,PoMaster p where t.poNo = p.poNo and to_char(p.poDate,'yyyy-MM-dd')" + " <='" + dateFlag
				+ "' and t.synType = 'A' and t.synStatus = '0' and t.poStoreNo = '" + poStoreCode
				+ "' and months_between(sysdate,t.createdDate) <= 2";
		//		// 只查询正向销售单
		//		String nativeLock = "select t from SapDailyUplPoV2 t,PoMaster p where t.poNo = p.poNo and to_char(p.poDate,'yyyy-MM-dd')"
		//				+ " <='"
		//				+ dateFlag
		//				+ "' and t.synType = 'A' and t.synStatus = '0' "
		//				+ " and t.poStoreNo = '"
		//				+ poStoreCode
		//				+ "' and months_between(sysdate,t.createdDate) <= 2"
		//				+ " and t.poDocType in ('G301','G302','G303','G304')";
		Query q = entityManager.createQuery(nativeLock);
		if (maxQueryNum > 0) {
			q.setFirstResult(0);
			q.setMaxResults(maxQueryNum);
		}
		q.setLockMode(LockModeType.PESSIMISTIC_READ);// 加悲观锁
		@SuppressWarnings("unchecked")
		List<SapDailyUplPoV2> listLock = q.getResultList();
		if (listLock != null && listLock.size() > 0) {
			for (int i = 0; i < listLock.size(); i++) {
				SapDailyUplPoV2 obj = listLock.get(i);
				obj.setSynStatus("1");// 会自动更新，有update语句打印出来
			}
		}

		return listLock;
	}

	/* (non-Javadoc)
	 
	 */
	//GBSSYW-1464 增加56类型的单子
	@Override
	public List<PosDeliveryMaster> getPosDeliveryMasterList(String sapPostingFlag, int maxQueryNum) {
		//		//GBSSYW-1464 增加poType类型数据参数
		//		String[] defaultParaList = new String[] { "10", "21", "51", "52", "53", "92", "57", "56", "55" };//默认参数
		//		List<String> paraList = new ArrayList<String>();
		//		try {
		//			List<Map<String, Object>> list = DataSetUtils.createMappedOptions("UPLOAD_PO_TYPE", SecurityUtils.getUserContext());
		//			if (list != null) {
		//				for (int i = 0; i < list.size(); i++) {
		//					String type = list.get(i).get("code").toString();
		//					paraList.add(type);
		//				}
		//			}
		//		} catch (Exception e) {
		//			//do nothing...
		//		}
		//
		//		if (paraList == null || paraList.size() < 1) {
		//			for (String defaultPara : defaultParaList) {
		//				paraList.add(defaultPara);
		//			}
		//		}
		//
		//		//OLDGBSS-2742 【开发】优化获取自营店交货主信息列表查询
		//		//		List<String> periodList = new ArrayList<String>();
		//		//		periodList.add(DateTimeUtil.getPeriod(0));//获取最近3个月
		//		//		periodList.add(DateTimeUtil.getPeriod(-1));
		//		//		periodList.add(DateTimeUtil.getPeriod(-2));
		//
		//		String nativeLock = "select t from PosDeliveryMaster t,PoMaster p where t.sapPostingFlag=:sapPostingFlag and p.poNo=t.poNo"
		//				+ " and p.sapPostingFlag='Y' and p.poStatus='00' and p.poType in (:typeList)";
		//		Query q = entityManager.createQuery(nativeLock);
		//		q.setParameter("sapPostingFlag", sapPostingFlag);
		//		q.setParameter("typeList", paraList);
		//		//		q.setParameter("periodList", periodList);//modify OLDGBSS-2742 【开发】优化获取自营店交货主信息列表查询
		//		if (maxQueryNum > 0) {
		//			q.setFirstResult(0);
		//			q.setMaxResults(maxQueryNum);
		//		}
		//
		//		@SuppressWarnings("unchecked")
		//		List<PosDeliveryMaster> pdmList = q.getResultList();

		//		return pdmList;
		return null;
	}

	/* (non-Javadoc)
	 
	 */
	@Override
	public PosDeliveryMaster getPosDeliveryMaster(String posDeliveryNo) {
		String nativeLock = "select t from PosDeliveryMaster t where t.posDeliveryNo=:posDeliveryNo";
		Query q = entityManager.createQuery(nativeLock);
		q.setParameter("posDeliveryNo", posDeliveryNo);
		q.setLockMode(LockModeType.PESSIMISTIC_READ);

		return (PosDeliveryMaster) q.getSingleResult();
	}

	/* (non-Javadoc)
	 
	 */
	@Override
	public List<SapDailyUplPoV2> getBySynTypeAndSynStatusAccordingToNumAndDateFlag(String poStoreCode, String dateFlag) {
		String nativeLock = "select t from SapDailyUplPoV2 t,PoMaster p where t.poNo = p.poNo and to_char(p.poDate,'yyyy-MM-dd')" + " <='" + dateFlag
				+ "' and t.synType = 'A' and t.synStatus = '0' and t.poStoreNo = '" + poStoreCode
				+ "' and months_between(sysdate,t.createdDate) <= 2";
		//				// 只查询正向销售单
		//				String nativeLock = " from SapDailyUplPoV2 t,PoMaster p where t.poNo = p.poNo and to_char(p.poDate,'yyyy-MM-dd')" +
		//						" <='" + dateFlag + "' and t.synType = 'A' and t.synStatus = '0' "
		//						+ " and t.poStoreNo = '" + poStoreCode + "' and months_between(sysdate,t.createdDate) <= 2"
		//						+ " and t.poDocType in ('G301','G302','G303','G304')";
		Query q = entityManager.createQuery(nativeLock);
		q.setLockMode(LockModeType.PESSIMISTIC_READ);// 加悲观锁
		@SuppressWarnings("unchecked")
		List<SapDailyUplPoV2> listLock = q.getResultList();
		if (listLock != null && listLock.size() > 0) {
			for (int i = 0; i < listLock.size(); i++) {
				SapDailyUplPoV2 obj = listLock.get(i);
				obj.setSynStatus("1");// 会自动更新，有update语句打印出来
			}
		}

		return listLock;
	}

	@Override
	public List<SapDailyUplPoV2> getBySynTypeAndSynStatusAndpoStoreCode(String poStoreCode) {
		String nativeLock = " from SapDailyUplPoV2 t where t.synType = 'A' and t.synStatus = '0' " + " and t.poStoreNo = '" + poStoreCode
				+ "' and months_between(sysdate,t.createdDate) <= 2";
		//		// 只查询正向销售单
		//		String nativeLock = " from SapDailyUplPoV2 t where t.synType = 'A' and t.synStatus = '0' "
		//				+ " and t.poStoreNo = '" + poStoreCode + "' and months_between(sysdate,t.createdDate) <= 2"
		//				+ " and t.poDocType in ('G301','G302','G303','G304')";
		Query q = entityManager.createQuery(nativeLock);
		q.setLockMode(LockModeType.PESSIMISTIC_READ);// 加悲观锁
		@SuppressWarnings("unchecked")
		List<SapDailyUplPoV2> listLock = q.getResultList();
		if (listLock != null && listLock.size() > 0) {
			for (int i = 0; i < listLock.size(); i++) {
				SapDailyUplPoV2 obj = listLock.get(i);
				obj.setSynStatus("1");// 会自动更新，有update语句打印出来
			}
		}

		return listLock;
	}

	@Override
	public List<SapDailyUplPo> getSapDailyUplPoByPoNo(String poNo) {
		String nativeLock = " from SapDailyUplPo t where  t.poNo = '" + poNo + "'";
		Query q = entityManager.createQuery(nativeLock);
		q.setLockMode(LockModeType.PESSIMISTIC_READ);// 加悲观锁
		@SuppressWarnings("unchecked")
		List<SapDailyUplPo> listLock = q.getResultList();
		if (listLock != null && listLock.size() > 0) {
			for (int i = 0; i < listLock.size(); i++) {
				SapDailyUplPo obj = listLock.get(i);
				obj.setSynStatus("1");// 会自动更新，有update语句打印出来
			}
		}

		return listLock;
	}

	@Override
	public List<SapDailyUplPoV2> getSapDailyUplPoV2ByPoNo(String poNo) {
		String nativeLock = " from SapDailyUplPoV2 t where t.poNo = '" + poNo + "'";
		Query q = entityManager.createQuery(nativeLock);
		q.setLockMode(LockModeType.PESSIMISTIC_READ);// 加悲观锁
		@SuppressWarnings("unchecked")
		List<SapDailyUplPoV2> listLock = q.getResultList();
		if (listLock != null && listLock.size() > 0) {
			for (int i = 0; i < listLock.size(); i++) {
				SapDailyUplPoV2 obj = listLock.get(i);
				obj.setSynStatus("1");// 会自动更新，有update语句打印出来
			}
		}
		return listLock;
	}

}
