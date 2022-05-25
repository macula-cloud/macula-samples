package org.macula.cloud.logistics.vo;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Division2RDCResponse extends LogisticsResponse {

	private static final long serialVersionUID = 1L;

	private String code;

	private String name;

	private String whCode1;

	private String whCode2;

	private String whCode3;

	private Date effectiveDate;

	private Date inactiveDate;

}
