/**
 * 
 */
package org.macula.cloud.po.gbss.repository;

import java.util.List;

import org.macula.cloud.po.domain.PoDetailDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>PoDetailDiscountRepository</b> 是
 * </p>
 * 
 */

public interface PoDetailDiscountRepository extends JpaRepository<PoDetailDiscount, Long> {

	List<PoDetailDiscount> findByPoNo(String poNo);

}
