package org.macula.cloud.po.repository;

import java.util.List;
import java.util.Map;

import org.macula.cloud.po.domain.PoMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PoMasterRepository extends JpaRepository<PoMaster, Long>, JpaSpecificationExecutor<PoMaster> {


    PoMaster findByPoNo(String poNo);

    @Query(value = "select  m.*,p.`status`,s.company_name " + "from po_master m left join po_process_master p on m.po_no = p.po_no "
            + "left join sys_company s on m.company_no = s.company_no " + "where 1=1 "
            + "and IF (?1 != '', m.po_no like %?1%, 1=1) " + "and IF (?2 != '', m.po_process_code like %?2%, 1=1) "
            + "and IF (?3 != '', m.sap_posting_doc_no like %?3%, 1=1) " + "and IF (?4 != '', m.po_date like %?4%, 1=1) "
            + "and m.po_status in (?5) order by m.po_entry_time desc limit ?6,?7", nativeQuery = true)
    List<Map<String, String>> findPoMasterByCondition(String poNo, String poProcessCode, String sapPostingDocNo, String poDate,
                                                      List<String> status, long offset, int size);

    @Query(value = "select  count(m.po_no) " + "from po_master m left join po_process_master p on m.po_no = p.po_no " + "where 1=1 "
            + "and IF (?1 != '', m.po_no like %?1%, 1=1) " + "and IF (?2 != '', m.po_process_code like %?2%, 1=1) "
            + "and IF (?3 != '', m.sap_posting_doc_no like %?3%, 1=1) " + "and IF (?4 != '', m.po_date like %?4%, 1=1) "
            + "and m.po_status in (?5) ", nativeQuery = true)
    int findPoMasterByCondition(String poNo, String poProcessCode, String sapPostingDocNo, String poDate, List<String> status);
}
