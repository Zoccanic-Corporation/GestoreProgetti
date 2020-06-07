package it.uniroma3.siw.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.micrometer.core.ipc.http.HttpSender.Method;
import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.services.CredentialsService;

@Controller
public class MainController
{
	@Autowired
	SessionData sessionData;
	
	@Autowired
	CredentialsService credentialsService;
	
	public MainController() {
    }

    /**
     * This method is called when a GET request is sent by the user to URL "/" or "/index".
     * This method prepares and dispatches the index view.
     *
     * @param model the Request model
     * @return the name of the target view, that in this case is "index"
     */
	@RequestMapping(value= {"/", "/index"}, method = RequestMethod.GET)
	public String index(Model model)
	{
		return "index";
	}
	
	@RequestMapping(value = {"/admin/users"}, method = RequestMethod.GET)
	public String usersList(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Credentials> allCredentials = this.credentialsService.getAllCredentials();
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("credentialsList", allCredentials);
		
		return "allUsers";
	}
	
	@RequestMapping(value = { "/admin/users/{username}/delete"}, method = RequestMethod.POST)
	public String removeUser(Model model, @PathVariable String username) {
		this.credentialsService.deleteCredentials(username);
		return "redirect:/admin/users";
	}
	
	
}
	
