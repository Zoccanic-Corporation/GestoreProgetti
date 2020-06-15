package it.uniroma3.siw.taskmanager.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.services.ProjectService;
import it.uniroma3.siw.taskmanager.services.UserService;
import it.uniroma3.siw.taskmanager.validator.TaskValidator;

@Controller
public class VisibilityController {
	@Autowired
	SessionData sessionData;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	TaskValidator taskValidator;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = { "/projects/{projectId}/user/add"}, method = RequestMethod.GET)
	public String myNewTask(Model model, @PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		Project project = projectService.getProject(projectId);
		List<User> users = userService.UsersToShareProject(project, loggedUser);
		
		model.addAttribute("project", project);
		
		if(!loggedUser.equals(project.getOwner()))
			return "accessDenied.html";
		
		
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("users", users);
		return "addUsers";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/user/add/{username}"}, method = RequestMethod.POST)
	public String createProject(Model model, @PathVariable("projectId") Long projectId, @PathVariable("username") Long userId) {
		Project project = projectService.getProject(projectId);
		//User loggedUser = sessionData.getLoggedUser();
		User user = userService.getUser(userId);
		//condividi il progetto all'utente
		this.projectService.shareProjectWithUser(project, user);
		//ricalcola la lista delle persone con cui condividire il progetto
		//List<User> users = userService.UsersToShareProject(project, loggedUser);
	
		//model.addAttribute("project", project);
		//model.addAttribute("loggedUser", loggedUser);
		//model.addAttribute("users", users);
		return "redirect:/projects/" + project.getId()+ "/user/add";
		
		
	}
}
