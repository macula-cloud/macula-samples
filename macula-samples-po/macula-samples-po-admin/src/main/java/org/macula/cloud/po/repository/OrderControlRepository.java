package org.macula.cloud.po.repository;

import org.macula.cloud.po.domain.SysParamInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderControlRepository extends JpaRepository<SysParamInfo, Long> {
    List<SysParamInfo> findByParaSystem(String oms);

    @Modifying
    @Transactional
    @Query(value = "update sys_param_info s set para_value = ?2 where s.id = ?1", nativeQuery = true)
    void updateById(Long id, String param);
}
