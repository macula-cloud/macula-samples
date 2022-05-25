/**
 * PosIntercoListDetailRepository.java 2012-10-19
 */
package org.macula.cloud.po.gbss.repository;

import java.util.List;

import org.macula.cloud.po.gbss.domain.PosIntercoListDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>PosIntercoListDetailRepository</b> 自营店内部往来转储单据信息表repository接口
 * </p>
 *
 
 
 
 */
public interface PosIntercoListDetailRepository extends JpaRepository<PosIntercoListDetail, Long> {

	public List<PosIntercoListDetail> findByPoNo(String poNo);

}
