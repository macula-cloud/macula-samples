package org.macula.cloud.po.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class StatusStartRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private String poNo;
	private String poProcessCode;
	private String status;
	private Date statusTime;
	private String comments;
	private String poSource;
	private String refSourceNo;
	private Long refSourceId;

}
