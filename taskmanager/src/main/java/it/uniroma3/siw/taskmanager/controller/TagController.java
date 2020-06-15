package it.uniroma3.siw.taskmanager.controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.model.Tag;
import it.uniroma3.siw.taskmanager.services.TagService;
import it.uniroma3.siw.taskmanager.validator.TagValidator;

@Controller
public class TagController {

	@Autowired
	SessionData sessionData;
	
	@Autowired
	TagValidator tagValidator;
	
	@Autowired
	TagService tagService;
	
	@RequestMapping(value="/tag/add", method= RequestMethod.GET)	
	public String tagInitialization(Model model) {
		model.addAttribute("tagForm", new Tag());
		return "addTag";
	}

	@RequestMapping(value="/tag/add", method= RequestMethod.POST)	
	public String tagCreation(@Valid @ModelAttribute("tagForm") Tag tag, Model model, BindingResult tagBindingResult) {
		tagValidator.validate(tag, tagBindingResult);
		if(!tagBindingResult.hasErrors()) {
			this.tagService.saveTag(tag);
			model.addAttribute("confermaInsTag", true);
			return "redirect:/home";
		}
		model.addAttribute("tagForm", tag);
		return "addTag";
	}
}
