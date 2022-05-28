package org.pfe.controllers;



import javax.transaction.Transactional;

import org.pfe.dao.UserRepository;
import org.pfe.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository<User> userep;
	
	public void updateResetPassword(String token,String email) throws UserNotFoundException
	{
		
		
		User user=userep.getUserByEmail(email);
		if(user !=null) {
			user.setResetPasswordToken(token);
			userep.save(user);
		}
		else {
			throw new UserNotFoundException("could not find any user with email: "+email);
		}
		
		
	}
	public User get(String restPsswordToken) {
		return userep.findByResetPasswordToken(restPsswordToken);
		
	}
	public void updatePassword(User user,String newPassword) {
		 
		BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		String encodedPassword=passwordEncoder.encode(newPassword);
		user.setPassword(encodedPassword);
		user.setResetPasswordToken(null);
		userep.save(user);
	}

}
