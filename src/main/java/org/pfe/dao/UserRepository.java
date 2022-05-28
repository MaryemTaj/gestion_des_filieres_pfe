package org.pfe.dao;

import java.util.List;

import org.pfe.entities.Etudiant;
import org.pfe.entities.Filiere;
import org.pfe.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
	
	@Query("select u from User u where u.email=:email")
	public User getUserByEmail(@Param("email") String email);
	
	@Query("select u from User u where u.resetPasswordToken=:resetPasswordToken")
	public User findByResetPasswordToken(@Param("resetPasswordToken")String resetPasswordToken);
	
	@Query("select u from User u inner join u.roles ur where ur.name='ADMIN'")
	public Page<User> getAdmins(Pageable page);
	
	
	@Query("select u from User u inner join u.roles ur where ur.name='PROFESSEUR'")
	public Page<User> getProfs(Pageable page);
	
	
	@Query("select u from User u inner join u.roles ur where ur.name='ETUDIANT'")
	public Page<User> getEtudiants(Pageable page);
	
	@Query("select u from User u inner join u.roles ur where ur.name='RESPONSABLE'")
	public Page<User> getRespnsables(Pageable page);
	
	
	
	
	
/*==================================================================================================================================================*/
	
	
	
	public Page<User>findByNomContains(String mc,Pageable page);

	
	
	
	
	
	
	
	
	
	@Query("select u from User u inner join u.roles ur where ur.name='RESPONSABLE'")
	public List<User> findAllRespnsables();

	@Query("select u from User u inner join u.roles ur where ur.name='ETUDIANT'")
	public List<User> findAllEtudiants();
	
	
	@Query("select u from User u inner join u.roles ur where ur.name='PROFESSEUR'")
	public List<User> findAllProfs();
	

}
