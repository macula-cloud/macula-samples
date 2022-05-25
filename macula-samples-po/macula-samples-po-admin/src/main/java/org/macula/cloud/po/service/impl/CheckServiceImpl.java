package org.macula.cloud.po.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.macula.cloud.core.utils.StringUtils;
import org.macula.cloud.po.domain.PoCheckDetail;
import org.macula.cloud.po.domain.PoCheckMaster;
import org.macula.cloud.po.repository.PoCheckDetailRepository;
import org.macula.cloud.po.repository.PoCheckMasterRepository;
import org.macula.cloud.po.service.CheckService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CheckServiceImpl implements CheckService {

    private PoCheckMasterRepository poCheckMasterRepository;

    private PoCheckDetailRepository poCheckDetailRepository;

    @Override
    public Page<PoCheckMaster> getList(Pageable pageRequest, String synStatus) {
        Specification<PoCheckMaster> sp = (Specification<PoCheckMaster>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if (StringUtils.isNotBlank(synStatus)) {
                list.add(cb.equal(root.get("synStatus").as(String.class), synStatus));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };

        return poCheckMasterRepository.findAll(sp, pageRequest);
    }

    @Override
    public List<PoCheckDetail> findById(Long id) {
        return poCheckDetailRepository.findByCheckMasterId(id);
    }

    @Override
    public PoCheckMaster findByMstId(Long id) {
        Optional<PoCheckMaster> ms = poCheckMasterRepository.findById(id);
        return ms.get();
    }

}
