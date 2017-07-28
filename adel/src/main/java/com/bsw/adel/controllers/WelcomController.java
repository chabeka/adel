package com.bsw.adel.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomController {
	@RequestMapping("/welcom")
	public String welcom(){
		return "rien";
	}
}
