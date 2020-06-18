package com.uniroma3.montorsmeds.TaskManager.controller;

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
import com.uniroma3.montorsmeds.TaskManager.controller.validation.CommentoValidator;
import com.uniroma3.montorsmeds.TaskManager.model.Commento;
import com.uniroma3.montorsmeds.TaskManager.model.Task;
import com.uniroma3.montorsmeds.TaskManager.model.User;
import com.uniroma3.montorsmeds.TaskManager.service.CommentoService;
import com.uniroma3.montorsmeds.TaskManager.service.ProjectService;
import com.uniroma3.montorsmeds.TaskManager.service.TaskService;
import com.uniroma3.montorsmeds.TaskManager.service.UserService;

@Controller
public class CommentoController {
	
	@Autowired
	CommentoService commentoService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired 
	CommentoValidator commentoValidator;
	
	@Autowired
	SessionData sessionData;
	
	
	@RequestMapping(value = {"/projects/{projectId}/tasks/{taskId}/comments/add"}, method = RequestMethod.GET)
	public String addCommentForm(Model model, @PathVariable Long projectId, @PathVariable Long taskId) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("commentForm", new Commento());
		model.addAttribute("project", this.projectService.getProject(projectId));
		model.addAttribute("task", this.taskService.getTask(taskId));
		return "addComment";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/tasks/{taskId}/comments/add"}, method = RequestMethod.POST)
	public String addComment(@Valid @ModelAttribute("commentForm") Commento commento, BindingResult commentBindingResult, Model model, @PathVariable Long projectId, @PathVariable Long taskId) {
		User loggedUser = sessionData.getLoggedUser();
		Task task = this.taskService.getTask(taskId);
		commentoValidator.validate(commento, commentBindingResult);
		model.addAttribute("loggedUser", loggedUser);
		if (!commentBindingResult.hasErrors()) {
			commento.setUser(loggedUser);
			commento.setTask(task);
			task.addCommento(commento);
			this.commentoService.saveCommento(commento);
			this.taskService.saveTask(task);
			this.userService.saveUser(loggedUser);
			this.sessionData.update();
			return "redirect:/projects/" + projectId; 
		}
		return "addComment";
	}
	
}
