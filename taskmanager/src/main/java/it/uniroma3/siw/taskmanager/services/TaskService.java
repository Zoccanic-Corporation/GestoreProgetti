package it.uniroma3.siw.taskmanager.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.taskmanager.model.Tag;
import it.uniroma3.siw.taskmanager.model.Task;

import it.uniroma3.siw.taskmanager.repository.TaskRepository;
import it.uniroma3.siw.taskmanager.validator.TaskValidator;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;
	
	@Transactional
	public Task getTask(Long id) {
		Optional<Task> result = this.taskRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public void deleteTask(Task task) {
		this.taskRepository.delete(task);
	}
	
	@Transactional
	public Task saveTask(Task task) {
		return this.taskRepository.save(task);
	}
	
	@Transactional
	public Task setCompleted(Task task) {
		task.setCompleted(true);
		return this.taskRepository.save(task);
	}
	
	@Transactional
	public Task addTag(Task task, Tag tag) {
		task.addTag(tag);
		return this.taskRepository.save(task);
	}
	
	@Transactional
	public Task updateTask(Task oldTask, Task newTask, TaskValidator v) {
		oldTask.setCompleted(newTask.isCompleted());
		String n=newTask.getName();
		String d=newTask.getDescription();
		if(!n.isEmpty()&&v.validName(n))
		oldTask.setName(newTask.getName());
		if(!d.isEmpty()&&v.validDescription(d))
		oldTask.setDescription(newTask.getDescription());	
		return this.taskRepository.save(oldTask);
	}

}
