package org.pfe.entities;

import javax.persistence.*;

@Entity
public class Notes {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_note;
	
	public Notes() {
		super();
		// TODO Auto-generated constructor stub
	}

	private double valeur_note;
	@Column(length=40)
	private String session__note;


	
	@ManyToOne
	
	@JoinColumn(name="id_etud",insertable = false, updatable = false)
	private Etudiant etudiant;
	@ManyToOne
	

	@JoinColumn(name="id_module",insertable = false, updatable = false)
	private Module module;
	

	
	public Long getId_note() {
		return id_note;
	}

	public void setId_note(Long id_note) {
		this.id_note = id_note;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public double getValeur_note() {
		return valeur_note;
	}

	public void setValeur_note(double valeur_note) {
		this.valeur_note = valeur_note;
	}

	public String getSession__note() {
		return session__note;
	}

	public void setSession__note(String session__note) {
		this.session__note = session__note;
	}

}
