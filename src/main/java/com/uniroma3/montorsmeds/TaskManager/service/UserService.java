package com.uniroma3.montorsmeds.TaskManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.uniroma3.montorsmeds.TaskManager.model.Project;
import com.uniroma3.montorsmeds.TaskManager.model.User;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniroma3.montorsmeds.TaskManager.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	protected UserRepository userRepository;

	@Transactional
	public User getUser(long id) {
		Optional<User> result = this.userRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public User saveUser(User user) {
		return this.userRepository.save(user);
	}

	@Transactional
	public List<User> getAllUsers(){
		List<User> result = new ArrayList<>();
		Iterable<User> iterable = this.userRepository.findAll();
		for(User user : iterable) {
			result.add(user);
		}
		return result;
	}

	@Transactional
	public List<User> getMembers(Project project) {
		List<User> result = new ArrayList<>();
		Iterable<User> iterable = this.userRepository.findAll();
		for(User user : iterable) {
			if (project.getUtentiCondivisi().contains(user))
				result.add(user);
		}
		return result;
	}

}
