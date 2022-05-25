package org.macula.cloud.po.gbss.service;

import org.macula.cloud.po.domain.SapDailyUplPo;
import org.macula.cloud.po.gbss.repository.DsSapDailyUplPoRepository;
import org.macula.cloud.po.gbss.sap.DealerOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GBSSClientService {

	@Autowired
	private DsSapDailyUplPoRepository sapDailyUplRepository;

	@Autowired
	private SaveDealerOrderThreadHelpService dealerOrderService;

	public DealerOrderVo getDealerOrderVo(String poNo) {
		SapDailyUplPo dailyUplPo = sapDailyUplRepository.findByPoNo(poNo);
		return dealerOrderService.prepareDataNotPos(dailyUplPo);
	}

	/**
	 * 根据poNo:获取推送给SAP组合数据的所有相关表数据
	 * @param poNo
	 * @return
	 */
	//	public PoResultDetailVo getResultDetailVo(String poNo) {
	//		return dealerOrderService.getResultDetailVo(poNo);
	//	}
	//
	//	public PoResultDetailVo getPoDetailVo(String poNo) {
	//		return dealerOrderService.getPoDetailVo(poNo);
	//	}
}
