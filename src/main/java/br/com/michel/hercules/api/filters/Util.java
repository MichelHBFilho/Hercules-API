package br.com.michel.hercules.api.filters;

import java.util.List;

import br.com.michel.hercules.model.Profile;
import br.com.michel.hercules.model.User;

public class Util {
	
	private Util() {}
	
	public static boolean isEmployee(User user) {
		List<Profile> profiles = (List<Profile>) user.getAuthorities();
		
		for (Profile p : profiles) {
			if(p.getAuthority().equals("ROLE_EMPLOYEE"))
				return true;
		}
		
		return false;
	}

}
