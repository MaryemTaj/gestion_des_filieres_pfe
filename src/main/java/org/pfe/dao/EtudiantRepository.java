package org.pfe.dao;

import java.util.List;

import org.pfe.entities.Etudiant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface EtudiantRepository extends UserRepository<Etudiant> {
	@Query("select u from Etudiant u where u.email=:email")
	public Etudiant getEtudiantByEmail(@Param("email") String e);
	
	
	
	
	@Query("select e from Etudiant e where e.filiere.id=:id")
	Page <Etudiant> findByidfiliere(@Param("id")Long id_filiere,Pageable page);
	
	
	
	
	//@Query(value="SELECT * FROM etudiant  WHERE id_etud=?" 
		//	 ,nativeQuery=true )
	//List<Etudiant> InfosFiliereEtud(Long id_etud);

}
