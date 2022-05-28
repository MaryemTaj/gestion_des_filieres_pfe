package org.pfe.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.pfe.dao.AdminRepository;
import org.pfe.dao.CycleRepository;
import org.pfe.dao.DepartementRepository;
import org.pfe.dao.EtudiantRepository;
import org.pfe.dao.FiliereRepository;
import org.pfe.dao.ListModulesRepository;
import org.pfe.dao.ModuleRepository;
import org.pfe.dao.ProfRepository;
import org.pfe.dao.RoleRepository;
import org.pfe.dao.UserRepository;
import org.pfe.entities.Admin;
import org.pfe.entities.Cycle;
import org.pfe.entities.Departement;
import org.pfe.entities.Etudiant;
import org.pfe.entities.Filiere;
import org.pfe.entities.Liste_moduleId;
import org.pfe.entities.Liste_modules;
import org.pfe.entities.Module;
import org.pfe.entities.Professeur;
import org.pfe.entities.Role;
import org.pfe.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.util.StringUtils;
import net.bytebuddy.utility.RandomString;

@Controller
public class AdminController  {
	@Autowired
	private FiliereRepository filrep;
	@Autowired
	private CycleRepository cycRep;
	@Autowired
	private DepartementRepository depRep;
	@Autowired
	private UserRepository<User> userRep;
	
	@Autowired
	private EtudiantRepository etudRep;
	@Autowired
	private AdminRepository admindRep;
	@Autowired
	private ProfRepository profRep;
	@Autowired
	private RoleRepository roleRep;
	@Autowired
	private ModuleRepository modRep;
	@Autowired
	private ListModulesRepository lmRep;
	
	
	
