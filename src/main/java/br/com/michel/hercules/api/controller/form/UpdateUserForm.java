package br.com.michel.hercules.api.controller.form;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import br.com.michel.hercules.exceptions.NotFoundException;
import br.com.michel.hercules.model.User;
import br.com.michel.hercules.repository.UserRepository;

public class UpdateUserForm {

	@NotBlank
	@Email
	public String email;
	@NotBlank
	public String password;
	public String newEmail;
	public String newPassword;

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

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public User toUser(UserRepository userRepository) {
		Optional<User> optional = userRepository.findByEmail(email);
		
		if(optional.isEmpty())
			throw new NotFoundException();
		
		User user = optional.get();
		
		if(this.newEmail != null)
			user.setEmail(newEmail);
		if(this.newPassword != null)
			user.setAndEncodePassword(newPassword);
		
		return user;
	}

}
