package com.uniroma3.montorsmeds.TaskManager.service;

import java.util.Optional;

import javax.transaction.Transactional;

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
	public void setCompleted(Task task) {
		task.setCompleted();
		this.taskRepository.save(task);
	}

}
