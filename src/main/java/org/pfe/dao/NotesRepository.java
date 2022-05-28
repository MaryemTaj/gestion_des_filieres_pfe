package org.pfe.dao;

import java.util.List;

import org.pfe.entities.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotesRepository extends JpaRepository<Notes, Long> {
	@Query(
			  value="SELECT * FROM notes n inner JOIN module m on n.id_module= m.id_module"
			  + " inner JOIN etudiant e on n.id_etud=e.id_etud" + 
			  " WHERE e.id_etud=? "
			  ,nativeQuery=true) 
public List<Notes> findNoteByIdEtud(Long id_etud) ;
}


