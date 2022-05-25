package org.macula.cloud.po.gbss.controller;

import org.macula.cloud.po.domain.SapDailyUplPo;
import org.macula.cloud.po.gbss.repository.DsSapDailyUplPoRepository;
import org.macula.cloud.po.gbss.sap.DealerOrderVo;
import org.macula.cloud.po.gbss.service.SaveDealerOrderThreadHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oms/gbss")
public class GbssOrderServiceController {

	@Autowired
	private SaveDealerOrderThreadHelpService saveDealerOrderThreadHelpService;
	@Autowired
	private DsSapDailyUplPoRepository sapDailyUplRepository;

	/**
	 * 根据poNo从GBSS查询所有对应数据
	 * @param poNo
	 * @return
	 */
	@GetMapping("/package/{poNo}")
	public ResponseEntity<DealerOrderVo> packageSapVo(@PathVariable("poNo") String poNo) {
		SapDailyUplPo sapDailyUplPo = sapDailyUplRepository.findByPoNo(poNo);
		DealerOrderVo vo = saveDealerOrderThreadHelpService.prepareDataNotPos(sapDailyUplPo);
		return ResponseEntity.ok(vo);
	}

}
