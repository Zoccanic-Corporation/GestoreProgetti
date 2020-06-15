package it.uniroma3.siw.taskmanager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.taskmanager.model.Project;
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

@Transactional
public Tag getTag(Long id) {
	Optional<Tag> result = this.tagRepository.findById(id);
	return result.orElse(null);
}

@Transactional
public List<Tag> getTagByName(String name) {
	return tagRepository.findByNome(name);	
}

@Transactional
public List<Tag> getAllTags() {
    List<Tag> result = new ArrayList<>();
    Iterable<Tag> iterable = this.tagRepository.findAll();
    for(Tag tag : iterable)
        result.add(tag);
    return result;
}

@Transactional
public List<Tag> TagsNotInProject(Project project){
    List<Tag>  allTags= this.getAllTags();
    List<Tag>  tagsOfProject= project.getTags();
    if(tagsOfProject.size() != 0) {
        for(Tag t: tagsOfProject)
            allTags.remove(t);
    }
    return allTags;
}



}
