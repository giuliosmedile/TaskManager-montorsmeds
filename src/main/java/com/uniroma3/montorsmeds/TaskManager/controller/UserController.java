package com.uniroma3.montorsmeds.TaskManager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniroma3.montorsmeds.TaskManager.controller.session.SessionData;
import com.uniroma3.montorsmeds.TaskManager.controller.validation.UserValidator;
import com.uniroma3.montorsmeds.TaskManager.model.Credentials;
import com.uniroma3.montorsmeds.TaskManager.model.User;
import com.uniroma3.montorsmeds.TaskManager.service.CredentialsService;
import com.uniroma3.montorsmeds.TaskManager.service.UserService;

@Controller
public class UserController {

	@Autowired
	SessionData sessionData;
	
	@Autowired
	CredentialsService credentialsService;

	@Autowired
	UserService userService;
	
	@Autowired
	UserValidator userValidator;
	
	public UserController() {
		
	}
	
	@RequestMapping(value = {"/home"}, method = RequestMethod.GET)
	public String home(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Credentials credentials = sessionData.getLoggedCredentials();
		model.addAttribute("user", loggedUser);
		model.addAttribute("credentials", credentials);
		return "home";
	}
	
	@RequestMapping(value = {"/users/me"}, method = RequestMethod.GET)
	public String me(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Credentials credentials = sessionData.getLoggedCredentials();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("credentials", credentials);
		return "userProfile";
	}
	
	@RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
	public String admin(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("user", loggedUser);
		return "admin";
	}
	
	@RequestMapping(value = {"/admin/users"}, method = RequestMethod.GET)
	public String usersList(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Credentials> allCredentials = this.credentialsService.getAllCredentials();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("credentialsList", allCredentials);
		return "allUsers";
	}
	
	@RequestMapping(value = {"/admin/users/{username}/delete"}, method = RequestMethod.POST)
	public String removeUser(Model model, @PathVariable String username) {
		this.credentialsService.deleteCredentials(username);
		
		return "redirect:/admin/users";
	}
	
	@RequestMapping(value = {"/users/me/updateProfile"}, method = RequestMethod.GET)
	public String showUserUpdateForm(Model model) {
		model.addAttribute("updateUserForm", new User());
		model.addAttribute("loggedUser", this.sessionData.getLoggedUser());
		return "updateProfile";
	}
	
	@RequestMapping(value = {"/users/me/updateProfile"}, method = RequestMethod.POST)
	public String updateUser(@Valid @ModelAttribute("updateUserForm") User user, BindingResult userBindingResult, Model model) {
		User loggedUser = sessionData.getLoggedUser();
		
		this.userValidator.validate(user, userBindingResult);
		if(!userBindingResult.hasErrors()) {
			user.setId(loggedUser.getId());
			this.userService.updateUser(user);
			sessionData.update();
			model.addAttribute("loggedUser", sessionData.getLoggedUser());
			return "redirect:/users/me";
		}
		
		return "updateProfile";
	}
}
