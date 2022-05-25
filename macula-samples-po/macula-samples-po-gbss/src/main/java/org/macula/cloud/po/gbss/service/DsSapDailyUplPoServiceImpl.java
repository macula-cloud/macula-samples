/**
 * 
 */
package org.macula.cloud.po.gbss.service;

import java.util.List;

import javax.annotation.Resource;

import org.macula.cloud.po.domain.SapDailyUplPo;
import org.macula.cloud.po.domain.SapDailyUplPoV2;
import org.macula.cloud.po.gbss.repository.DsDealerRepository;
import org.macula.cloud.po.gbss.repository.DsSapDailyUplPoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * <b>DsSapDailyUplPoServiceImpl</b> 是
 * </p>
 *
 
 
 
 *
 */
@Service
public class DsSapDailyUplPoServiceImpl implements DsSapDailyUplPoService {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	private DsSapDailyUplPoRepository dsSapDailyUplPoRepository;
	@Resource
	private DsDealerRepository dsDealerRepository;

	@Override
	@Transactional
	public List<SapDailyUplPo> findBySynTypeAndSynStatusAccordingToNum(int queryNum) throws Exception {
		try {
			List<SapDailyUplPo> list = dsSapDailyUplPoRepository.findBySynTypeAndSynStatusAccordingToNum(queryNum);
			return list;
		} catch (Exception e) {
			log.error("查询SAP上传中间表出错：", e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 
	 */
	@Override
	@Transactional
	public List<SapDailyUplPo> findBySynTypeAndSynStatusAccordingToNumAndDateFlag(int queryNum, String dateFlag) throws Exception {
		try {
			List<SapDailyUplPo> list = dsSapDailyUplPoRepository.findBySynTypeAndSynStatusAccordingToNumAndDateFlag(queryNum, dateFlag);
			return list;
		} catch (Exception e) {
			log.error("查询SAP上传中间表出错：", e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 
	 */
	@Override
	@Transactional
	public List<SapDailyUplPoV2> getBySynTypeAndSynStatusAccordingToNum(int queryNum) throws Exception {
		//		try {
		//			List<SapDailyUplPoV2> list = sapDailyUplPoV2Repository.getBySynTypeAndSynStatusAccordingToNum(queryNum);
		//			return list;
		//		} catch (Exception e) {
		//			log.error("查询服务中心SAP上传中间表出错：", e);
		//			throw e;
		//		}
		return null;
	}

	/* (non-Javadoc)
	 
	 */
	@Override
	@Transactional
	public List<SapDailyUplPoV2> getPosRevokeAndReturnOrderToNum(int queryNum) throws Exception {
		//		try {
		//			List<SapDailyUplPoV2> list = sapDailyUplPoV2Repository.getPosRevokeAndReturnOrderToNum(queryNum);
		//			return list;
		//		} catch (Exception e) {
		//			log.error("查询服务中心待上传的撤单和退货单出错：", e);
		//			throw e;
		//		}
		return null;
	}

	/* (non-Javadoc)
	 
	 */
	@Override
	@Transactional
	public List<SapDailyUplPoV2> getPosRevokeAndReturnOrderToNumAndDateFlag(int queryNum, String dateFlag) throws Exception {
		//		try {
		//			List<SapDailyUplPoV2> list = sapDailyUplPoV2Repository.getPosRevokeAndReturnOrderToNumAndDateFlag(queryNum, dateFlag);
		//			return list;
		//		} catch (Exception e) {
		//			log.error("查询服务中心待上传的撤单和退货单出错：", e);
		//			throw e;
		//		}
		return null;
	}

	/* (non-Javadoc)
	 
	 */
	@Override
	@Transactional
	public List<String> getAllPosStoreList() throws Exception {
		//		try {
		//			List<String> list = dsDealerRepository.getAllPosStoreList();
		//			return list;
		//		} catch (Exception e) {
		//			log.error("获取服务中心列表出错:" + e);
		//			throw e;
		//		}

		return null;
	}

	@Override
	@Transactional
	public List<SapDailyUplPoV2> getBySynTypeAndSynStatusAndpoStoreCode(String poStoreCode, int maxQueryNum) throws Exception {
		//		try {
		//			// 重新获取待上传sap的订单记录
		//			List<SapDailyUplPoV2> list = sapDailyUplPoV2Repository.getBySynTypeAndSynStatusAndpoStoreCode(poStoreCode, maxQueryNum);
		//			//			System.out.println("list:" + list);
		//			return list;
		//		} catch (Exception e) {
		//			log.error("查询服务中心SAP上传中间表出错：", e);
		//			throw e;
		//		}

		return null;
	}

	/* (non-Javadoc)
	
	*/
	@Override
	@Transactional
	public List<SapDailyUplPoV2> getBySynTypeAndSynStatusAndpoStoreCodeAndDateFlag(String poStoreCode, int maxQueryNum, String dateFlag)
			throws Exception {
		//		try {
		//			List<SapDailyUplPoV2> list = sapDailyUplPoV2Repository.getBySynTypeAndSynStatusAccordingToNumAndDateFlag(poStoreCode, maxQueryNum,
		//					dateFlag);
		//			return list;
		//		} catch (Exception e) {
		//			log.error("查询服务中心SAP上传中间表出错：", e);
		//			throw e;
		//		}

		return null;
	}
}
