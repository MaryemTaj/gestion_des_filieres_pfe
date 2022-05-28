package org.pfe.dao;

import java.util.List;

import org.pfe.entities.Cours;
import org.pfe.entities.ReclamationEtudiant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface ReclamationEtudiantRepository extends JpaRepository<ReclamationEtudiant, Long> {
	@Query("select r from ReclamationEtudiant r inner join r.professeur rp where rp.id=:id")
	public Page<ReclamationEtudiant> listCoursByprof(@Param("id")Long id,Pageable page);
	
	
	
	
	@Query(value=" SELECT r FROM ReclamationEtudiant r inner JOIN r.professeur rp inner join r.etudiant e where e.id=:id ")
	List<ReclamationEtudiant> listerRclmEtud(@Param("id")Long id);

}

