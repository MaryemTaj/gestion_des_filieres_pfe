package org.pfe.dao;


import java.util.List;

import org.pfe.entities.ReclamationProf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReclamationProfRepository extends JpaRepository<ReclamationProf, Long> {
	
	@Query("SELECT r FROM ReclamationProf r inner JOIN r.professeur inner JOIN r.etudiant WHERE r.etudiant.id=:id") 
public List<ReclamationProf> findReclamProf(@Param("id")Long id_etud) ;

}
