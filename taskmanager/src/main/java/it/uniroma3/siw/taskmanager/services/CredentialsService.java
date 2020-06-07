package it.uniroma3.siw.taskmanager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.repository.CredentialsRepository;

/**
 * La classe Service che gestisce la logica relativa a Credentials
 * Si occupa quindi di recuperare o salvare le istanze Credentials nel DB
 *
 */
@Service
public class CredentialsService {
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	protected CredentialsRepository credentialsRepository;
	
	@Transactional
	public Credentials getCredentials(Long id) {
		Optional<Credentials> result = this.credentialsRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public Credentials getCredentials(String username) {
		Optional<Credentials> result = this.credentialsRepository.findByUserName(username);
		return result.orElse(null);
	}
	
	@Transactional
	public Credentials saveCredentials(Credentials credentials) {
		credentials.setRole(Credentials.DEFAULT_ROLE);
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		return this.credentialsRepository.save(credentials);
	}
	
	@Transactional
	public List<Credentials> getAllCredentials(){
		List<Credentials> result = new ArrayList<>();
        Iterable<Credentials> iterable = this.credentialsRepository.findAll();
        for(Credentials credentials : iterable)
            result.add(credentials);
        return result;
	}
	
	@Transactional
	public void deleteCredentials(String username) {
		Optional<Credentials> credentials = this.credentialsRepository.findByUserName(username);
		if(credentials.isPresent())
			this.credentialsRepository.delete(credentials.get());
	}
}
