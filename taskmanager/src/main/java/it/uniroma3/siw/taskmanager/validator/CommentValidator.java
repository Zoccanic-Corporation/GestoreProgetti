package it.uniroma3.siw.taskmanager.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.taskmanager.model.Comment;

@Component
public class CommentValidator implements Validator {
	final Integer MAX_TEXT_LENGHT = 1000;
	final Integer MIN_TEXT_LENGHT = 2;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Comment.class.equals(clazz);
	}
	@Override
	public void validate(Object c, Errors errors) { 
		Comment comment = (Comment) c;
		String text = comment.getText().trim();
		
		if(text.isEmpty()) {
			errors.rejectValue("name", "required");
		}
		else if(text.length() < MIN_TEXT_LENGHT || text.length() > MAX_TEXT_LENGHT)
			errors.rejectValue("name", "size");
		
	}
	
	
}
