package org.macula.samples.huaweicloud.obs.webdav;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OssFile {

	private long length;
	private String link;
	private String name;
	private Date putTime;
}
