package org.pfe.entities;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
public class ReclamationEtudiant {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_recl_etud;
	@NotEmpty
	@Lob
	private String contenuReclEtud;
	
	@CreationTimestamp
	private LocalDateTime dateCreation;
	
	
	@ManyToOne
	@JsonBackReference
	private Etudiant etudiant;
	
	@ManyToOne	
	@JsonBackReference
	private Professeur professeur;
	

	public ReclamationEtudiant() {
		super();
	}
	public int getId_recl_etud() {
		return id_recl_etud;
	}
	public void setId_recl_etud(int id_recl_etud) {
		this.id_recl_etud = id_recl_etud;
	}
	public String getContenuReclEtud() {
		return contenuReclEtud;
	}
	public void setContenuReclEtud(String contenuReclEtud) {
		this.contenuReclEtud = contenuReclEtud;
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
	public LocalDateTime getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	

}
