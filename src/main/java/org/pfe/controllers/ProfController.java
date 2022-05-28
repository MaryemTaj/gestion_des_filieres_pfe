package org.pfe.controllers;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.pfe.dao.CoursRepository;
import org.pfe.dao.EtudiantRepository;
import org.pfe.dao.FiliereRepository;
import org.pfe.dao.ListModulesRepository;
import org.pfe.dao.ModuleRepository;
import org.pfe.dao.NotesRepository;
import org.pfe.dao.ProfRepository;
import org.pfe.dao.ReclamationEtudiantRepository;
import org.pfe.dao.ReclamationProfRepository;
import org.pfe.entities.Cours;
import org.pfe.entities.Cycle;
import org.pfe.entities.Departement;
import org.pfe.entities.Etudiant;
import org.pfe.entities.Filiere;
import org.pfe.entities.Liste_modules;
import org.pfe.entities.Module;
import org.pfe.entities.Notes;
import org.pfe.entities.Professeur;
import org.pfe.entities.ReclamationEtudiant;
import org.pfe.entities.ReclamationProf;
import org.pfe.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;



@Controller
public class ProfController {
	@Autowired
	private ModuleRepository modRep;
	@Autowired
	private ProfRepository profRep;
	@Autowired
	private CoursRepository courRep;
	@Autowired
	NotesRepository notRep;
	
	@Autowired
	private ReclamationProfRepository recRep;
	@Autowired
	private ReclamationEtudiantRepository recEtdRep;
	@Autowired
	private ModuleRepository lmRep;
	@Autowired
	private FiliereRepository filRep;
	
	@Autowired
	private ListModulesRepository LMRep;
	
