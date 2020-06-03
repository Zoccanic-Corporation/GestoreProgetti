package it.uniroma3.siw.taskmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController
{
	@RequestMapping(value="/")
	public String getIndex(Model model)
	{
		model.addAttribute("Titolo", "Ho fatto un titolo dinamico");
		return "index";
	}
}