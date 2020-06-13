package com.uniroma3.montorsmeds.TaskManager.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.uniroma3.montorsmeds.TaskManager.model.Credentials;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

	public Optional<Credentials> findByUsername(String username);
}
