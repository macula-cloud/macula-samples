package org.macula.cloud.po.service;

import org.macula.cloud.po.domain.SysParamInfo;

import java.util.List;

public interface OrderControlService {
    List<SysParamInfo> findByParaSystem();

    void updateById(Long id, String param);
}
