package it.uniroma3.siw.taskmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tag {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 50)
	private String nome;
	@Column(length = 20)
	private String colore;
	@Column(length = 200)
	private String descrizone;

	public Tag(String nome,String colore,String descrizione) {
		this.nome=nome;
		this.colore=colore;
		this.descrizone=descrizione;
	}

	//GETTERS AND SETTERS

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

		return this.descrizone;
	}
	public void setDescrizione(String desc) {
		this.descrizone=desc;
	}
	//riguardo override di equals e hashCode per ora si suppone non ci siano bisogno di mappe etc..
}
