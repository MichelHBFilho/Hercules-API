package br.com.michel.hercules.api.controller.form;

import br.com.michel.hercules.model.User;
import br.com.michel.hercules.repository.UserRepository;

public class UpdateUserForm {

	public String email;
	public String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User toUser(UserRepository userRepository, User user) {
		if (this.email != null)
			user.setEmail(email);
		if (this.password != null)
			user.setAndEncodePassword(password);

		return user;
	}

}
