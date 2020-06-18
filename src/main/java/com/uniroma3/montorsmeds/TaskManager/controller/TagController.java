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
import com.uniroma3.montorsmeds.TaskManager.controller.validation.ProjectValidator;
import com.uniroma3.montorsmeds.TaskManager.controller.validation.TagValidator;
import com.uniroma3.montorsmeds.TaskManager.model.Project;
import com.uniroma3.montorsmeds.TaskManager.model.Tag;
import com.uniroma3.montorsmeds.TaskManager.model.Task;
import com.uniroma3.montorsmeds.TaskManager.model.User;
import com.uniroma3.montorsmeds.TaskManager.service.CredentialsService;
import com.uniroma3.montorsmeds.TaskManager.service.ProjectService;
import com.uniroma3.montorsmeds.TaskManager.service.TagService;
import com.uniroma3.montorsmeds.TaskManager.service.TaskService;
import com.uniroma3.montorsmeds.TaskManager.service.UserService;

@Controller
public class TagController {
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserService userService;
	
	@Autowired 
	TaskService taskService;
	
	@Autowired 
	TagService tagService;
	
	@Autowired
	CredentialsService credentialsService;
	
	@Autowired
	ProjectValidator projectValidator;
	
	@Autowired
	SessionData sessionData;
	
	@Autowired
	TagValidator tagValidator;
	
	@RequestMapping(value = {"/projects/{projectId}/tags/add"}, method = RequestMethod.GET)
	public String addTagForm(Model model, @PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("tagForm", new Tag());
		model.addAttribute("project", this.projectService.getProject(projectId));
		return "addTag";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/tags/add"}, method = RequestMethod.POST)
	public String createTag(@Valid @ModelAttribute("tagForm") Tag tag, BindingResult tagBindingResult, Model model, @PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		tagValidator.validate(tag, tagBindingResult);
		model.addAttribute("loggedUser", loggedUser);
		if (!tagBindingResult.hasErrors()) {
			Project project = this.projectService.getProject(projectId);
			this.tagService.saveTag(tag);
			project.addTag(tag);
			this.projectService.saveProject(project);
			return "redirect:/projects/" + projectId; 
		}
		return "addTag";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/tasks/{taskId}/tags/edit"}, method = RequestMethod.GET)
	public String addTagToTaskForm(Model model, @PathVariable Long projectId, @PathVariable Long taskId) {
		User loggedUser = sessionData.getLoggedUser();
		Project project = this.projectService.getProject(projectId);
		Task task = this.taskService.getTask(taskId);
		List<Tag> tags = new ArrayList<>();
		for (Tag t : project.getTags()) {
			if (!task.getTags().contains(t)) {
				tags.add(t);
			}
		}
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("tagToTaskForm", new Tag());
		model.addAttribute("project", project);
		
		model.addAttribute("task", task);
		model.addAttribute("tags", tags);
		return "addTagToTask";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/tasks/{taskId}/tags/{tagId}/edit"}, method = RequestMethod.POST)
	public String createTagToTask(Model model, @PathVariable Long projectId, @PathVariable Long taskId, @PathVariable Long tagId) {
		User loggedUser = sessionData.getLoggedUser();
		Task task = this.taskService.getTask(taskId);
		Project project = this.projectService.getProject(projectId);
		Tag tag = this.tagService.findTagById(tagId);

		
		task.addTag(tag);
		tag.addTask(task);
		this.tagService.saveTag(tag);
		this.taskService.saveTask(task);
		this.projectService.saveProject(project);
		
		return "redirect:/projects/{projectId}";
	}

}
