package org.pfe.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@SQLDelete(sql = "UPDATE Departement SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Departement implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "nom must not be null")
	@Column(nullable = false)
	private String nom;
	@CreationTimestamp
	private LocalDateTime dateCreation;
	
	
	
	private boolean deleted = Boolean.FALSE;
	 @OneToMany(mappedBy="dept")
	    private Set<Filiere> filList=new HashSet<>();
	
	 

	public Departement(@NotNull(message = "nom must not be null") String nom) {
		super();
		this.nom = nom;
	}
	

	public Departement(Long id, @NotNull(message = "nom must not be null") String nom, Set<Filiere> filList) {
		super();
		this.id = id;
		this.nom = nom;
		this.filList = filList;
	}


	public Departement() {
		super();
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
