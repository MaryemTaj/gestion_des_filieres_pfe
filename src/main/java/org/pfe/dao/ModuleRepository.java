package org.pfe.dao;


import java.util.List;
import org.pfe.entities.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
	public Page<Module>findByNomContains(String mc,Pageable page);
	
	
	
	@Query("select m from Module m inner join m.liste_mode lm where lm.prof.id=:id")
	public Module getModuleByProfs(@Param("id")Long id);
	
	@Query("select m from Module m inner join m.liste_mode lm inner join lm.filiere lmf inner join lmf.etudiants lmfe where lmfe.id=:id")
	public List<Module> getModuleByEtudiants(@Param("id")Long id);
	
	
	
	@Query(value="select m from Module m inner join m.liste_mode lm inner join lm.prof inner join lm.filiere where lm.prof.id=:id1 and lm.filiere.id=:id2 ")
	List<Module> findByIdProfAndIdFiliere(@Param("id1")Long id_prof,@Param("id2") Long id_filiere);
	
	
	
	@Query(value="select m FROM Module m inner join m.liste_mode lm inner join lm.prof inner join lm.filiere where lm.prof.id=:id")
	Page<Module> findByIdProf(@Param("id")Long id_prof,Pageable page);

}
