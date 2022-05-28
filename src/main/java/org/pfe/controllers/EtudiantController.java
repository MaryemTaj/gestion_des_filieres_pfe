package org.pfe.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.pfe.dao.CoursRepository;
import org.pfe.dao.CycleRepository;
import org.pfe.dao.DepartementRepository;
import org.pfe.dao.EtudiantRepository;
import org.pfe.dao.FiliereRepository;
import org.pfe.dao.ListModulesRepository;
import org.pfe.dao.ModuleRepository;
import org.pfe.dao.NotesRepository;
import org.pfe.dao.ProfRepository;
import org.pfe.dao.ReclamationEtudiantRepository;
import org.pfe.dao.ReclamationProfRepository;
import org.pfe.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class EtudiantController {

	@Autowired
	private EtudiantRepository etudiantRepository ;
	@Autowired
	private ProfRepository profRepository ;
	@Autowired
	private DepartementRepository departementRepository ;
	@Autowired
	private CycleRepository cycleRepository ;
	@Autowired
	private FiliereRepository filiereRepository ;
	@Autowired
	private ListModulesRepository listeModulesRepository ;
	@Autowired
	private ModuleRepository moduleRepository ;
	@Autowired
	private NotesRepository notesRepository ;
	
	@Autowired
	private CoursRepository coursRepository ;
	
	@Autowired
	private ReclamationProfRepository reclam_ProfRepository ;
	
	@Autowired
	private ReclamationEtudiantRepository reclam_EtudRepository ;
	
	public EtudiantController() {
		
	}
	
//....................... Page d'accueil - infos du cycle departm filiere .......................
	
	
	@RequestMapping(value="/infosEtudiants/{id_etud}" ,  method = RequestMethod.GET)
	public String FiliereEtudiants(@PathVariable Long id_etud, Model model) {
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String login = auth.getName();
		Long id=etudiantRepository.getEtudiantByEmail(login).getId();
		Etudiant filiereEtudiants = etudiantRepository.findById(id).get();
	    model.addAttribute("filiereEtudiants", filiereEtudiants);
		return"AcceuilEtud" ;
		
	}
	
//..................... Liste des modules avec le nom du prof et semestre ..........................
	
	 @RequestMapping(value = "/modules/{id_etud}", method = RequestMethod.GET)
	public String ListeModules(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		Long id=etudiantRepository.getEtudiantByEmail(login).getId();
		List<Liste_modules> listeModules = listeModulesRepository.findByIdEtud(id);
	    model.addAttribute("listeModules", listeModules);
		return"ModulesEtud" ;
		 
	    
	    
	}
	

//..................... Liste des cours selon les modules ..........................
		
		
	
	  @RequestMapping(value = "/cours/{id_etud}/{nom_module}", method = RequestMethod.GET)
	  public String listeCours(@PathVariable Long id_etud ,@PathVariable String nom_module, Model model) {
	  List<Cours> listeCours = coursRepository.findCours(nom_module);
	  model.addAttribute("listeCours", listeCours);
	  return"CoursEtud" ;
	  
	  }
	  

//................................. Telecharger un cours ....................................
		
	  
	  @GetMapping(path="/download/{id_cours}")
		public void downloadFile(@PathVariable Long id_cours,HttpServletResponse response) throws Exception {
			Optional<Cours> result=coursRepository.findById(id_cours);
			if(!result.isPresent()) {
				throw new Exception("Could not find document with id :"+id_cours);}
			Cours c=result.get();
			response.setContentType("application/octet-stream");
			String headerKey="Content-Disposition";
			String headerValue="attachement; filename="+c.getNomDocument();
			response.setHeader(headerKey, headerValue);
			ServletOutputStream outputStream=response.getOutputStream();
			outputStream.write(c.getDocuments());
			outputStream.close();	
		}
		
	  
	

//..................................... Tableau des notes .........................................
		
		
		 @RequestMapping(value = "/notes_Etud/{id_etud}", method = RequestMethod.GET)
		public String ListeNotes(@PathVariable Long id_etud, Model model) {
			List<Notes> listeNotes = notesRepository.findNoteByIdEtud(id_etud);
		     model.addAttribute("listeNotes", listeNotes);
			return"NotesEtud" ;
			
		}
		
	 
	 
	
		
		
	
//................................Liste des infos personnelles ...................................
	 
	
	 @RequestMapping(value = "/infos/{id_etud}", method = RequestMethod.GET)
	public String ListeEtudiants(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		Long id=etudiantRepository.getEtudiantByEmail(login).getId();
		Etudiant listeEtudiants = etudiantRepository.findById(id).get();
	     model.addAttribute("listeEtudiants", listeEtudiants);
		return"ProfileEtud" ;
		
	}

	 
	 
	 
//...........................Liste des reclamations recues de profs ...................................
	 
		
		 @RequestMapping(value = "/reclamationsP/{id_etud}", method = RequestMethod.GET)
		public String ListeReclamsProf(Model model) {
			 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				String login = auth.getName();
				Long id=etudiantRepository.getEtudiantByEmail(login).getId();
			List<ReclamationProf> listeReclamProf = reclam_ProfRepository.findReclamProf(id);
		     model.addAttribute("listeReclamProf", listeReclamProf);
			return"ReclamRecuesEtud" ;
			
		}
		 
		 @RequestMapping("/deleteReclamProf/{id_recl_prof}")
		 public String deleteReclamProf(@PathVariable(name = "id_recl_prof") Long id_recl_prof) {
			 reclam_ProfRepository.deleteById(id_recl_prof);
		     return "redirect:/reclamationsP/1";
		 }

		 
		 
//........................... Les reclamation envoyées par l'etudiant ...............................

	 
	 @Autowired
	    private ReclamationEtudiantRepository rclmE;
	 
     @RequestMapping(value = "/ReclamationsEtud/{id_etud}", method = RequestMethod.GET)
	 public String ListeRclmE(Model model) {
    	 
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
 		String login = auth.getName();
 		Long id=etudiantRepository.getEtudiantByEmail(login).getId();
	     List<ReclamationEtudiant> listReclamationsE = reclam_EtudRepository.listerRclmEtud(id);
	     model.addAttribute("listReclamationsE", listReclamationsE);
	      
	     return "ReclamEnvoyeeEtud";
	 }
	 
     
     @RequestMapping(value = "/newRclmEtud/{id_etud}")
	 public String showNewReclamEtudPage(Model model) {
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
 		String login = auth.getName();
 		Long id=etudiantRepository.getEtudiantByEmail(login).getId();
    	 List<Professeur> listeProfs = profRepository.findAll();
    	 
		 ReclamationEtudiant reclamationsE = new ReclamationEtudiant();
		 Etudiant ee =etudiantRepository.findById(id).get();
		 reclamationsE.setEtudiant(ee);
	     model.addAttribute("reclamationsE", reclamationsE);
	     model.addAttribute("listeProfs", listeProfs);
	      
	     return "newRclmEtud";
	 }
	 @ResponseBody
	 @RequestMapping(value = "/saveReclam", method = RequestMethod.POST)
	 public Professeur saveReclamationE(@ModelAttribute("reclamationsE") ReclamationEtudiant reclamationsE) {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String login = authentication.getName();
			Etudiant P=etudiantRepository.getEtudiantByEmail(login);
			reclamationsE.setEtudiant(P);
			rclmE.save(reclamationsE);
			return reclamationsE.getProfesseur();
	      
	     //return "redirect:/ReclamationsEtud/{id_etud}";
	 }
	 
	
		 
	 
	 @RequestMapping("/deleteReclamEtud/{id_recl_etud}")
	 public String deleteRclmEtud(@PathVariable(name = "id_recl_etud") Long id_recl_etud) {
		 reclam_EtudRepository.deleteById(id_recl_etud);
	     return "redirect:/ReclamationsEtud/{id_etud}";
	 }
}
