package org.macula.cloud.po.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.macula.cloud.core.utils.StringUtils;
import org.macula.cloud.po.domain.PoMessageLevel;
import org.macula.cloud.po.repository.MessageLevelRepository;
import org.macula.cloud.po.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 
 
 */
@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private MessageLevelRepository messageLevelRepository;

    @Override
    public Page<PoMessageLevel> getList(Pageable pagRequest, String userName, String mobile, String level) {

        Specification<PoMessageLevel> sp = (Specification<PoMessageLevel>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if (StringUtils.isNotBlank(userName)) {
                list.add(cb.like(root.get("userName").as(String.class), userName + "%"));
            }
            if (StringUtils.isNotBlank(mobile)) {
                list.add(cb.like(root.get("mobile").as(String.class), "%" + mobile + "%"));
            }
            if (StringUtils.isNotBlank(level)) {
                list.add(cb.like(root.get("level").as(String.class), "%" + level + "%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };

        return messageLevelRepository.findAll(sp, pagRequest);
    }

    @Override
    public void add(List<PoMessageLevel> messages, String createBy) {

        for (PoMessageLevel message : messages) {
            message.setCreatedBy(createBy);
        }
        messageLevelRepository.saveAll(messages);
    }

    /**
     * @param ids
     
     
     * 根据id删除
     */
    @Override
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            messageLevelRepository.deleteById(id);
        }
    }

    /**
     * @param mg
     
     
     * 编辑记录
     */
    @Override
    public void updateByOneEntity(PoMessageLevel mg) {
        messageLevelRepository.saveAndFlush(mg);
    }
}
