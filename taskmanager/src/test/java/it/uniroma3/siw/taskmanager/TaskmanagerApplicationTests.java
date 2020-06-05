package it.uniroma3.siw.taskmanager;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.repository.ProjectRepository;
import it.uniroma3.siw.taskmanager.repository.TaskRepository;
import it.uniroma3.siw.taskmanager.repository.UserRepository;
import it.uniroma3.siw.taskmanager.services.ProjectService;
import it.uniroma3.siw.taskmanager.services.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
class TaskmanagerApplicationTests {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserService userService;

	@Autowired
	private ProjectService projectService;
	
	@Before
	public void deleteAll() {
		System.out.println("Deleting all data...");
		this.userRepository.deleteAll();
		this.taskRepository.deleteAll();
		this.projectRepository.deleteAll();
		System.out.println("Done.");
		
	}
	@Test
	public void testUpdateUser() {
		User user1 = new User("Mario", "Rossi");
		user1 = userService.saveUser(user1);
		assertEquals(user1.getId().longValue(), 1L);
		assertEquals(user1.getFirstName(), "Mario");
		
		User user2 = new User("Luca", "Bianchi");
		user2 = userService.saveUser(user2);
		assertEquals(user2.getId().longValue(), 2L);
		assertEquals(user2.getFirstName(), "Luca");
		
		User user1Update = new User("Maria", "Rossi");
		user1Update.setId(user1.getId());
		user1 = userService.saveUser(user1Update);
		assertEquals(user1Update.getId().longValue(), 1L);
		assertEquals(user1Update.getFirstName(), "Maria");
		
		Project project1 = new Project("testproject1", "è il testproject1");
		project1.setOwner(user1Update);
		project1 = projectService.saveProject(project1);
		assertEquals(project1.getOwner(), user1Update);
		assertEquals(project1.getName(), "testproject1");
		
		Project project2 = new Project("testproject2", "questo invece è il testproject2");
		project2.setOwner(user1Update);
		project2 = projectService.saveProject(project2);
		assertEquals(project2.getOwner(), user1Update);
		assertEquals(project2.getName(), "testproject2");
		
		project1 = projectService.shareProjectWithUser(project1, user2);
		List<Project> projects = projectRepository.findByOwner(user1Update);
		assertEquals(projects.size(), 2);
		assertEquals(projects.get(0), project1);
		assertEquals(projects.get(1), project2);
		
		List<Project> projectVisibleByUser2 = projectRepository.findByMembers(user2);
		assertEquals(projectVisibleByUser2.size(), 1);
		assertEquals(projectVisibleByUser2.get(0), project1);
		
		List<User> project1Members = userRepository.findByVisibleProjects(project1);
		assertEquals(project1Members.size(), 1);
		assertEquals(project1Members.get(0), user2);
		
		
	}
	@Test
	void contextLoads() {
	}

}
