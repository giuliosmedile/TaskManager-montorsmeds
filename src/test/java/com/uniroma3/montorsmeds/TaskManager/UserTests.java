package com.uniroma3.montorsmeds.TaskManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Before;

import com.uniroma3.montorsmeds.TaskManager.model.User;
import com.uniroma3.montorsmeds.TaskManager.repository.UserRepository;
import com.uniroma3.montorsmeds.TaskManager.service.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserTests {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;

	@Before
	@AfterEach
	public void deleteAll() {
		System.out.println("Deleting all data in db...");
		userRepository.deleteAll();
		System.out.println("Fattoh!");
	}

	@Test
	public void createUserTest() {
		User user = new User("Silvia", "Montorselli", "Fyu11", "sonobellissima");
		user = userService.saveUser(user);
		assertEquals("Silvia", user.getNome());
		assertEquals("Montorselli", user.getCognome());
		assertEquals("Fyu11", user.getUsername());
	}
	
	@Test
	public void updateUserTest() {
		User user = new User("Roberto", "Rossi", "mago99", "ciaone");
		user = userService.saveUser(user);
		user.setNome("Giulio");
		user.setCognome("Smedile");
		user = userService.saveUser(user);
		assertEquals("Giulio", user.getNome());
		assertEquals("Smedile", user.getCognome());
	}
	
	@Test
	public void getAllUsersTest() {
		User user1 = new User("Mario", "Verdi", "mverdi", "password123");
		User user2 = new User("Riccardo", "Marroni", "pollo", "padella");
		
		user1 = userService.saveUser(user1);
		user2 = userService.saveUser(user2);
		
		List<User> allUsers = userService.getAllUsers();
		assertEquals(allUsers.get(0).getNome(), "Mario");
		assertEquals(allUsers.get(1).getNome(), "Riccardo");		
	}
	
	public void getUserById() {
		User user1 = new User("Francesco", "Antonelli", "fanto", "perfezione");
		User user2 = new User("Giacomo", "Scavalcasedili", "lunotto", "123123");
		
		user1 = userService.saveUser(user1);
		user2 = userService.saveUser(user2);
		
		long id = user1.getId();
		
		assertEquals(userService.getUser(id).getNome(), user1.getNome());
	}
	


}
