package org.macula.cloud.logistics.vo;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddressResponse extends LogisticsResponse {

	private static final long serialVersionUID = 1L;

	private List<DivisionVO> children;

	@Data
	public class DivisionVO {
		private String divisionCode;
		private int divisionLevel;
		private String pinyin;
		private String divisionName;
		private String divisionTname;
		private String divisionId;
		private String divisionAbbName;
		private boolean deleted;
		private String version;
		private String parentId;

		private List<DivisionVO> children;
	}
}
