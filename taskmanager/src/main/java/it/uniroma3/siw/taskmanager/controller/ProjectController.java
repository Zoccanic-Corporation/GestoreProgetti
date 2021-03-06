package it.uniroma3.siw.taskmanager.controller;

import java.util.List;

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
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.services.ProjectService;
import it.uniroma3.siw.taskmanager.services.UserService;
import it.uniroma3.siw.taskmanager.validator.ProjectValidator;

@Controller
public class ProjectController {
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProjectValidator projectValidator;
	
	@Autowired
	SessionData sessionData;
	
	@RequestMapping(value = { "/projects"}, method = RequestMethod.GET)
	public String myOwnedProjects(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Project> projectsList = projectService.retrieveProjectsOwnedBy(loggedUser);
		List<Project> projectShare = projectService.findByUserVisibleProjects(loggedUser);
		/*Da capire problema di loggedUser.getVisibleProject() che contiene doppioni dello stesso progetto*/
		
		
		model.addAttribute("projectsShare", projectShare);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectsList", projectsList);
		return "myOwnedProjects";
	}
	
	@RequestMapping(value = {"/projects/{projectId}"}, method = RequestMethod.GET)
	public String project(Model model,
						  @PathVariable Long projectId) {
		
		
		User loggedUser = sessionData.getLoggedUser();
		Project project = projectService.getProject(projectId);
		
		if(project == null)
			return "redirect:/projects";
		
		
		List<User> members = userService.getMembers(project);
		if(!project.getOwner().equals(loggedUser) && !members.contains(loggedUser))
			return "redirect:/projects";
		
		if(project.getOwner().equals(loggedUser))//diplay bottone "Gestisci"
			model.addAttribute("gestione", "ok");
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project", project);
		model.addAttribute("members", members);

		return "project";
	}
	
	@RequestMapping(value = {"/projects/add"}, method = RequestMethod.GET)
	public String createProjectForm(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectForm", new Project());
		return "addProject";
	}
	
	@RequestMapping(value = {"/projects/add"}, method = RequestMethod.POST)
	public String createProject(@Valid @ModelAttribute("projectForm") Project project,
								BindingResult projectBindingResult,
								Model model) {
		User loggedUser = sessionData.getLoggedUser();
		
		projectValidator.validate(project, projectBindingResult);
		if(!projectBindingResult.hasErrors()) {
			project.setOwner(loggedUser);
			this.projectService.saveProject(project);
			return "redirect:/projects/" + project.getId();
		}
		model.addAttribute("loggedUser", loggedUser);
		return "addProject";
	}
	

}
