package org.pfe.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
@SQLDelete(sql = "UPDATE list_modules SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Liste_modules implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public void setFiliere(Filiere filiere) {
		this.filiere = filiere;
	}
	
	
	private boolean deleted = Boolean.FALSE;
	@EmbeddedId
	private Liste_moduleId liste_moduelId;
	private String semestre_mod;
	
	
	
	@ManyToOne
	 @OnDelete(action = OnDeleteAction.CASCADE)
	@MapsId("idProf")
	@JsonBackReference
	@JoinColumn
	private Professeur prof;
	
	
	
	
	@ManyToOne
	 @OnDelete(action = OnDeleteAction.CASCADE)
	@MapsId("idModule")
	@JsonBackReference
	@JoinColumn(name="id_module",referencedColumnName = "id_module", insertable = false, updatable = false)

	private Module module;
	@ManyToOne
	 @OnDelete(action = OnDeleteAction.CASCADE)
	@MapsId("idFiliere")
	@JsonBackReference
	@JoinColumn(name="id_filiere", referencedColumnName = "id_filiere")
	private Filiere filiere;

	
	
		
	public Liste_modules() {
		super();
	}

	public String getSemestre_mod() {
		return semestre_mod;
	}

	public Professeur getProf() {
		return prof;
	}

	public void setProf(Professeur prof) {
		this.prof = prof;
	}

	public void setSemestre_mod(String semestre_mod) {
		this.semestre_mod = semestre_mod;
	}

	public Liste_moduleId getListe_moduelId() {
		return liste_moduelId;
	}

	public void setListe_moduelId(Liste_moduleId liste_moduelId) {
		this.liste_moduelId = liste_moduelId;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Filiere getFiliere() {
		return filiere;
	}

	public void setFilier(Filiere filiere) {
		this.filiere = filiere;
	}

	@Override
	public String toString() {
		return "Liste_modules [module=" + module + ", filiere=" + filiere + "]";
	}
	

	public Liste_modules( Liste_moduleId liste_moduelId, String semestre_mod) {
		super();
		this.liste_moduelId = new Liste_moduleId();
		this.semestre_mod = semestre_mod;

	
		
	}
	
	
	

	
	

}
