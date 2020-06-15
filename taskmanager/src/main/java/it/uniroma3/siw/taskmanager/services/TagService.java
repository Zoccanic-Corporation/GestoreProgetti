package it.uniroma3.siw.taskmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.taskmanager.model.Tag;
import it.uniroma3.siw.taskmanager.repository.TagRepository;

@Service
public class TagService {
@Autowired
private TagRepository tagRepository;

@Transactional
public Tag saveTag(Tag tag) {
	return this.tagRepository.save(tag);
}

}
