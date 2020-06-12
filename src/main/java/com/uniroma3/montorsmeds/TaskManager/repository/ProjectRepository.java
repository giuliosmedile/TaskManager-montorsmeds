package com.uniroma3.montorsmeds.TaskManager.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.uniroma3.montorsmeds.TaskManager.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {

	public Optional<Project> getProject(String name, String owner);
	
}
