package com.uniroma3.montorsmeds.TaskManager.service;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniroma3.montorsmeds.TaskManager.model.Task;
import com.uniroma3.montorsmeds.TaskManager.model.User;
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
	public void removeTask(Task task) {
		this.taskRepository.delete(task);
	}
	
	@Transactional
	public void removeTask(Long id) {
		Task task = this.getTask(id);
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
		this.taskRepository.save(old);
	}
	
	@Transactional
	public void updateUser(Task task, User user) {
		Task old = this.getTask(task.getId());
		old.setUser(user);
		this.taskRepository.save(old);
	}

}
