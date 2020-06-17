package com.uniroma3.montorsmeds.TaskManager.service;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniroma3.montorsmeds.TaskManager.model.Task;
import com.uniroma3.montorsmeds.TaskManager.repository.TaskRepository;

@Service 
public class TaskService {
	
	@Autowired
	protected TaskRepository taskRepository;
	
//	salvataggio task
	@Transactional
	public Task saveTask(Task task) {
		return this.taskRepository.save(task);
		
	}
	
//  rimuovi una task 
	@Transactional 
	public void deleteTask(Task task) {
		this.taskRepository.delete(task);
	}
	
//  ottieni una task
	@Transactional
	public Task getTask(long id) {
		Optional<Task> result = this.taskRepository.findById(id);
		return result.orElse(null);
	}
//  settaggio task come completata
	@Transactional 
	public Task setCompleted(Task task) {
		task.setCompleted();
		return this.taskRepository.save(task);
	}

	@Transactional
	public void updateTask(Task task) {
		Task old = this.getTask(task.getId());
		old.setNome(task.getNome());
		old.setDescrizione(task.getDescrizione());
		old.setUser(task.getUser());
		old.setProject(task.getProject());
		this.taskRepository.save(old);
	}

}
