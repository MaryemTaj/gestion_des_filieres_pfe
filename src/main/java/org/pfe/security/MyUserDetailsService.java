package org.pfe.security;

import org.pfe.dao.UserRepository;
import org.pfe.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {
	
	
	
	@Autowired
	private UserRepository<User> userRep;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user=userRep.getUserByEmail(email);
		if(user==null) {
			throw new UsernameNotFoundException("Could not fin email");
		}
		return new MyUserDetails(user);
	}

}
