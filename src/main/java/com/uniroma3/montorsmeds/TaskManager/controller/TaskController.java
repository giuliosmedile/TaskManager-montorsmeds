package com.uniroma3.montorsmeds.TaskManager.controller;

import java.util.ArrayList;
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
import com.uniroma3.montorsmeds.TaskManager.controller.validation.TaskValidator;
import com.uniroma3.montorsmeds.TaskManager.model.Credentials;
import com.uniroma3.montorsmeds.TaskManager.model.Project;
import com.uniroma3.montorsmeds.TaskManager.model.Task;
import com.uniroma3.montorsmeds.TaskManager.model.User;
import com.uniroma3.montorsmeds.TaskManager.service.CredentialsService;
import com.uniroma3.montorsmeds.TaskManager.service.ProjectService;
import com.uniroma3.montorsmeds.TaskManager.service.TaskService;
import com.uniroma3.montorsmeds.TaskManager.service.UserService;

@Controller
public class TaskController {

	@Autowired
	SessionData sessionData;
	
	@Autowired
	CredentialsService credentialsService;

	@Autowired
	UserService userService;
	
	@Autowired 
	ProjectService projectService; 
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	TaskValidator taskValidator;
	
	public TaskController() {
		
	}

	@RequestMapping(value = {"/projects/{projectId}/tasks/add"}, method = RequestMethod.GET)
	public String createTaskForm(Model model, @PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("taskForm", new Task());
		model.addAttribute("project", this.projectService.getProject(projectId));
		return "addTask";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/tasks/add"}, method = RequestMethod.POST)
	public String createTask(@Valid @ModelAttribute("taskForm") Task task, BindingResult taskBindingResult, Model model, @PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		taskValidator.validate(task, taskBindingResult);
		model.addAttribute("loggedUser", loggedUser);
		if (!taskBindingResult.hasErrors()) {
			task.setUser(loggedUser);
			task.setProject(this.projectService.getProject(projectId));
			this.taskService.saveTask(task);
			return "redirect:/projects/" + projectId; 
		}
		return "addTask";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/tasks/{taskId}/edit"}, method = RequestMethod.GET)
	public String showTaskUpdateForm(Model model, @PathVariable Long projectId, @PathVariable Long taskId) {
		model.addAttribute("updateTaskForm", new Task());
		model.addAttribute("loggedUser", this.sessionData.getLoggedUser());
		model.addAttribute("oldTask", this.taskService.getTask(taskId));
		model.addAttribute("projectId", projectId);
		return "updateTask";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/tasks/{taskId}/edit"}, method = RequestMethod.POST)
	public String updateTask(@Valid @ModelAttribute("updateTaskForm") Task task, BindingResult taskBindingResult, Model model, @PathVariable Long projectId, @PathVariable Long taskId) {
		this.taskValidator.validate(task, taskBindingResult);
		if(!taskBindingResult.hasErrors()) {
			task.setId(taskId);
			this.taskService.updateTask(task);
			model.addAttribute("loggedUser", sessionData.getLoggedUser());
			return "redirect:/projects/" + projectId;
		}
		
		return "updateTask";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/tasks/{taskId}/delete"}, method = RequestMethod.POST)
	public String deleteTask(Model model, @PathVariable Long projectId, @PathVariable Long taskId) {
		Task task = this.taskService.getTask(taskId);
		Project project = this.projectService.getProject(projectId);
		project.removeTask(task);
		this.projectService.updateProject(project);
		this.taskService.removeTask(task);
		return "redirect:/projects/" + projectId;
	}
	
	@RequestMapping(value = {"/projects/{projectId}/tasks/{taskId}/share"}, method = RequestMethod.GET)
	public String allShareableUsers(Model model, @PathVariable Long projectId, @PathVariable Long taskId) {
		User loggedUser = sessionData.getLoggedUser();
		List<Credentials> allCredentials = new ArrayList<>();
		allCredentials.addAll(this.credentialsService.getAllCredentials());
		List<Credentials> myCredentials = new ArrayList<>();
		Project project = this.projectService.getProject(projectId);
		for (Credentials c : allCredentials) {
			if (project.getUtentiCondivisi().contains(c.getUser())) {
				myCredentials.add(c);
			}
		}
				
		//posso fare questa cosa perchè, mettendo strategia Identity per l'id, l'id dello user è lo stesso delle credentials
		allCredentials.remove(this.credentialsService.getCredentials(loggedUser.getId()));
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("credentialsList", myCredentials);
		model.addAttribute("projectId", projectId);
		model.addAttribute("taskId", taskId);
		return "shareProject";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/tasks/{taskId}/share/{credentialsId}"}, method = RequestMethod.POST)
	public String shareProject(Model model, @PathVariable Long projectId, @PathVariable Long credentialsId, @PathVariable Long taskId) {
		Project project = this.projectService.getProject(projectId);
		User user = this.userService.getUser(credentialsId);
		Task task = this.taskService.getTask(taskId);
		
		this.taskService.updateUser(task, user);
		
		return "redirect:/projects/{projectId}";
	}

}
