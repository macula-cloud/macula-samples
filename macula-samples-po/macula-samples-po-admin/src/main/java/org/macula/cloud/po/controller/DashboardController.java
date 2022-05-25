package org.macula.cloud.po.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.choerodon.core.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/admin")
@Api("Amin公告板Controller")
public class DashboardController {

	@GetMapping("/dashboard")
	@Permission(permissionPublic = true)
	@ApiOperation(value = "获取Amin公告板")
	public String dashboard() {
		return "dashboard";
	}
}
