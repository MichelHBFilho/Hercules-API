package br.com.michel.hercules;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.michel.hercules.model.User;
import br.com.michel.hercules.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthTest {

	@Autowired
	private MockMvc mmvc;
	@Autowired
	private UserRepository userRepo;
	
	@SuppressWarnings("unchecked")
	@Test
	void authTest() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", "james");
		json.put("password", "password");
		User user = new User();
		user.setAndEncodePassword("password");
		user.setEmail("james");
		userRepo.save(user);
		
		mmvc.perform(MockMvcRequestBuilders.post(new URI("/api/auth"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString()))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void badAuthTest() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", "james");
		json.put("password", "password");
		mmvc.perform(MockMvcRequestBuilders.post(new URI("/api/auth"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString()))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}

}
