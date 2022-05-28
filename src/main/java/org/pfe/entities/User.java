package org.pfe.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.type.TrueFalseType;
import org.springframework.format.annotation.DateTimeFormat;



@Entity

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type", discriminatorType = DiscriminatorType.STRING)
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	@NotBlank(message = "ne doit pas etre null" )

	private String Nom;
	@NotBlank(message = "ne doit pas etre null" )

	private String preNom;

	public void setNom(String nom) {
		Nom = nom;
	}
	private String password;
	@NotBlank(message = "ne doit pas etre null" )
	private String cin;
	
	@NotBlank(message = "ne doit pas etre null" )
	@Column(unique = true)
	private String email;
	
	@Column(unique = true)
	private String resetPasswordToken;
	
	
	
	@Column(length = 45 ,nullable = true)
	private String iamge;
	
	@CreationTimestamp
	private LocalDateTime dateCreation;
	
	private boolean deleted = Boolean.FALSE;
	
	
	
	@ManyToMany(cascade = CascadeType.PERSIST,fetch =FetchType.EAGER)
	@JoinTable(name="user_roles",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="role_id") )
	private Set<Role> roles=new HashSet<>();
	
	

	
	
	// @OneToMany(mappedBy = "user")
	//  private List<Module> modules;
	
	
	
	
	
	public String getIamge() {
		return iamge;
	}
	public void setIamge(String iamge) {
		this.iamge = iamge;
	}
	
	public User(@NotBlank(message = "ne doit pas etre null") String nom,
			@NotBlank(message = "ne doit pas etre null") String preNom,
			@NotBlank(message = "ne doit pas etre null") String password,
			@NotBlank(message = "ne doit pas etre null") String cin,
			@NotBlank(message = "ne doit pas etre null") String email) {
		Nom = nom;
		this.preNom = preNom;
		this.password = password;
		this.cin = cin;
		this.email = email;
		
	}
	public String getNom() {
		return Nom;
	}
	
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public String getResetPasswordToken() {
		return resetPasswordToken;
	}
	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}
	
	
	public LocalDateTime getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	
	public String getPreNom() {
		return preNom;
	}
	public void setPreNom(String preNom) {
		this.preNom = preNom;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Long getId() {
		return id;
	}
	public boolean hasRole(String roleName) {
		Iterator<Role> iterator=roles.iterator();
		while(iterator.hasNext()) {
			Role role=iterator.next();
			if(role.getName().equals(roleName)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	public void addRole(Role r) {
		this.roles.add(r); 
	}
	
	
	@Override
	public String toString() {
		return this.Nom+" "+this.preNom;
	}

	@Transient
	public String getUserImagePath() {
		if(iamge==null || id==null) {return null;}
		return "/user-images/"+id+"/"+iamge;
		
	}
	public User() {
		super();
	}
	
	
	
	

}
