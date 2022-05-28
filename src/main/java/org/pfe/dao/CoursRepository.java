package org.pfe.dao;

import java.util.List;

import org.pfe.entities.Cours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {
	public Page<Cours>findByNomContains(String mc,Pageable page);
	
	@Query("select c from Cours c inner join c.module cm inner join cm.liste_mode clm where clm.prof.id=:id  ")
	public Page<Cours> listCoursByprof(@Param("id")Long id,Pageable page);
	

	 @Query(value="SELECT * FROM cours c inner JOIN module m on c.id_module= m.id_module"
			  + " inner JOIN liste_modules l on m.id_module=l.id_module" +
			  " inner JOIN filiere f on l.id_filiere=f.id_filiere " +
			  " inner JOIN user e on f.id_filiere=e.id_filiere WHERE m.nom=?"
			  ,nativeQuery=true) 
			  public  List<Cours> findCours(String nom_module);
	
}
