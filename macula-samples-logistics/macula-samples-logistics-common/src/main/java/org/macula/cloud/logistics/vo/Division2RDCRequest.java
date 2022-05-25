package org.macula.cloud.logistics.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Division2RDCRequest extends LogisticsRequest {

	private static final long serialVersionUID = 1L;

	private String divisionId;

}
