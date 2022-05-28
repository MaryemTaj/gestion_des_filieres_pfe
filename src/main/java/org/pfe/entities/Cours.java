package org.pfe.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
@Entity
@SQLDelete(sql = "UPDATE cours SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Cours {
	 
		@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
		private Long id_cours;
		private String nom;
		

		private byte[] documents;
		private String type_cours;
		private String nomDocument;
		private Long documentSize;
		private boolean deleted = Boolean.FALSE;
		@CreationTimestamp
		private LocalDateTime dateCreation;
		@ManyToOne
		@JoinColumn(name="id_module",referencedColumnName = "id_module")
		private Module module;

		public Cours() {
			super();
			
		}

		

	


		

		public String getNom() {
			return nom;
		}

		

		public Long getDocumentSize() {
			return documentSize;
		}



		public void setDocumentSize(Long documentSize) {
			this.documentSize = documentSize;
		}



		public String getNomDocument() {
			return nomDocument;
		}



		public void setNomDocument(String nomDocument) {
			this.nomDocument = nomDocument;
		}



		public void setNom(String nom) {
			this.nom = nom;
		}



		

		


		
		public byte[] getDocuments() {
			return documents;
		}

		public void setDocuments(byte[] documents) {
			this.documents = documents;
		}








		public String getType_cours() {
			return type_cours;
		}

		public void setType_cours(String type_cours) {
			this.type_cours = type_cours;
		}

		
		public Long getIs_cours() {
			return id_cours;
		}

		public void setIs_cours(Long is_cours) {
			this.id_cours = is_cours;
		}

		

		public Module getModule() {
			return module;
		}

		public void setModule(Module module) {
			this.module = module;
		}

		public Long getId_cours() {
			return id_cours;
		}

		@Override
		public String toString() {
			return this.nom ;
		}
		
		

}
