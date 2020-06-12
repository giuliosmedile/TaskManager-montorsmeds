package com.uniroma3.montorsmeds.TaskManager;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;

import com.uniroma3.montorsmeds.TaskManager.model.User;
import com.uniroma3.montorsmeds.TaskManager.repository.ProjectRepository;
import com.uniroma3.montorsmeds.TaskManager.repository.TaskRepository;
import com.uniroma3.montorsmeds.TaskManager.repository.UserRepository;
import com.uniroma3.montorsmeds.TaskManager.service.ProjectService;
import com.uniroma3.montorsmeds.TaskManager.service.TaskService;
import com.uniroma3.montorsmeds.TaskManager.service.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
class TaskManagerApplicationTests {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private UserService userService;

	@Autowired
	private TaskService taskService;
	
	@Before
	public void deleteAll() {
		System.out.println("Deleting all data in db...");
		projectRepository.deleteAll();
		userRepository.deleteAll();
		taskRepository.deleteAll();
		System.out.println("Fattoh!");
	}

	@Test
	public void updateUserTest() {
		User user = new User("Silvia", "Montorselli", "Fyu11", "sonobellissima");
		user = userService.saveUser(user);
		assertEquals("Silvia", user.getNome());
		assertEquals("Montorselli", user.getCognome());
		assertEquals("Fyu11", user.getUsername());
		assertEquals(1, user.getId());		
	}
	
	@Test
	void contextLoads() {
	}

}
