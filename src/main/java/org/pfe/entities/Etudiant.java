package org.pfe.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import groovy.transform.Generated;
@Entity
@DiscriminatorValue("Etudiant")
public class Etudiant extends User  implements Serializable {
	private static final long serialVersionUID = 1L;
	private String cne;

	@OneToMany(mappedBy = "etudiant")
	private List<ReclamationEtudiant> reclamEtud;
	@OneToMany(mappedBy = "etudiant")
	private List<ReclamationProf> reclamProf;
	
	@ManyToOne
	@JoinColumn(name = "id_filiere")
	@JsonBackReference
	private Filiere filiere;
	
	

	public List<ReclamationEtudiant> getReclamEtud() {
		return reclamEtud;
	}


	public void setReclamEtud(List<ReclamationEtudiant> reclamEtud) {
		this.reclamEtud = reclamEtud;
	}


	public List<ReclamationProf> getReclamProf() {
		return reclamProf;
	}


	public void setReclamProf(List<ReclamationProf> reclamProf) {
		this.reclamProf = reclamProf;
	}


	public Filiere getFiliere() {
		return filiere;
	}


	public void setFiliere(Filiere filiere) {
		this.filiere = filiere;
	}


	public Etudiant(@NotBlank(message = "ne doit pas etre null") String nom,
			@NotBlank(message = "ne doit pas etre null") String preNom,
			@NotBlank(message = "ne doit pas etre null") String password,
			@NotBlank(message = "ne doit pas etre null") String cin,
			@NotBlank(message = "ne doit pas etre null") String email, String cne) {
		super(nom, preNom, password, cin, email);
		this.cne = cne;
	}
	

	public Etudiant() {
		super();
	}


	public String getCne() {
		return cne;
	}

	public void setCne(String cne) {
		this.cne = cne;
	}
	

	

	




	

	
	
	

}
