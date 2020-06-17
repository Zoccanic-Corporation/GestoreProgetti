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
import it.uniroma3.siw.taskmanager.model.Task;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.services.ProjectService;
import it.uniroma3.siw.taskmanager.services.TaskService;
import it.uniroma3.siw.taskmanager.validator.TaskValidator;

@Controller
public class TaskController {
	@Autowired
	SessionData sessionData;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	TaskValidator taskValidator;
	
	@Autowired
	TaskService taskService;
	
	@RequestMapping(value = { "/projects/{projectId}/task/add"}, method = RequestMethod.GET)
	public String myNewTask(Model model, @PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		Project project = projectService.getProject(projectId);
		List<User> shareUsers = project.getMembers();
		
		shareUsers.add(project.getOwner());
		
		model.addAttribute("shareUsers", shareUsers);
		model.addAttribute("project", project);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("taskForm", new Task());
		return "addTask";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/task/add"}, method = RequestMethod.POST)
	public String createProject(@Valid @ModelAttribute("taskForm") Task task,
								BindingResult taskBindingResult,
								Model model, @PathVariable("projectId") Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		Project project = projectService.getProject(projectId);
		
		
		
		taskValidator.validate(task, taskBindingResult);
		if(!taskBindingResult.hasErrors()) {
			task.setCompleted(false);
			project.addTask(task);
			this.projectService.saveProject(project);
			return "redirect:/projects/"+project.getId();
		}
		model.addAttribute("shareUsers", project.getMembers());
		model.addAttribute("project", project);
		model.addAttribute("loggedUser", loggedUser);
		return "addTask";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/task/manage/{taskId}"}, method = RequestMethod.GET)
	public String myTask(Model model, @PathVariable("taskId") Long taskId, @PathVariable("projectId") Long projectId){
		
		//la validate riguarda chi può vedere il task (forse) perchè se chi puo vedere il proj puo gestire il task non c'è bisogno 
		Project project = projectService.getProject(projectId);
		Task task=taskService.getTask(taskId);
		User loggedUser = sessionData.getLoggedUser();
		
		//check ownership del progetto a cui il task è associato, ripetitivo ma impedisce che si acceda alla pagina di controllo del task tramite un URL "handmade" da chi non dovrebbe vedere il bottone "Gestisci"
		if(this.taskValidator.isOwner(project, loggedUser)){
		// è owner del task quindi ha accesso alla pagina
		model.addAttribute("project", project);
		model.addAttribute("task", task);		
		return "task";		
		}

		return "redirect:/projects/"+projectId;
	}
	
	@RequestMapping(value = {"/projects/{projectId}/task/del/{taskId}"}, method = RequestMethod.GET)
	public String deleteTask(Model model, @PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {
		Task task=taskService.getTask(taskId);
        this.taskService.deleteTask(task);
		return "redirect:/projects/"+projectId;
	}
	
	@RequestMapping(value = {"/projects/{projectId}/task/update/{taskId}"}, method = RequestMethod.GET)
	public String getUpdateForm(Model model, @PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {
		
	    Task task=taskService.getTask(taskId);
	    Project project=projectService.getProject(projectId);
	    
	    model.addAttribute("task", task);
        model.addAttribute("project", project);
		return "updateTask";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/task/update/{taskId}"}, method = RequestMethod.POST)
	public String setUpdate(Model model, @PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId, @ModelAttribute("task") Task newTask,BindingResult taskBindingResult) {
	    /*   
		System.out.println("-SSa ssa prova:");
	    System.out.println(newTask.getName());
		if(newTask.isCompleted())
		System.out.println("è completato");
		*/
            Task task=taskService.getTask(taskId);
            Project project=projectService.getProject(projectId);
            task=taskService.updateTask(task,newTask);            
            model.addAttribute("task", task);
            model.addAttribute("project", project);  		
			return "redirect:/projects/"+project.getId()+"/task/manage/"+task.getId();	
	}
	
}
