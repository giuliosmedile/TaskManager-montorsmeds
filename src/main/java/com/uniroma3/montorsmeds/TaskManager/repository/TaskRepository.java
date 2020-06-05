package com.uniroma3.montorsmeds.TaskManager.repository;

import org.springframework.data.repository.CrudRepository;
import com.uniroma3.montorsmeds.TaskManager.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
