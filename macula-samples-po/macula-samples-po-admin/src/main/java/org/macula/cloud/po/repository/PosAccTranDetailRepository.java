package org.macula.cloud.po.repository;

import java.util.Date;
import java.util.List;

import org.macula.cloud.po.domain.PosAccTranDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosAccTranDetailRepository extends JpaRepository<PosAccTranDetail, Long> {
	List<PosAccTranDetail> findByRefDocNoAndPosStoreNoAndPosTranDate(String refDocNo, String posStoreNo, Date posTranDate);
}
