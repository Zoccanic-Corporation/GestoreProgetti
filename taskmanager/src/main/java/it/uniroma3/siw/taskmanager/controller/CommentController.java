package it.uniroma3.siw.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.model.Comment;
import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.Task;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.services.CommentService;
import it.uniroma3.siw.taskmanager.services.ProjectService;
import it.uniroma3.siw.taskmanager.services.TaskService;
import it.uniroma3.siw.taskmanager.validator.CommentValidator;

@Controller
public class CommentController {
	@Autowired
	SessionData sessionData;

	@Autowired
	ProjectService projectService;

	@Autowired
	TaskService taskService;

	@Autowired
	CommentValidator commentValidator;

	@Autowired
	CommentService commentService;

	@RequestMapping(value = { "/projects/{projectId}/task/comment/{taskId}"}, method = RequestMethod.GET)
	public String taskComment(Model model, @PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {
		Project project = projectService.getProject(projectId);
		Task task=taskService.getTask(taskId);
		//User loggedUser = sessionData.getLoggedUser();

		List<Comment> comments = commentService.getAllComments(task);
		model.addAttribute("comments", comments);
		model.addAttribute("project", project);
		model.addAttribute("task", task);
		model.addAttribute("commentForm", new Comment());
		return "commentPage";
	}

	@RequestMapping(value = { "/projects/{projectId}/task/comment/{taskId}"}, method = RequestMethod.POST)
	public String taskPostComment(Model model, @PathVariable("projectId") Long projectId, 
			@PathVariable("taskId") Long taskId, @ModelAttribute("commentForm") Comment comment,
			BindingResult commentBindingResult) {
		
		User loggUser = sessionData.getLoggedUser();
		Project project = projectService.getProject(projectId);
		Task task = taskService.getTask(taskId);
		
		
		commentValidator.validate(comment, commentBindingResult);
		if(!commentBindingResult.hasErrors()) {
			comment.setUser(loggUser);
			comment.setTask(task);
			this.commentService.saveComment(comment);
			return "redirect:/projects/"+project.getId()+"/task/comment/" + taskId;
		}
		model.addAttribute("comments", commentService.getAllComments(task));
		model.addAttribute("project", project);
		model.addAttribute("loggedUser", loggUser);
		return "commentPage";
	}
}
