package it.uniroma3.siw.taskmanager.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.Tag;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.repository.ProjectRepository;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRepository;
	
	@Transactional
	public Project getProject(Long id) {
		Optional<Project> result = this.projectRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public Project saveProject(Project project) {
		return this.projectRepository.save(project);
	}
	
	@Transactional
	public void deleteProject(Project project) {
		this.projectRepository.delete(project);
	}
	
	@Transactional
	public Project shareProjectWithUser(Project project, User user) {
		project.addMember(user);
		return this.projectRepository.save(project);
	}
	 
	

	@Transactional
	public List<Project> retrieveProjectsOwnedBy(User user){
		return this.projectRepository.findByOwner(user);
	}
	
	@Transactional
	public Project addTag(Project project, Tag tag) {
		project.addTag(tag);
		return this.projectRepository.save(project);
	}
	
	@Transactional
	public List<Project> findByUserVisibleProjects(User user){
		return this.projectRepository.findByMembers(user);
	}
}
