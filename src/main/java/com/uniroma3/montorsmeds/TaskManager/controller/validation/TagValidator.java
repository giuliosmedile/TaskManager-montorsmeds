package com.uniroma3.montorsmeds.TaskManager.controller.validation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.uniroma3.montorsmeds.TaskManager.model.Project;
import com.uniroma3.montorsmeds.TaskManager.model.Tag;
import com.uniroma3.montorsmeds.TaskManager.model.Task;


@Component
public class TagValidator implements Validator {

	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;
	final Integer MAX_DESCRIPTION_LENGTH = 1000;
	
	@Override
	public void validate(Object o, Errors errors) {
		Tag tag = (Tag)o;
		String nome = tag.getNome().trim();
		String descrizione = tag.getDescrizione().trim();
		if (nome.trim().isEmpty()) {
			errors.rejectValue("nome", "required");
		} 
		else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH) {
			errors.rejectValue("nome", "size");
		}
		if (descrizione.length() > MAX_DESCRIPTION_LENGTH) {
			errors.rejectValue("descrizione", "size");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Project.class.equals(clazz);
	}
	
}
