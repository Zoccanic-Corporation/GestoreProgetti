package it.uniroma3.siw.taskmanager.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tag {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 50, unique=true)
	private String nome;
	@Column(length = 20)
	private String colore;
	@Column(length = 100)
	private String descrizione;
    @ManyToMany(mappedBy="tags")//da controllare # tab di join
    private List<Task> tasks;
    @ManyToMany(mappedBy="tags")
    private List<Project> projects;
    
	

	public Tag() {
		this.tasks=new ArrayList<Task>();
	}

	public Tag(String nome,String colore,String descrizione) {
		this();
		this.nome=nome;
		this.colore=colore;
		this.descrizione=descrizione;
	}

	//GETTERS AND SETTERS
	public List<Project> getProjects() {
		return projects;
	}
		
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome=nome;
	}

	public String getColore() {
		return this.colore;
	}
	public void setColore(String colore) {
		this.colore=colore;
	}

	public String getDescrizione() {

		return this.descrizione;
	}
	public void setDescrizione(String desc) {
		this.descrizione=desc;
	}
	
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void addTask(Task task) {
		this.tasks.add(task);
	}
	//riguardo override di equals e hashCode per ora si suppone non ci siano bisogno di mappe etc..
}
