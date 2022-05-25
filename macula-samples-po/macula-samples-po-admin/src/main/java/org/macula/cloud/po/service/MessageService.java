package org.macula.cloud.po.service;

import java.util.List;

import org.macula.cloud.po.domain.PoMessageLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 
 
 */
public interface MessageService {

    /**
     
     
     * 查询所有及根据条件查询记录
     */
    Page<PoMessageLevel> getList(Pageable pagRequest, String userName, String mobile, String level);

    /**
     
     
     * 保存数据
     */
    void add(List<PoMessageLevel> message, String createBy);

    /**
     
     
     * 根据id删除
     */
    void deleteByIds(Long[] ids);

    /**
     
     
     * 编辑记录
     */
    void updateByOneEntity(PoMessageLevel mg);
}
