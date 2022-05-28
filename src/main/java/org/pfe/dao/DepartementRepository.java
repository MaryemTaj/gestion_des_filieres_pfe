package org.pfe.dao;

import org.pfe.entities.Departement;
import org.pfe.entities.Filiere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {
	@Query("select u from Departement u where u.id=:id")
	public Departement findbyId(@Param("id")Long id);
	
	public Page<Departement>findByNomContains(String mc,Pageable page);
	
	
	
}
