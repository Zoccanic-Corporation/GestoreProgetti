package it.uniroma3.siw.taskmanager.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.taskmanager.model.Task;

@Component
public class TaskValidator implements Validator {
	
	final Integer MAX_NAME_LENGHT = 100;
	final Integer MIN_NAME_LENGHT = 2;
	final Integer MAX_DESCRIPTION_LENGHT = 1000;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Task.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Task task = (Task) o;
		String name = task.getName().trim();
		String description = task.getDescription().trim();
		
		if(name.isEmpty()) {
			errors.rejectValue("name", "required");
		}
		else if(name.length() < MIN_NAME_LENGHT || name.length() > MAX_NAME_LENGHT)
			errors.rejectValue("name", "size");
		
		if(description.length() > MAX_DESCRIPTION_LENGHT)
			errors.rejectValue("description", "size");
	}

}
