package org.macula.cloud.po.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.wildfly.common.annotation.NotNull;

public class PageUtils {

	private PageUtils() {

	}

	public static Pageable pageRequest(@NotNull Pageable pageable) {
		return PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
	}

}
