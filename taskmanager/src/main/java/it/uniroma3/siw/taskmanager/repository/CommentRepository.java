package it.uniroma3.siw.taskmanager.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.taskmanager.model.Comment;
import it.uniroma3.siw.taskmanager.model.Task;



@Repository
public interface CommentRepository extends CrudRepository<Comment, Long>{
	public List<Comment> findAllByTask(Task task);
	
}