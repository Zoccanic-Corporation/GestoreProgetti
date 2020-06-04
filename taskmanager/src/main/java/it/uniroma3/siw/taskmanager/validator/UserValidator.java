package it.uniroma3.siw.taskmanager.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.services.UserService;

@Component
public class UserValidator implements Validator {
	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;
	
	@Autowired
    UserService userService;
	
	@Override
	public void validate(Object o, Errors errors) {
		User user = (User)o;
		String firsName = user.getFirstName().trim();
		String lastName = user.getLastName().trim();

		if(firsName.isEmpty()) 
			errors.rejectValue("firstName", "required");
		else if(firsName.length() < MIN_NAME_LENGTH || firsName.length() > MAX_NAME_LENGTH)
				errors.rejectValue("firstName", "size");
		
		if(lastName.isEmpty()) 
			errors.rejectValue("lastName", "required");
		else if(lastName.length() < MIN_NAME_LENGTH || lastName.length() > MAX_NAME_LENGTH)
				errors.rejectValue("lastName", "size");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}
}
