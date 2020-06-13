package com.uniroma3.montorsmeds.TaskManager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniroma3.montorsmeds.TaskManager.controller.validation.CredentialsValidator;
import com.uniroma3.montorsmeds.TaskManager.controller.validation.UserValidator;
import com.uniroma3.montorsmeds.TaskManager.model.Credentials;
import com.uniroma3.montorsmeds.TaskManager.model.User;
import com.uniroma3.montorsmeds.TaskManager.service.CredentialsService;

@Controller
public class AuthenticationController {

	@Autowired
	CredentialsService credentialsService;
	
	@Autowired
	UserValidator userValidator;
	
	@Autowired
	CredentialsValidator credentialsValidator;
	
	@RequestMapping(value = {"/users/register"}, method = RequestMethod.GET)
	public String showRegisterForm(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("credentialsForm", new Credentials());
		return "registerUser";
	}
	
	@RequestMapping(value = {"/users/register"}, method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("userForm") User user, BindingResult userBindingResult, @Valid @ModelAttribute("credentialsForm") Credentials credentials, BindingResult credentialsBindingResult, Model model) {
		this.userValidator.validate(user, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);
		if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			return "registrationSuccesful";
		}
		return "registerUser";
		
	}
	
}
