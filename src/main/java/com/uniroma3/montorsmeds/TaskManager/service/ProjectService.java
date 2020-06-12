package com.uniroma3.montorsmeds.TaskManager.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniroma3.montorsmeds.TaskManager.model.Project;
import com.uniroma3.montorsmeds.TaskManager.model.Task;
import com.uniroma3.montorsmeds.TaskManager.model.User;
import com.uniroma3.montorsmeds.TaskManager.repository.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	protected ProjectRepository projectRepository;

	@Transactional
	public Project saveProject(Project project) {
		return this.projectRepository.save(project);
	}
	
	@Transactional
	public void deleteProject(Project project) {
		this.projectRepository.delete(project);
	}
	
	@Transactional
	public Project getProject(long id) {
		Optional<Project> result = this.projectRepository.findById(id);
		return result.orElse(null);
	}
	
//  per ottenere il progetto dal nome del progetto e il nome del proprietario
	@Transactional
	public Project getProject(String name, String owner) {
		Optional<Project> result = this.projectRepository.getProject(name, owner);
		return result.orElse(null);
	}
//  aggiunge un membro alla lista degli utenti condivisi
	@Transactional
	public Project addMember(Project project, User user) {
		project.addMember(user);
		return this.projectRepository.save(project);
	}
	
//  rimuovi un membro dalla lista degli utenti condivisi
	@Transactional
	public void removeMember(Project project, User user) {
		project.removeMember(user);
	}
	
//  aggiungi una task al progetto
	@Transactional
	public Project addTask (Project project, Task task) {
		project.addTask(task);
		return this.projectRepository.save(project);
	}
	
//  rimuovi una task dal progetto
	public void removeTask(Project project, Task task) {
		project.removeTask(task);
	}
}
