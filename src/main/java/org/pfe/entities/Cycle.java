package org.pfe.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
@Entity

@SQLDelete(sql = "UPDATE cycle SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Cycle implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	public LocalDateTime getDateCreation() {
		return dateCreation;
	}


	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}


	@Column(unique=true,nullable = false)
	private String nom;
	@CreationTimestamp
	private LocalDateTime dateCreation;
	private boolean deleted = Boolean.FALSE;
	
	
	
	@OneToMany(mappedBy="cyc")
	private Set<Filiere> filList=new HashSet<>();


	public Cycle() {
		super();
	}


	public Cycle(String nom) {
		super();
		this.nom = nom;
		
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public Set<Filiere> getFilList() {
		return filList;
	}


	public void setFilList(Set<Filiere> filList) {
		this.filList = filList;
	}


	public Long getId() {
		return id;
	}
	


	
	
	
}
