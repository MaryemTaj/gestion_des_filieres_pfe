package org.pfe.controllers;




import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.pfe.dao.UserRepository;
import org.pfe.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import net.bytebuddy.utility.RandomString;

@Controller
public class ForgotPasswordController {
	@Autowired
	private UserRepository<User> userep;
	@Autowired
	private UserService uservice;
	@Autowired
	private JavaMailSender mailSender;
	@GetMapping("/password/forgotPassword")
	public String showForgotPasswordForm(Model model) {
		model.addAttribute("tiltle","Forgot password form");
		return "forgotPasswordForm";
		
	}
	@PostMapping("/password/forgotPassword")
	public String processForgotPassword(HttpServletRequest request,Model model) throws UnsupportedEncodingException, MessagingException {
		String email=request.getParameter("email"); 
		String token =RandomString.make(45);
		System.out.println("email: "+email);
		System.out.println("token: "+token);
		
			try {
				uservice.updateResetPassword(token, email);
				String resetPasswordLink=Utility.getSiteUrl(request)+"/reset_password?token="+token;
				System.out.println(resetPasswordLink);
				sendEmail(email,resetPasswordLink);
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				model.addAttribute("error", e.getMessage());
			}
		
				
		
		
		return "forgotPasswordForm";
	}

	
	
	@PostMapping("/reset_password")
	public String resetPassword(HttpServletRequest request,Model model)  {
		String token=request.getParameter("token");
		String password=request.getParameter("password");
		User user=uservice.get(token);
		if(user==null) {
			model.addAttribute("message", "invalid token");
			return "loginForm";
		}
		else {
			uservice.updatePassword(user, password);
			model.addAttribute("message", "you have successfully updated your password");
			return "LoginForm";
		}
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void sendEmail(String email,String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message=mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message);
		helper.setFrom("gestionDeFilieres@test.com","gestion des filieres");
		helper.setTo(email);
		String subject="Reset password link";
		String content="<p>Hello,</p> "+"you have requested reset password link"+" click below to change your password"+
		
				
		"<p><a href=\""+resetPasswordLink+"\">Change my password</a></p>";
		
		helper.setSubject(subject);
		helper.setText(content,true);
		mailSender.send(message);
		
	}

	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value="token")String token,Model model) {
		User user=uservice.get(token);
		if(user==null) {
			model.addAttribute("message", "invalid token");
		}
		model.addAttribute("token", token);
		
		return "reset_passowrd_form";
		
	}
}
