package org.macula.cloud.logistics.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Gbss2CainiaoRequest extends LogisticsRequest {

	private static final long serialVersionUID = 1L;

	private String areaCode;

	private String address;
}
