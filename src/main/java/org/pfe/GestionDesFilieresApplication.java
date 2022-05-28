package org.pfe;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.pfe.dao.CycleRepository;
import org.pfe.dao.DepartementRepository;
import org.pfe.dao.FiliereRepository;
import org.pfe.dao.ProfRepository;
import org.pfe.dao.RoleRepository;
import org.pfe.dao.UserRepository;
import org.pfe.entities.Cycle;
import org.pfe.entities.Departement;
import org.pfe.entities.Etudiant;
import org.pfe.entities.Filiere;
import org.pfe.entities.Professeur;
import org.pfe.entities.Role;
import org.pfe.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.bytebuddy.utility.RandomString;

@SpringBootApplication
public class GestionDesFilieresApplication implements CommandLineRunner {
@Autowired
private FiliereRepository filrep;
@Autowired
private CycleRepository crep;
@Autowired
private DepartementRepository drep;
@Autowired
private UserRepository<User> userep;
@Autowired
private RoleRepository rolerep;
@Autowired
private ProfRepository profRep;
@Autowired
private UserRepository<Etudiant> etudRep;
	public static void main(String[] args) {
		SpringApplication.run(GestionDesFilieresApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Etudiant e=new Etudiant("Elhamine","Elmehdi","$2a$10$gt7KNTInCZOPWKOdcCCzi.UXVgf6Au4lL7.wuKBul/N3wdq68Lib6","cin","elhamine@gmail.com","cne");
	  //etudRep.save(e);
		
	
		
		
	}
}
