package com.uniroma3.montorsmeds.TaskManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.uniroma3.montorsmeds.TaskManager.controller.validation.CredentialsValidator;
import com.uniroma3.montorsmeds.TaskManager.controller.validation.UserValidator;
import com.uniroma3.montorsmeds.TaskManager.model.Credentials;
import com.uniroma3.montorsmeds.TaskManager.service.CredentialsService;

@Controller
public class AuthenticationController {

	@Autowired
	CredentialsService credentialsService;
	
	@Autowired
	UserValidator userValidator;
	
	@Autowired
	CredentialsValidator credentialsValidator;
}
