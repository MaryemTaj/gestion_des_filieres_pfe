package org.pfe.dao;


import java.util.List;

import org.pfe.entities.Professeur;
import org.pfe.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfRepository extends UserRepository<Professeur> {
	@Query("select p from Professeur p where p.email=:email")
	public Professeur getProfByEmail(@Param("email") String email);
	
	@Query("select p from Professeur p GROUP BY p HAVING COUNT(p.liste_modules)>0")
	public List<Professeur>getProfWithNoModules();
	
	
	
	
	
	@Query(" SELECT p FROM Professeur p inner JOIN p.liste_modules lp inner JOIN lp.filiere lpf inner JOIN lpf.etudiants lpfe WHERE lpfe.id=:id ")
    public List<Professeur> findProfsByIdEtud(@Param("id")Long id_etud) ;
	
	
	

}
