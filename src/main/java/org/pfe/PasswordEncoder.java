package org.pfe;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
	public static void main(String[] args) {
		BCryptPasswordEncoder enc=new BCryptPasswordEncoder();
		System.out.println(enc.encode("1234"));
		
	}

}
