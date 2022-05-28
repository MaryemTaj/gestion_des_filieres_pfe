package org.pfe.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Liste_moduleId implements Serializable {
	private static final long serialVersionUID = 1L;
	 private Long idModule;
	 private Long idFiliere;
	 private Long idProf;
	/*@ManyToOne
	private Module module;

	@ManyToOne
	private Filiere filiere;*/

	public Long getIdModule() {
		return idModule;
	}

	public void setIdModule(Long idModule) {
		this.idModule = idModule;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFiliere == null) ? 0 : idFiliere.hashCode());
		result = prime * result + ((idModule == null) ? 0 : idModule.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Liste_moduleId other = (Liste_moduleId) obj;
		if (idFiliere == null) {
			if (other.idFiliere != null)
				return false;
		} else if (!idFiliere.equals(other.idFiliere))
			return false;
		if (idModule == null) {
			if (other.idModule != null)
				return false;
		} else if (!idModule.equals(other.idModule))
			return false;
		return true;
	}

	

	public Long getIdFiliere() {
		return idFiliere;
	}

	public void setIdFiliere(Long idFiliere) {
		this.idFiliere = idFiliere;
	}

	public Liste_moduleId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Liste_moduleId(Long idModule, Long idFiliere) {
	
		this.idModule = idModule;
		this.idFiliere = idFiliere;
	}
	


}
