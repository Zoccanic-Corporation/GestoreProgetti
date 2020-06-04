package it.uniroma3.siw.taskmanager.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.taskmanager.model.Credentials;

/**
 * 
 * 
 * @see Credentials
 *
 */
@Repository
public interface CredentialsRepository extends CrudRepository<Credentials, Long>{
	/**
	 * Ritorna le Credenziali usando Username
	 * @param username parametro passato alla funzione
	 * @return oggetto Opzionale per le Credenziali relativo allo username passato 
	 */
	
	public Optional<Credentials> findByUserName(String username);

}
