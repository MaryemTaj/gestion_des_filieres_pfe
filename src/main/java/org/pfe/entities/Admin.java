package org.pfe.entities;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("Admin")
public class Admin  extends User  implements Serializable {
	private static final long serialVersionUID = 1L;

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Admin(@NotBlank(message = "ne doit pas etre null") String nom,
			@NotBlank(message = "ne doit pas etre null") String preNom,
			@NotBlank(message = "ne doit pas etre null") String password,
			@NotBlank(message = "ne doit pas etre null") String cin,
			@NotBlank(message = "ne doit pas etre null") String email) {
		super(nom, preNom, password, cin, email);
		// TODO Auto-generated constructor stub
	}
	

}
