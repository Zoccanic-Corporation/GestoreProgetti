package it.uniroma3.siw.taskmanager.controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.Tag;
import it.uniroma3.siw.taskmanager.model.Task;
import it.uniroma3.siw.taskmanager.services.ProjectService;
import it.uniroma3.siw.taskmanager.services.TagService;
import it.uniroma3.siw.taskmanager.services.TaskService;
import it.uniroma3.siw.taskmanager.validator.TagValidator;

@Controller
public class TagController {

	@Autowired
	SessionData sessionData;

	@Autowired
	TagValidator tagValidator;

	@Autowired
	TagService tagService;

	@Autowired
	ProjectService projectService;
	
	@Autowired
	TaskService taskService;

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
			//model.addAttribute("confermaInsTag", true); useless
			return "redirect:/home";//da fare un redirect su una pagina di conferma creazione con home/creane un altro
		}
		model.addAttribute("tagForm", tag);
		return "addTag";
	}

	@RequestMapping(value="/projects/{projectId}/tag/add", method= RequestMethod.GET)	
	public String tagListIni(Model model, @PathVariable("projectId") Long projectId) {
		Project project = projectService.getProject(projectId);
		List<Tag> tags=tagService.TagsNotInProject(project);//devo fare un metodo che mi restituisca solo i tag non presenti nel progetto
		model.addAttribute("project", project);
		model.addAttribute("tagsList", tags);
		return "tagList";
	}

	@RequestMapping(value="/projects/{projectId}/tag/add/{tagId}", method= RequestMethod.POST)		
	public String selectTag(Model model, @PathVariable("tagId") Long tagId, @PathVariable("projectId") Long projectId) {
		Project project = projectService.getProject(projectId);
		Tag tag = tagService.getTag(tagId);
		projectService.addTag(project, tag);
        model.addAttribute("project", project);
		return "redirect:/projects/"+projectId+"/tag/add"; 
	}

	@RequestMapping(value="/projects/{projectId}/task/manage/{taskId}/tag/add", method= RequestMethod.GET)	
	public String getTagsToTask(Model model, @PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {
		Project project = projectService.getProject(projectId);		
		Task task=taskService.getTask(taskId);
		List<Tag> tags=tagService.TagsInProjectNotinTask(project,task);
		
		model.addAttribute("task", task);
		model.addAttribute("project", project);
		model.addAttribute("projectTags", tags);
		
		return "addTagToTask";
	}

	@RequestMapping(value="/projects/{projectId}/task/manage/{taskId}/tag/add/{tagId}", method= RequestMethod.POST)	
	public String setTagToTask(Model model, @PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId, @PathVariable("tagId") Long tagId) {

	Task task=taskService.getTask(taskId);
	Tag tag = tagService.getTag(tagId);
		taskService.addTag(task, tag);
		tagService.addTask(tag, task);

		return "redirect:/projects/"+projectId+"/task/manage/"+taskId+"/tag/add";
	}
}

