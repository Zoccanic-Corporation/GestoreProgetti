package it.uniroma3.siw.taskmanager.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.Task;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.services.ProjectService;
import it.uniroma3.siw.taskmanager.validator.TaskValidator;

@Controller
public class TaskController {
	@Autowired
	SessionData sessionData;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	TaskValidator taskValidator;
	
	@RequestMapping(value = { "/projects/{projectId}/task/add"}, method = RequestMethod.GET)
	public String myNewTask(Model model, @PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		Project project = projectService.getProject(projectId);
		
		model.addAttribute("project", project);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("taskForm", new Task());
		return "addTask";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/task/add"}, method = RequestMethod.POST)
	public String createProject(@Valid @ModelAttribute("taskForm") Task task,
								BindingResult taskBindingResult,
								Model model, @PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		Project project = projectService.getProject(projectId);
		
		taskValidator.validate(task, taskBindingResult);
		if(!taskBindingResult.hasErrors()) {
			task.setCompleted(false);
			project.addTask(task);
			this.projectService.saveProject(project);
			return "redirect:/projects/" + project.getId();
		}
		model.addAttribute("project", project);
		model.addAttribute("loggedUser", loggedUser);
		return "addTask";
	}
}
