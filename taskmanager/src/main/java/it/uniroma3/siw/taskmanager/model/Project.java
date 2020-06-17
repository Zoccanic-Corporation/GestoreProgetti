package it.uniroma3.siw.taskmanager.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	private User owner;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<User> members;

	@OneToMany//(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Tag> tags;

	@Column
	private String description;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="project_id")
	private List<Task> tasks;

	public Project() {
		this.members = new ArrayList<User>();
		this.tasks = new ArrayList<Task>();
		this.tags = new ArrayList<Tag>();
	}

	public Project(String name, String description) {
		this();
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
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

	public void addTag(Tag tag) {
		this.tags.add(tag);
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public void addMember(User member) {
		this.members.add(member);
	}

	@Override
	public String toString() {

		return "Project{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", tasks=" + tasks +
				'}';
	}

	// this is a semplification
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Project project = (Project) o;
		return Objects.equals(name, project.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}