package org.macula.cloud.po.service;

import org.macula.cloud.po.vo.DealerOrderVo;
import org.macula.cloud.po.vo.PoResultDetailVo;

public interface OrderPackageService {

	/**
	 * 方法说明:根据poNo将本系统中相关数据查询出,并封装成SAP需要的VO对象
	 * @param poNo:单号
	 * @param vo  
	 * @return
	 */
	DealerOrderVo packageSapOrderVo(String poNo, PoResultDetailVo vo);

}