	@Autowired
	private EtudiantRepository EtdRep;
	
	
	@GetMapping(path="/cours")
	public String cours(Model model,@RequestParam(name="page",defaultValue = "0")int page,@RequestParam(name="size",defaultValue = "5") int size,
			@RequestParam(name="keyword",defaultValue = "")String mc) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String login = authentication.getName();
		Long id=profRep.getProfByEmail(login).getId();
		Page<Cours> cours=courRep.listCoursByprof(id,PageRequest.of(page, size));
		model.addAttribute("cours",cours.getContent());
		model.addAttribute("pages",new int[cours.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("keyword", mc);
		model.addAttribute("size", size);
		return "cours";
	}
	
	
	
	
	@RequestMapping(value = "/listModuleProf/{id_prof}/{id_filiere}", method = RequestMethod.GET)
	public String listeModuleprof(@PathVariable Long id_prof,@PathVariable Long id_filiere, Model m) {
	    	List<Module> listmodule = lmRep.findByIdProfAndIdFiliere(id_prof, id_filiere);//findByIdProfAndIdFiliere(id_prof,id_filiere);
		
		    Cours cours=new Cours();	
		    m.addAttribute("listesModules",listmodule);
		   
		    m.addAttribute("cours", cours);
		    
		    return "CoursForm"; 
	}
	
	@GetMapping(path="/FormCours")
	public String formCours(Model model) {
		List<Module> m=modRep.findAll();
		model.addAttribute("listModules",m);
		model.addAttribute("cours", new Cours());
		
		return "coursForm";
	
	}
	
	@PostMapping(path="/saveCours",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String saveCours(HttpServletRequest request,@RequestParam("file") MultipartFile file,Cours cours) throws IOException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String login = authentication.getName();
		Module m=modRep.getById(profRep.getProfByEmail(login).getId());
		
		cours.setModule(m);
		String fileName= StringUtils.cleanPath(file.getOriginalFilename());
		cours.setNomDocument(fileName);
		cours.setDocuments(file.getBytes());
		cours.setDocumentSize(file.getSize());
		courRep.save(cours);
		
		return "redirect:/cours";
	}
	
	
	@GetMapping(path="/download")
	public void downloadFile(@Param("id")Long id,HttpServletResponse response) throws Exception {
		Optional<Cours> result=courRep.findById(id);
		if(!result.isPresent()) {
			throw new Exception("Could not find document with id :"+id);}
		Cours c=result.get();
		response.setContentType("application/octet-stream");
		String headerKey="Content-Disposition";
		String headerValue="attachement; filename="+c.getNomDocument();
		response.setHeader(headerKey, headerValue);
		ServletOutputStream outputStream=response.getOutputStream();
		outputStream.write(c.getDocuments());
		outputStream.close();
	}
	@GetMapping(path="reclamationsProf")
	public String getReclamationsPage(Model model,@RequestParam(name="page",defaultValue = "0")int page,@RequestParam(name="size",defaultValue = "5") 
	int size,@RequestParam(name="keyword",defaultValue = "")String mc) {	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String login = authentication.getName();
		Long id=profRep.getProfByEmail(login).getId();
		Page<ReclamationEtudiant>recProf=recEtdRep.listCoursByprof(id,PageRequest.of(page, size));
		model.addAttribute("reclamations",recProf.getContent());
		model.addAttribute("pages",new int[recProf.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("keyword", mc);
		model.addAttribute("size", size);
		return "reclamationsProf";}

@GetMapping(path="/reponse/{id}/{id2}")
public String getReponse(@PathVariable("id")Long id,@PathVariable("id2")Long id2,Model model) {
	ReclamationProf p=new ReclamationProf();
	Etudiant ee =EtdRep.findById(id).get();
	p.setEtudiant(ee);
	List<Etudiant>le=EtdRep.findAll();
	model.addAttribute("reponseReclamation", p);
	model.addAttribute("listEtudiants",le);
	return "formReclamationProfReponse";
}


@PostMapping("/saveReponseReclamationEtudiant")
public String showResponse(ReclamationProf p) {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	String login = authentication.getName();
	Professeur P=profRep.getProfByEmail(login);
	p.setProfesseur(P);
	recRep.save(p);
		return "redirect:/reclamationsProf";
	
}

@GetMapping("/finModuleProf/{id}")
public String findModuleProf(@PathVariable("id") Long id,Model model,@RequestParam(name="page",defaultValue = "0")int page,@RequestParam(name="size",defaultValue = "5") int size,String mc) {
	Page<Module> modules=modRep.findByIdProf(id,PageRequest.of(page, size));
	model.addAttribute("modules",modules.getContent());
	model.addAttribute("pages",new int[modules.getTotalPages()]);
	model.addAttribute("currentPage",page);
	return "ModuleProf";
}
@GetMapping("findFiliereByProf/{id}")
public String findFiliereByProf(@PathVariable("id")Long id,Model model,@RequestParam(name="page",defaultValue = "0")int page,@RequestParam(name="size",defaultValue = "5") int size) {
	Page<Filiere> modules=filRep.findFilieresByIdProf(id,PageRequest.of(page, size));
	model.addAttribute("filieres",modules.getContent());
	model.addAttribute("pages",new int[modules.getTotalPages()]);
	model.addAttribute("currentPage",page);
	return "filieresProf";
}

@GetMapping("showEtudiantsProf/{id}")
public String showEtudiantsByFilieres(@PathVariable("id") Long id,Model model,@RequestParam(name="page",defaultValue = "0")int page,@RequestParam(name="size",defaultValue = "5") int size) {
	Page<Etudiant> modules=EtdRep.findByidfiliere(id,PageRequest.of(page, size));
	model.addAttribute("Etudiants",modules.getContent());
	model.addAttribute("pages",new int[modules.getTotalPages()]);
	model.addAttribute("currentPage",page);
	
	return "EtudiantsFilieresProf";
}


//pour la sauvgarde des notes 
 
   //@ResponseBody
    @RequestMapping(value = "/saisie", method = RequestMethod.POST)
    public String addNotes(@Valid Notes notes, BindingResult result) {
    if (result.hasErrors()) {
	return "index";
     }
     notRep.save(notes);
    return "redirect:/listFiliereNotes";
}
  
    
    
    @GetMapping("findFiliereProf/{id}")
    public String findFiliereProf(@PathVariable("id")Long id,Model model,@RequestParam(name="page",defaultValue = "0")int page,@RequestParam(name="size",defaultValue = "5") int size) {
    	Page<Filiere> modules=filRep.findFilieresByIdProf(id,PageRequest.of(page, size));
    	model.addAttribute("filieres",modules.getContent());
    	model.addAttribute("pages",new int[modules.getTotalPages()]);
    	model.addAttribute("currentPage",page);
    	return "AjoutCoursfiliere";
    }

    
}



