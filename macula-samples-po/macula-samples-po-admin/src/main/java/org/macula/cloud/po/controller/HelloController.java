package org.macula.cloud.po.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.choerodon.core.annotation.Permission;

@RestController
@RequestMapping("/actuator")
public class HelloController {

	@GetMapping("/hello")
	@Permission(permissionLogin = true)
	public String hello(Principal principal) {
		return "Hello world! " + principal.toString();
	}
}
