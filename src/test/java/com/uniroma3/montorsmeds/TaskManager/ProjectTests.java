package com.uniroma3.montorsmeds.TaskManager;
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
import com.uniroma3.montorsmeds.TaskManager.service.ProjectService;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProjectTests {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectService projectService;

	@Before
	public void deleteAll() {
		System.out.println("Deleting all data in db...");
		projectRepository.deleteAll();
		System.out.println("Fattoh!");
	}

	@Test
	public void aggiungiProjectTest() {
		Project project = new Project("Progetto", new User("Giulio", "Smedile", "smeds", "lacipolla"));
		project = projectService.saveProject(project);
		assertEquals(project.getProprietario().getNome(), "Giulio");
		assertEquals(project.getProprietario().getCognome(), "Smedile");
		assertEquals(project.getId(), 1);
	}
	
	@Test
	public void getProjectById() {
		Project project1 = new Project("ProgettoBello", new User("Giulio", "Smedile", "smeds", "lacipolla"));
		Project project2 = new Project("ProgettoBrutto", new User("Silvia", "Montorselli", "Fyu11", "sonobellissima"));
		
		projectService.saveProject(project1);
		projectService.saveProject(project2);
		
		assertEquals(projectService.getProject(1), project1);
		assertEquals(projectService.getProject(2), project2);
	}
	
	@Test
	public void getProjectByProprietario() {
		User user1 = new User("Giulio", "Smedile", "smeds", "lacipolla");
		Project project1 = new Project("ProgettoBello", user1);
		User user2 = new User("Silvia", "Montorselli", "Fyu11", "sonobellissima");
		Project project2 = new Project("ProgettoBrutto", user2);
		
		projectService.saveProject(project1);
		projectService.saveProject(project2);
		
		assertEquals(projectService.getProject("ProgettoBello", "Giulio"), project1);
		assertEquals(projectService.getProject("ProgettoBrutto", "Silvia"), project2);
	}
	
	@Test
	public void addMemberTest() {
		User user = new User("Giulio", "Smedile", "smeds", "lacipolla");
		Project project = new Project("ProgettoBello", user);
		
		projectService.addMember(project, user);
		
		assertEquals(projectService.getProject(1).getUtentiCondivisi().get(0), user);
	}
	
	
	
	


}
