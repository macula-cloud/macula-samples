package org.macula.cloud.po.service.impl;

import lombok.AllArgsConstructor;
import org.macula.cloud.po.domain.SysParamInfo;
import org.macula.cloud.po.repository.OrderControlRepository;
import org.macula.cloud.po.service.OrderControlService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderControlServiceImpl implements OrderControlService {

    private static final String OMS = "OMS";
    private OrderControlRepository orderControlRepository;

    @Override
    public List<SysParamInfo> findByParaSystem() {
        return orderControlRepository.findByParaSystem(OMS);
    }

    @Override
    public void updateById(Long id, String param) {
        if ("0".equals(param)) {
            param = "false";
        } else if ("1".equals(param)) {
            param = "true";
        } else {
            param = "";
        }
        orderControlRepository.updateById(id, param);
    }
}
