package org.pfe.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@SQLDelete(sql = "UPDATE module SET deleted = true WHERE id_module=?")
@Where(clause = "deleted=false")
public class Module {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_module;            
	private String nom;
	@CreationTimestamp
	private LocalDateTime dateCreation;
	
	
	private boolean deleted = Boolean.FALSE;
	
	@OneToMany (mappedBy="module")
	@JsonBackReference
	private List<Notes> notes;
	
	@OneToMany (mappedBy="module")
	@JsonBackReference
	private List<Liste_modules> liste_mode;
  
	@OneToMany (mappedBy="module" )
	@JsonBackReference
	private List<Cours> cours;
	
	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
	  
	  public List<Liste_modules> getListe_mode() {
		return liste_mode;
	}
	public void setListe_mode(List<Liste_modules> liste_mode) {
		this.liste_mode = liste_mode;
	}

	public List<Cours> getCours() {
		return cours;
	}
	public void setCours(List<Cours> cours) {
		this.cours = cours;
	}
	public Module() {
			super();
		}
	public Long getId_module() {
		return id_module;
	}
	public void setId_module(Long id_module) {
		this.id_module = id_module;
	}
	public Module(String nom) {
		super();
		this.nom = nom;
	}
	@Override
	public String toString() {
		return this.nom;
	}
	
	
	
	
	   


}
