package com.uniroma3.montorsmeds.TaskManager.controller;

import java.lang.reflect.Method;
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
import com.uniroma3.montorsmeds.TaskManager.model.User;
import com.uniroma3.montorsmeds.TaskManager.model.Credentials;
import com.uniroma3.montorsmeds.TaskManager.model.Project;
import com.uniroma3.montorsmeds.TaskManager.service.CredentialsService;
import com.uniroma3.montorsmeds.TaskManager.service.ProjectService;
import com.uniroma3.montorsmeds.TaskManager.service.UserService;

@Controller
public class ProjectController {

	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CredentialsService credentialsService;
	
	@Autowired
	ProjectValidator projectValidator;
	
	@Autowired
	SessionData sessionData;
	
	// ritorna la lista dei miei progetti
	@RequestMapping(value = {"/projects"}, method = RequestMethod.GET)
	public String myOwnedProjects(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Project> projectList = new ArrayList<>();
		projectList = projectService.getAllProjectsFromUser(loggedUser);
		model.addAttribute("title", "My Projects");
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectList", projectList);
		return "myProjects";
	}
	
	// ritorna la lista dei progetti condivisi con me
	@RequestMapping(value = {"/projects/shared"}, method = RequestMethod.GET)
	public String sharedProjects(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Project> projectList = new ArrayList<>();
		projectList = projectService.getAllProjectsSharedWithUser(loggedUser);
		model.addAttribute("title", "Shared Projects with me");
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectList", projectList);
		return "myProjects";
	}
	
	@RequestMapping(value = {"/projects/{projectId}"}, method = RequestMethod.GET)
	public String project(Model model, @PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		Project project = projectService.getProject(projectId);
		if (project == null) {
			return "redirect:/projects";
		}
		List<User> members = userService.getMembers(project);
		if (!project.getProprietario().equals(loggedUser) && !members.contains(loggedUser)) {
			return "redirect:/projects";
		}
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project", project);
		model.addAttribute("members", members);
		
		return "project";
	}
	
	@RequestMapping(value = {"/projects/add"}, method = RequestMethod.GET)
	public String createProjectForm(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectForm", new Project());
		return "addProject";
	}
	
	@RequestMapping(value = {"/projects/add"}, method = RequestMethod.POST)
	public String createProject(@Valid @ModelAttribute("projectForm") Project project, BindingResult projectBindingResult, Model model) {
		User loggedUser = sessionData.getLoggedUser();
		projectValidator.validate(project, projectBindingResult);
		model.addAttribute("loggedUser", loggedUser);
		if (!projectBindingResult.hasErrors()) {
			project.setProprietario(loggedUser);
			this.projectService.saveProject(project);
			return "redirect:/projects/" + project.getId(); 
		}
		return "addProject";
		
	}
	
	@RequestMapping(value = {"/projects/{projectId}/update"}, method = RequestMethod.GET)
	public String showProjectUpdateForm(Model model, @PathVariable Long projectId) {
		model.addAttribute("updateProjectForm", new Project());
		model.addAttribute("loggedUser", this.sessionData.getLoggedUser());
		model.addAttribute("oldProject", this.projectService.getProject(projectId));
		return "updateProject";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/update"}, method = RequestMethod.POST)
	public String updateProject(@Valid @ModelAttribute("updateProjectForm") Project project, BindingResult projectBindingResult, Model model, @PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		
		this.projectValidator.validate(project, projectBindingResult);
		if(!projectBindingResult.hasErrors()) {
			project.setId(projectId);
			this.projectService.updateProject(project);
			sessionData.update();
			model.addAttribute("loggedUser", sessionData.getLoggedUser());
			return "redirect:/projects";
		}
		
		return "updateProject";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/delete"}, method = RequestMethod.POST)
	public String removeProject(Model model, @PathVariable Long projectId) {
		this.projectService.deleteProject(this.projectService.getProject(projectId));
		
		return "redirect:/projects";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/share"}, method = RequestMethod.GET)
	public String allShareableUsers(Model model, @PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		List<Credentials> allCredentials = this.credentialsService.getAllCredentials();
		
		//posso fare questa cosa perchè, mettendo strategia Identity per l'id, l'id dello user è lo stesso delle credentials
		allCredentials.remove(this.credentialsService.getCredentials(loggedUser.getId()));
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("credentialsList", allCredentials);
		model.addAttribute("projectId", projectId);		
		return "shareProject";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/share/{credentialsId}"}, method = RequestMethod.POST)
	public String shareProject(Model model, @PathVariable Long projectId, @PathVariable Long credentialsId) {
		Project project = this.projectService.getProject(projectId);
		User user = this.userService.getUser(credentialsId);
		
		this.projectService.addMember(project, user);
		
		return "redirect:/projects/{projectId}";
	}
}
