package org.pfe.dao;

import java.util.List;

import org.pfe.entities.Cycle;
import org.pfe.entities.Filiere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface CycleRepository extends JpaRepository<Cycle, Long> {
	@Query("select u from Cycle u where u.id=:id")
	public Cycle findbyId(@Param("id")Long id);
	
	public Page<Cycle>findByNomContains(String mc,Pageable page);

	
}
