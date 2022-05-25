package org.macula.cloud.po.gbss.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuator")
public class HelloActuatorController {

	@GetMapping("/hello")
	public String hello() {
		return "Hello world!";
	}
}
