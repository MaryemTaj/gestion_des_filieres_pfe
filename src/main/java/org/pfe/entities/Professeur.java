package org.pfe.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.pfe.entities.Module;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
@DiscriminatorValue("Professeur")
public class Professeur extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	public Professeur() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Professeur(@NotBlank(message = "ne doit pas etre null") String nom,
			@NotBlank(message = "ne doit pas etre null") String preNom,
			@NotBlank(message = "ne doit pas etre null") String password,
			@NotBlank(message = "ne doit pas etre null") String cin,
			@NotBlank(message = "ne doit pas etre null") String email) {
		super(nom, preNom, password, cin, email);
		// TODO Auto-generated constructor stub
	}

	@OneToMany(mappedBy = "prof")
	 @OnDelete(action = OnDeleteAction.CASCADE)
	@JsonBackReference
	private List<Liste_modules> liste_modules;
	
	
	

	
	
	
	
	public List<Liste_modules> getListe_modules() {
		return liste_modules;
	}

	public void setListe_modules(List<Liste_modules> liste_modules) {
		this.liste_modules = liste_modules;
	}

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

	@OneToMany(mappedBy = "professeur")
	private List<ReclamationEtudiant> reclamEtud;
	
	@OneToMany(mappedBy = "professeur")
	private List<ReclamationProf> reclamProf;
	
	
}
