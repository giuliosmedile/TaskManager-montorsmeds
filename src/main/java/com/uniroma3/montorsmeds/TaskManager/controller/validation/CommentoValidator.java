package com.uniroma3.montorsmeds.TaskManager.controller.validation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.uniroma3.montorsmeds.TaskManager.model.Commento;
import com.uniroma3.montorsmeds.TaskManager.model.Project;
import com.uniroma3.montorsmeds.TaskManager.model.Task;


@Component
public class CommentoValidator implements Validator {

	final Integer MIN_LENGTH = 2;
	final Integer MAX_LENGTH = 1000;
	
	@Override
	public void validate(Object o, Errors errors) {
		Commento commento = (Commento)o;
		String text = commento.getText().trim();
		if (text.length() < MIN_LENGTH || text.length() > MAX_LENGTH) {
			errors.rejectValue("nome", "size");
		}		
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Project.class.equals(clazz);
	}
	
}
