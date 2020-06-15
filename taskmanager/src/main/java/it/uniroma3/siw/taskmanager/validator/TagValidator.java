package it.uniroma3.siw.taskmanager.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.taskmanager.model.Tag;
import it.uniroma3.siw.taskmanager.services.TagService;

@Component
public class TagValidator implements Validator{
	final Integer MAX_NAME_LENGHT = 50;
	final Integer MIN_NAME_LENGHT = 2;
	final Integer MAX_DESCRIPTION_LENGHT = 100;
	
	@Autowired
	TagService tagService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Tag.class.equals(clazz);
	}
	
	@Override
	public void validate(Object o, Errors errors) {
    Tag tag=(Tag) o;
    String name = tag.getNome().trim();
	String description = tag.getDescrizione().trim();
	
	if(name.isEmpty()) {
		errors.rejectValue("nome", "required");
	}
	else if(name.length() < MIN_NAME_LENGHT || name.length() > MAX_NAME_LENGHT)
		errors.rejectValue("nome", "size");
	else if(!this.isValid(name)) {
		errors.rejectValue("nome", "duplicate");
	}
	
	if(description.length() > MAX_DESCRIPTION_LENGHT)
		errors.rejectValue("descrizione", "size");
	}
	
	public boolean isValid(String nome) {
		return tagService.getTagByName(nome).isEmpty();
	}
	
}
