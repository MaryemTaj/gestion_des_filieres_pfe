package org.pfe.dao;



import java.util.List;

import org.pfe.entities.Departement;
import org.pfe.entities.Filiere;
import org.pfe.entities.Liste_modules;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@EnableJpaRepositories
@Repository
public interface FiliereRepository extends JpaRepository<Filiere, Long>{
	
	public Page<Filiere>findByNomContains(String mc,Pageable page);
	
	
	@Query("select u from Filiere u where u.id=:id")
	public Filiere findbyId(@Param("id")Long id);
	
	
	@Query("select DISTINCT f  FROM Filiere f inner join f.liste_modules fl where fl.prof.id=:id")
	Page <Filiere> findFilieresByIdProf(@Param("id")Long id,Pageable page);
	
	
	
	
	
}
