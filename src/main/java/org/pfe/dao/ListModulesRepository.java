package org.pfe.dao;

import java.util.List;

import org.pfe.entities.Etudiant;
import org.pfe.entities.Filiere;
import org.pfe.entities.Liste_modules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListModulesRepository extends JpaRepository<Liste_modules, Long> {
	@Query("select lm  FROM Liste_modules lm where lm.prof.id=:id group by lm.filiere.id")
	List<Liste_modules> findFilieresByIdProf(@Param("id")Long id);
	
	
	
	
	
	@Query(value="SELECT * FROM liste_modules l inner JOIN filiere f "
			+ "on l.id_filiere=f.id_filiere inner JOIN user e "
			+ "on f.id_filiere=e.id_filiere WHERE e.id=? ORDER BY l.semestre_mod" 
			,nativeQuery=true)
    public List<Liste_modules> findByIdEtud(Long id_etud) ;
    
    @Query(value="SELECT * FROM liste_modules WHERE prof_id=1 group by id_filiere" ,nativeQuery=true)
	List<Liste_modules> findByIdProf(Long id_prof);
    
    
    
   
    
    
}
