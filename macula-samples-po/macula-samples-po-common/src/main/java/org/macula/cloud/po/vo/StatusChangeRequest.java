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
public class StatusChangeRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String poNo;
	private String poProcessCode;
	private Date statusTime;
	private String status;
	private String resultId;
	private String comments;
}
