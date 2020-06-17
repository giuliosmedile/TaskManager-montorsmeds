package com.uniroma3.montorsmeds.TaskManager.service;

import java.util.ArrayList;
import java.util.List;
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

	@Transactional
	public List<Project> getAllProjectsFromUser(User user) {
		List<Project> result = new ArrayList<>();
		Iterable<Project> iterable = this.projectRepository.findAll();
		for(Project p : iterable) {
			if (p.getProprietario().equals(user))
				result.add(p);
		}
		return result;
	}

	//  per ottenere il progetto dal nome del progetto e il proprietario
	@Transactional
	public Project getProject(String name, User proprietario) {
		Optional<Project> result = this.projectRepository.findByNomeAndProprietario(name, proprietario);
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

	public List<Project> getAllProjectsSharedWithUser(User user) {
		List<Project> result = new ArrayList<>();
		Iterable<Project> iterable = this.projectRepository.findAll();
		for(Project p : iterable) {
			if (p.getUtentiCondivisi().contains(user))
				result.add(p);
		}
		return result;
	}

	@Transactional
	public void updateProject(Project project) {
		Project old = this.getProject(project.getId());
		old.setTasks(project.getTasks());
		old.setNome(project.getNome());
		old.setDescrizione(project.getDescrizione());
		this.saveProject(old);
	}

}
