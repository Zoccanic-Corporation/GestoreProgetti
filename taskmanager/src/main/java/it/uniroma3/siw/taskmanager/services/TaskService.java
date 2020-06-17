package it.uniroma3.siw.taskmanager.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.taskmanager.model.Tag;
import it.uniroma3.siw.taskmanager.model.Task;
import it.uniroma3.siw.taskmanager.model.Comment;

import it.uniroma3.siw.taskmanager.repository.TaskRepository;

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
	public Task updateTask(Task oldTask, Task newTask) {
		oldTask.setCompleted(newTask.isCompleted());
		if(!newTask.getName().isEmpty())
		oldTask.setName(newTask.getName());
		if(!newTask.getDescription().isEmpty())
		oldTask.setDescription(newTask.getDescription());	
		return this.taskRepository.save(oldTask);
	}

}
