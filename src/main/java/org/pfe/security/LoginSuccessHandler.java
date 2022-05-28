package org.pfe.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pfe.dao.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired
	private EtudiantRepository etudiantRepository ;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
	MyUserDetails userDetails= (MyUserDetails) authentication.getPrincipal();
	String redirectUrl=request.getContextPath();
	if(userDetails.hasRole("ADMIN")) {
		
		redirectUrl+="/filieres";
		
	}
	else if(userDetails.hasRole("PROFESSEUR")) {
		redirectUrl+="/cours";
	}
else if(userDetails.hasRole("ETUDIANT")) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String login = authentication.getName();
		Long id=etudiantRepository.getEtudiantByEmail(login).getId();
		redirectUrl+="/infosEtudiants/"+id+"";
	}
	
	response.sendRedirect(redirectUrl);
	}
	

}
