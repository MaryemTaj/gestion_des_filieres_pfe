package org.pfe.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
@Entity
@SQLDelete(sql = "UPDATE role SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Role implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_id")
	private Long id;
	@NotBlank(message = "must not be blank")
	private String name;
	private boolean deleted = Boolean.FALSE;
	
	 @ManyToMany(mappedBy="roles")
	    private Set<User> users=new HashSet<>();
	 
	public Role(@NotBlank(message = "must not be blank") String name) {
		super();
		this.name = name;
	}
	public Role() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	@Override
	public String toString() {
		return this.name;
	}
	
	

}
