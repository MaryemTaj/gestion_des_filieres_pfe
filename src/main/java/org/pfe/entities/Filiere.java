package org.pfe.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@SQLDelete(sql = "UPDATE filiere SET deleted = true WHERE id_filiere=?")
@Where(clause = "deleted=false")
public class Filiere implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_filiere;
	@NotBlank(message = "ne doit pas etre null" )
	@NotNull
	private String nom;
	
	@CreationTimestamp
	private LocalDateTime dateCreation;
	
	private boolean deleted = Boolean.FALSE;
	
	
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch =FetchType.EAGER)
    @JoinColumn(name="dept_id")
	@NotNull(message="ne doit pas etre null")
	@JsonBackReference
    private Departement dept;

	
	
	
	@OneToMany(mappedBy = "filiere")
	@JsonBackReference
	private List<Liste_modules> liste_modules;
	
	
	
	

	@ManyToOne
    @JoinColumn(name="cycle_id")
	@JsonBackReference
    private Cycle cyc;
	
	public List<Etudiant> getEtudiants() {
		return etudiants;
	}


	public void setEtudiants(List<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}


	public void setId_filiere(Long id_filiere) {
		this.id_filiere = id_filiere;
	}

	@OneToMany
	private List<Etudiant> etudiants;

	public Filiere(@NotBlank(message = "ne doit pas etre null") @NotNull String nom,
			@NotNull(message = "la date ne doit pas etre null") Departement dept, Cycle cyc) {
		super();
		this.nom = nom;
		
		this.dept = dept;
		this.cyc = cyc;
	}

	
	public Filiere() {
		super();
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	


	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Departement getDept() {
		return dept;
	}


	public void setDept(Departement dept) {
		this.dept = dept;
	}


	public Cycle getCyc() {
		return cyc;
	}


	public void setCyc(Cycle cyc) {
		this.cyc = cyc;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<Liste_modules> getListe_modules() {
		return liste_modules;
	}

	public void setListe_modules(List<Liste_modules> liste_modules) {
		this.liste_modules = liste_modules;
	}

	public Long getId_filiere() {
		return id_filiere;
	}


	
	
	

}
