package com.uniroma3.montorsmeds.TaskManager.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniroma3.montorsmeds.TaskManager.model.Tag;
import com.uniroma3.montorsmeds.TaskManager.repository.TagRepository;

@Service
public class TagService {
	
	@Autowired
	private TagRepository tagRepository;
	
	@Transactional
	public Tag findTagById(Long id) {
		Optional<Tag> result = this.tagRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public Tag saveTag(Tag tag) {
		return this.tagRepository.save(tag);
	}
	
	@Transactional
	public void updateTag(Tag tag) {
		Tag old = this.findTagById(tag.getId());
		old.setNome(tag.getNome());
		old.setDescrizione(tag.getDescrizione());
		old.setColore(tag.getColore());
		this.tagRepository.save(tag);
	}
	
}
