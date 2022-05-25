package org.macula.cloud.po.service;

import java.util.List;

import org.macula.cloud.po.domain.PoCheckDetail;
import org.macula.cloud.po.domain.PoCheckMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CheckService {
	Page<PoCheckMaster> getList(Pageable pageRequest, String synStatus);

	List<PoCheckDetail> findById(Long id);

	PoCheckMaster findByMstId(Long id);
}
