package org.macula.cloud.stock.web.pojo;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "LC_CATEGORY")
public class LcCategory extends org.macula.cloud.core.domain.LegacyUpdateable<java.lang.Long> implements java.io.Serializable {

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "STOCK")
    private String stock;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "CATEGORY_TYPE")
    private Short categoryType;

    @Column(name = "STATUS")
    private Short status;

    @Column(name = "LEAF_FLAG")
    private Boolean leafFlag;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    @Column(name = "LAST_UPDATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdatedTime;

    @Column(name = "CREATED_BY")
    private String createdBy;

}
