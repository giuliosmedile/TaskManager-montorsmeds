package com.uniroma3.montorsmeds.TaskManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;

import com.uniroma3.montorsmeds.TaskManager.model.Project;
import com.uniroma3.montorsmeds.TaskManager.model.User;
import com.uniroma3.montorsmeds.TaskManager.repository.ProjectRepository;
import com.uniroma3.montorsmeds.TaskManager.repository.UserRepository;
import com.uniroma3.montorsmeds.TaskManager.service.ProjectService;
import com.uniroma3.montorsmeds.TaskManager.service.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProjectTests {
	
	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private UserService userService;


	@Before
	@AfterEach
	public void deleteAll() {
		System.out.println("Deleting all data in db...");
		projectRepository.deleteAll();
		userRepository.deleteAll();
		System.out.println("Fattoh!");
	}

	@Test
	public void aggiungiProjectTest() {
		User user1 = new User("Giulio", "Smedile");
		Project project1 = new Project("Progetto", user1);
		
		user1 = userService.saveUser(user1);
		project1 = projectService.saveProject(project1);
		
		assertEquals(project1.getProprietario().getNome(), "Giulio");
		assertEquals(project1.getProprietario().getCognome(), "Smedile");
	}
	
	@Test
	public void getProjectById() {
		User user1 = new User("Giulia", "Smedila");
		
		
		Project project1 = new Project("ProgettoBello", user1);
		
		
		User user2 = new User("Silvia", "Montorselli");
		
		Project project2 = new Project("ProgettoBrutto", user2);
		
		user1 = userService.saveUser(user1);
		user2 = userService.saveUser(user2);
		project1 = projectService.saveProject(project1);
		project2 = projectService.saveProject(project2);
		
		assertEquals(projectService.getProject(1L).getNome(), project1.getNome());
		assertEquals(projectService.getProject(2L).getNome(), project2.getNome());
	}
	
	@Test
	public void getProjectByProprietario() {
		User user1 = new User("Giuli", "Smedil");
		
		Project project1 = new Project("ProgettoBell", user1);
		
		User user2 = new User("Silvi", "Montorsell");
		
		Project project2 = new Project("ProgettoBrutt", user2);
		
		user1 = userService.saveUser(user1);
		user2 = userService.saveUser(user2);
		project1 = projectService.saveProject(project1);
		project2 = projectService.saveProject(project2);
		
		assertEquals(projectService.getProject("ProgettoBell", user1).getNome(), project1.getNome());
		assertEquals(projectService.getProject("ProgettoBrutt", user2).getNome(), project2.getNome());
	}
	
	@Test
	public void addMemberTest() {
		User user1 = new User("Giulio1", "Smedile1");
		Project project1 = new Project("ProgettoBello1", user1);
		
		user1 = userService.saveUser(user1);
		project1 = projectService.saveProject(project1);
		assertTrue(project1.getUtentiCondivisi().isEmpty());
		
		project1 = projectService.addMember(project1, user1);
		assertEquals(project1.getUtentiCondivisi().get(0).getNome(), user1.getNome());
	}
	
	
	
	


}