	//pour afficher la liste des filieres
	@GetMapping(path="/filieres")
	public String getFilierePage(Model model,@RequestParam(name="page",defaultValue = "0")int page,@RequestParam(name="size",defaultValue = "5") 
	int size,@RequestParam(name="keyword",defaultValue = "")String mc) {	
		Page<Filiere>	 filieres=filrep.findByNomContains(mc,PageRequest.of(page, size));
		model.addAttribute("filieres",filieres.getContent());
		model.addAttribute("pages",new int[filieres.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("keyword", mc);
		model.addAttribute("size", size);
		return "filieres";}
	
	//pour afficher le formulaire d'ajout des filieres
	@GetMapping(path="filieres/formFiliere")
	public String formPatient(Model model,Long id) {
		List<Cycle> cycList=cycRep.findAll();
		List<Departement> depList=depRep.findAll();
		Filiere filiere=new Filiere();
		model.addAttribute("cycles",cycList);
		model.addAttribute("departements",depList);
		model.addAttribute("filiere",filiere);
		List<Module> modList=modRep.findAll();
		List<User> respList=userRep.findAllRespnsables();
		model.addAttribute("modules",modList);
		model.addAttribute("Etudiants",respList);
		return "formFiliere";}
	
	//pour persister le nouvelles filieres
	@PostMapping("/saveFiliere")
	public String saveFiliere(RedirectAttributes redirectAttributes,@Valid  Filiere filiere, BindingResult buindingResult,Model model) {
		if(buindingResult.hasErrors()) {
			List<Cycle> cycList=cycRep.findAll();
			List<Departement> depList=depRep.findAll();
			model.addAttribute("cycles",cycList);
			model.addAttribute("departements",depList);
			List<Module> modList=modRep.findAll();
			List<User> respList=userRep.findAllRespnsables();
			model.addAttribute("modules",modList);
			model.addAttribute("Etudiants",respList);		
			return "formFiliere";
			}
		filrep.save(filiere); 
		redirectAttributes.addFlashAttribute("message", "Success");
		return "redirect:/filieres";}
	
	//pour afficher le formulaire de mise a jour des filieres
		@GetMapping(path="/editFiliere/{id}")
		public String editPatient(Model model,@PathVariable("id") Long id) {
			List<Cycle> cycList=cycRep.findAll();
			List<Departement> depList=depRep.findAll();
			Filiere f=filrep.findById(id).get();
			model.addAttribute("fil",f);
			model.addAttribute("cycles",cycList);
			model.addAttribute("departements",depList);
			List<Module> modList=modRep.findAll();
			List<User> respList=userRep.findAllRespnsables();
			model.addAttribute("modules",modList);
			model.addAttribute("Etudiants",respList);
			return "EditFiliere";}
	
	//pour persister les filieres mise a jour
	
	
	//pour afficher le formulaire de login
		@GetMapping("/login")	
		public String loginPage(HttpServletRequest request) {
			Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
			if(authentication==null || authentication instanceof AnonymousAuthenticationToken) {
			return "loginForm";}
		return "redirect:/filieres";}
	
	
	
	//pour supprimer une filiere
	@GetMapping(path="/deleteFiliere")
	public String delet(RedirectAttributes redirectAttributes,Long id) {
		filrep.deleteById(id);
		redirectAttributes.addFlashAttribute("message", "Success");
		return "redirect:/filieres";}

	
	//***************************************Crud Departements*****************************************************************************
	
	//pour afficher la liste des departements
	@GetMapping(path="/departements")
	public String getDepartementPage(Model model,@RequestParam(name="page",defaultValue = "0")int page,@RequestParam(name="size",defaultValue = "5") int size) {
		Page<Departement> departements=depRep.findAll(PageRequest.of(page, size));
		model.addAttribute("departements",departements.getContent());
		model.addAttribute("pages",new int[departements.getTotalPages()]);
		model.addAttribute("currentPage",page);
		return "departements";}
	
	
	//pour supprimer une depratement
	@GetMapping(path="/deleteDepartement")
	public String deleteDepartement(RedirectAttributes redirectAttributes,Long id) {
		depRep.deleteById(id);
		redirectAttributes.addFlashAttribute("message", "Success");
		return "redirect:/departements";}
	
	@GetMapping(path="formDepartement")
	public String formDepartement(Model model,Long id) {
		model.addAttribute("departements",new Departement());
		return "formDepartement";}
	
	
	@PostMapping("/saveDepartement")
	public String saveDepartement(RedirectAttributes redirectAttributes,Departement dept) {
		depRep.save(dept);
		redirectAttributes.addFlashAttribute("message", "Success");
		return "redirect:/departements";}
//************************************************Crud Users****************************************
	
	//pour afficher la liste des utilisateurs
	@GetMapping(path="/users")
	public String getUserPage(Model model,@RequestParam(name="page",defaultValue = "0")int page,@RequestParam(name="size",defaultValue = "5") int size) {
		Page<User> users=userRep.findAll(PageRequest.of(page, size));
		model.addAttribute("users",users.getContent());
		model.addAttribute("pages",new int[users.getTotalPages()]);
		model.addAttribute("currentPage",page);
		return "users";}
	
	//pour afficher la liste des etudiants
	@GetMapping(path="/etudiants")
	public String getEtudiantPage(Model model,@RequestParam(name="page",defaultValue = "0")int page,@RequestParam(name="size",defaultValue = "5") int size) {
		Page<Etudiant> etudiant=etudRep.findAll(PageRequest.of(page, size,Sort.by(Sort.Direction.DESC, "dateCreation")));
		model.addAttribute("etudiants", etudiant.getContent());
		model.addAttribute("pages",new int[ etudiant.getTotalPages()]);
		model.addAttribute("currentPage",page);
		return "etudiants";}
	
	//pour afficher la liste des professeurs
	@GetMapping(path="/profs")
	public String getProfsPage(Model model,@RequestParam(name="page",defaultValue = "0")int page,@RequestParam(name="size",defaultValue = "5") int size) {
		Page<Professeur> users=profRep.findAll(PageRequest.of(page, size));
		model.addAttribute("profs",users.getContent());
		model.addAttribute("pages",new int[users.getTotalPages()]);
		model.addAttribute("currentPage",page);
		return "profs";}
	
	//pour afficher la liste des admins
	@GetMapping(path="/admins")
	public String getAdminsPage(Model model,@RequestParam(name="page",defaultValue = "0")int page,@RequestParam(name="size",defaultValue = "5") int size) {
		Page<User> users=userRep.getAdmins(PageRequest.of(page, size));
		model.addAttribute("admins",users.getContent());
		model.addAttribute("pages",new int[users.getTotalPages()]);
		model.addAttribute("currentPage",page);		
		return "admins";}
	
	//pour afficher le formulaire d'ajout des etudiants
	@GetMapping(path="etudiants/newEtudiants")
	public String showStudentForm(Model model) {
		List<Role> listRoles=roleRep.findAll();
		List<Filiere> filList=filrep.findAll();
		model.addAttribute("filieres", filList);
		model.addAttribute("listRoles",listRoles);
		model.addAttribute("Etudiant",new Etudiant());
		return "add_student_form";}
	
	
	
	
	@GetMapping(path="admins/newAdmins")
	public String showAdminForm(Model model) {
		List<Role> listRoles=roleRep.findAll();
		model.addAttribute("listRoles",listRoles);
		model.addAttribute("Admin",new Admin());
		return "add_admin_form";}
	
	
	
	//pour afficher le formulaire d'ajout des professeurs
		@GetMapping(path="professeurs/newProfesseur")
		public String ashowProfForm(Model model) {
			List<Role> listRoles=roleRep.findAll();
			model.addAttribute("listRoles",listRoles);
			model.addAttribute("Professeur",new Professeur());
			return "add_prof_form";}
	
	@GetMapping(path="/users/editUser/{id}")
	public String showEditForm(Model model,@PathVariable("id") Long id) {
		List<Role> listRoles=roleRep.findAll();
		model.addAttribute("listRoles",listRoles);
		model.addAttribute("user",userRep.findById(id).get());
		return "editUserForm";}
	
	
	@PostMapping("/saveStudent")
	public String listEtudaints(RedirectAttributes redirectAttributes,@Valid Etudiant etudiant,@RequestParam("fileImage") MultipartFile multipartFile,BindingResult bindingResult) throws IOException {
		if (bindingResult.hasErrors()) {
			return "";}
		BCryptPasswordEncoder enc=new BCryptPasswordEncoder();
		etudiant.setPassword(enc.encode("1234"));
	    String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
	    etudiant.setIamge(fileName);
		User SavedeUser =etudRep.save(etudiant);
		String uploadDir="./user-images/"+SavedeUser.getId(); 
		Path uploadPath=Paths.get(uploadDir);
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);}
		try {
			InputStream inputStream=multipartFile.getInputStream();
			Path filePath=uploadPath.resolve(fileName);
			Files.copy(inputStream,filePath ,StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {throw new IOException("Could not upload file "+fileName);}
		 redirectAttributes.addFlashAttribute("message", "Success");
		return "redirect:/etudiants";}
	
	
	
	
	@PostMapping("/saveProfesseur")
	public String saveProfesseur(RedirectAttributes redirectAttributes,@Valid Professeur prof,@RequestParam("fileImage") MultipartFile multipartFile,BindingResult bindingResult) throws IOException {
		if (bindingResult.hasErrors()) {
			return "";}
		BCryptPasswordEncoder enc=new BCryptPasswordEncoder();
		prof.setPassword(enc.encode("1234"));
	    String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
	    prof.setIamge(fileName);
		User SavedeUser =profRep.save(prof);
		String uploadDir="./user-images/"+SavedeUser.getId(); 
		Path uploadPath=Paths.get(uploadDir);
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);}
		try {
			InputStream inputStream=multipartFile.getInputStream();
			Path filePath=uploadPath.resolve(fileName);
			Files.copy(inputStream,filePath ,StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {throw new IOException("Could not upload file "+fileName);}
		 redirectAttributes.addFlashAttribute("message", "Success");
		return "redirect:/profs";}
	
	@PostMapping("/saveAdmin")
	public String saveAdmin(RedirectAttributes redirectAttributes,@Valid Admin admin,@RequestParam("fileImage") MultipartFile multipartFile,BindingResult bindingResult) throws IOException {
		if (bindingResult.hasErrors()) {
			return "";}
		BCryptPasswordEncoder enc=new BCryptPasswordEncoder();
		admin.setPassword(enc.encode("1234"));
	    String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
	    admin.setIamge(fileName);
		User SavedeUser =admindRep.save(admin);
		String uploadDir="./user-images/"+SavedeUser.getId(); 
		Path uploadPath=Paths.get(uploadDir);
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);}
		try {
			InputStream inputStream=multipartFile.getInputStream();
			Path filePath=uploadPath.resolve(fileName);
			Files.copy(inputStream,filePath ,StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {throw new IOException("Could not upload file "+fileName);}
		 redirectAttributes.addFlashAttribute("message", "Success");
		return "redirect:/admins";}
	
	
	
	
	
	
	
	
	//pour supprimer un utilisateur
	@GetMapping("editUser/{id}")
	public String showEditForm(@PathVariable("id")Long id,Model model) {
		User user=userRep.findById(id).get();
		List<Role> listRoles=roleRep.findAll();
		model.addAttribute("listRoles",listRoles);
		model.addAttribute("user",user);
		return "add_user_form";
	}
	
	@GetMapping(path="/deleteUser")
	public String deleteUser(Long id,RedirectAttributes redirectAttributes) {
		userRep.deleteById(id);
		 redirectAttributes.addFlashAttribute("message", "Success");
		 redirectAttributes.addFlashAttribute("alertClass", "alert-success");	
		return "redirect:/etudiants";}

	//************************************************Crud Cycle****************************************
		
	//pour afficher la liste des cycles
	@GetMapping(path="/cycle")
	public String getCyclePage(Model model,@RequestParam(name="page",defaultValue = "0")int page,@RequestParam(name="size",defaultValue = "5") int size) {
		Page<Cycle> cycles=cycRep.findAll(PageRequest.of(page, size));
		model.addAttribute("cycles",cycles.getContent());
		model.addAttribute("pages",new int[cycles.getTotalPages()]);
		model.addAttribute("currentPage",page);	
		return "cycle";}
	
	//pour supprimer un cycle
	@GetMapping(path="/deleteCycle")
	public String deleteCycle(RedirectAttributes redirectAttributes,Long id) {
		cycRep.deleteById(id);
		redirectAttributes.addFlashAttribute("message", "Success");
		return "redirect:/cycle";}
	
	@ResponseBody
	@GetMapping(path="test")
	public List<User> findUsers() {
		return userRep.findAllEtudiants();}
	
	//*****************************************CRUD MODULES ****************************************************//
	
	//pour afficher la liste des modules
	@GetMapping(path="/modules")
	public String getModulesPage(Model model,@RequestParam(name="page",defaultValue = "0")int page,@RequestParam(name="size",defaultValue = "5") int size,String mc) {
		//return modRep.findAll();
		Page<Module> modules=modRep.findAll(PageRequest.of(page, size));
		model.addAttribute("modules",modules.getContent());
		model.addAttribute("pages",new int[modules.getTotalPages()]);
		model.addAttribute("currentPage",page);
		return "modules";
	}
	
	@GetMapping(path="/newModule")
	public String ashowModuleForm(Model model) {
		List<Professeur> listProf=profRep.findAll();
		model.addAttribute("profs",listProf);
		model.addAttribute("module",new Module());
		model.addAttribute("listModules",new Liste_modules());
		model.addAttribute("modules",modRep.findAll());
		model.addAttribute("filieres",filrep.findAll());
		return "addModule";}
	
	@PostMapping("/saveModule")
	public String saveModule(RedirectAttributes redirectAttributes,@Valid  Module module, BindingResult buindingResult) {
		if(buindingResult.hasErrors()) return "formFiliere";
		modRep.save(module); 
		return "redirect:/modules";}
	
	@GetMapping(path="/deleteModule")
	public String deleteModule(RedirectAttributes redirectAttributes,Long id) {
		modRep.deleteById(id);	
		return "redirect:/modules";}	

	@PostMapping(path="/saveSemestre")
	public String showListeSemestre(Liste_modules lm) {
		Liste_moduleId lmId=new Liste_moduleId(lm.getModule().getId_module(),lm.getFiliere().getId_filiere());
		lm.setListe_moduelId(lmId);
		lmRep.save(lm);
		return "redirect:/modules";}
	
	
	@ResponseBody
	@GetMapping(path="/lm")
	public List<Professeur> getModuelByIdProf(HttpServletRequest request) {
	Principal principal = request.getUserPrincipal();
    return profRep.getProfWithNoModules();
    
	
	
	}
	
		


	@ResponseBody
	@GetMapping("/newModule/{id}/semestres")
	public HashMap<java.lang.String, java.lang.String> String(@PathVariable("id") Long id) {
		Cycle c=cycRep.findbyId(id);
		String c1="CYC1";
		String c2="CYC2";
		 HashMap<String, String> listSemestre = new HashMap<String, String>();
		if(c.getNom().equals(c1)) {
			listSemestre.put("Nom", "s1");
			listSemestre.put("Nom", "s2");
			listSemestre.put("Nom", "s3");
			listSemestre.put("Nom", "s4");
			return listSemestre;
			}
		else if(c.getNom().equals(c2)) {
			
			listSemestre.put("Nom", "s3");
			listSemestre.put("Nom", "s4");
		}
		return listSemestre;
			
		
			
		
		
	}
		
	}





