package org.macula.cloud.po.repository;

import org.macula.cloud.po.domain.SaleBranchInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleBranchInfoRepository extends JpaRepository<SaleBranchInfo, Long> {

	public SaleBranchInfo findBySaleBranchNo(String saleBranchNo);
}
