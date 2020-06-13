package com.uniroma3.montorsmeds.TaskManager;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;

import com.uniroma3.montorsmeds.TaskManager.model.Project;
import com.uniroma3.montorsmeds.TaskManager.model.Task;
import com.uniroma3.montorsmeds.TaskManager.model.User;
import com.uniroma3.montorsmeds.TaskManager.repository.ProjectRepository;
import com.uniroma3.montorsmeds.TaskManager.repository.TaskRepository;
import com.uniroma3.montorsmeds.TaskManager.repository.UserRepository;
import com.uniroma3.montorsmeds.TaskManager.service.ProjectService;
import com.uniroma3.montorsmeds.TaskManager.service.TaskService;
import com.uniroma3.montorsmeds.TaskManager.service.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
class TaskTests {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private ProjectRepository projectRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TaskService taskService;

	@Before
	public void deleteAll() {
		System.out.println("Deleting all data in db...");
		userRepository.deleteAll();
		projectRepository.deleteAll();
		taskRepository.deleteAll();
		System.out.println("Fattoh!");
	}

	@Test
	public void aggiungiTaskTest() {
		User user = new User("Giulio", "Smedile", "smeds", "lacipolla");
		Project project = new Project("ProgettoBello", user);
		Task task = new Task("Task", "Task bellissima", user, project);
		
		user = userService.saveUser(user);
		project = projectService.saveProject(project);
		task = taskService.saveTask(task);
		
		assertEquals(task.getNome(), "Task");
		assertEquals(task.getUser(), user);
		assertEquals(task.getProject(), project);
		assertEquals(task.getId().longValue(), 1L);
	}
	
	@Test
	public void setCompletedTest() {
		User user = new User("Giulio", "Smedile", "smeds", "lacipolla");
		Project project = new Project("ProgettoBello", user);
		Task task = new Task("Task", "Task bellissima", user, project);
		
		user = userService.saveUser(user);
		project = projectService.saveProject(project);
		taskService.setCompleted(task);
		
		assertNotNull(taskService.getTask(1));
		assertTrue(taskService.getTask(1).isCompleted());
		
	}
	
	
	


}
