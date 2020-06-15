package it.uniroma3.siw.taskmanager.repository;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import it.uniroma3.siw.taskmanager.model.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long>{

	//(>>)da vedere dopo l'implementazione dei livelli superiori
	public List<Tag> findByNome (String nome);
}
