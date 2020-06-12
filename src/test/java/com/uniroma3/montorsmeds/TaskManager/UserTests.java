package com.uniroma3.montorsmeds.TaskManager;
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
		assertEquals(1, user.getId());		
	}
	
	@Test
	public void updateUserTest() {
		User user = new User("Silvia", "Montorselli", "Fyu11", "sonobellissima");
		user = userService.saveUser(user);
		user.setNome("Giulio");
		user.setCognome("Smedile");
		user = userService.saveUser(user);
		assertEquals("Giulio", user.getNome());
		assertEquals("Smedile", user.getCognome());
	}
	
	@Test
	public void getAllUsersTest() {
		User user1 = new User("Silvia", "Montorselli", "Fyu11", "sonobellissima");
		User user2 = new User("Giulio", "Smedile", "smeds", "lacipolla");
		
		userService.saveUser(user1);
		userService.saveUser(user2);
		
		List<User> allUsers = userService.getAllUsers();
		assertEquals(allUsers.get(0).getNome(), "Silvia");
		assertEquals(allUsers.get(1).getNome(), "Giulio");		
	}
	
	public void getUserById() {
		User user1 = new User("Silvia", "Montorselli", "Fyu11", "sonobellissima");
		User user2 = new User("Giulio", "Smedile", "smeds", "lacipolla");
		
		userService.saveUser(user1);
		userService.saveUser(user2);
		
		assertEquals(userService.getUser(1), user1);
	}
	


}
