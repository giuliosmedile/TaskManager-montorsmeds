package com.uniroma3.montorsmeds.TaskManager.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniroma3.montorsmeds.TaskManager.model.Commento;
import com.uniroma3.montorsmeds.TaskManager.model.Task;
import com.uniroma3.montorsmeds.TaskManager.repository.CommentoRepository;

@Service
public class CommentoService {

	@Autowired
	private CommentoRepository commentoRepository;
	
	@Transactional
	public Commento saveCommento(Commento commento) {
		return this.commentoRepository.save(commento);
	}
	
	@Transactional
	public void removeCommento(Commento commento) {
		this.commentoRepository.delete(commento);
	}
	
	@Transactional
	public Commento getCommento(Long id) {
		Optional<Commento> result = this.commentoRepository.findById(id);
		return result.orElse(null);
	}
}
