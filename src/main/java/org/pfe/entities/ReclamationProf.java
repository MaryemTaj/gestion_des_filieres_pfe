package org.pfe.entities;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
@Entity
public class ReclamationProf {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_recl_prof;
	@NotEmpty
	@Lob
	private String contenuReclProf;
	
	@CreationTimestamp
	private LocalDateTime dateCreation;
	
	@ManyToOne
	private Etudiant etudiant;
	@ManyToOne	
	private Professeur professeur;
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ReclamationProf() {
		super();
	}

	public int getId_recl_prof() {
		return id_recl_prof;
	}
	public void setId_recl_prof(int id_recl_prof) {
		this.id_recl_prof = id_recl_prof;
	}
	
	

	public String getContenuReclProf() {
		return contenuReclProf;
	}

	public void setContenuReclProf(String contenuReclProf) {
		this.contenuReclProf = contenuReclProf;
	}

	
	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public Professeur getProfesseur() {
		return professeur;
	}

	public void setProfesseur(Professeur professeur) {
		this.professeur = professeur;
	}

	


	

}
