package br.com.michel.hercules;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.michel.hercules.model.Adress;
import br.com.michel.hercules.model.Employee;
import br.com.michel.hercules.model.Profile;
import br.com.michel.hercules.model.Responsible;
import br.com.michel.hercules.model.User;
import br.com.michel.hercules.repository.EmployeeRepository;
import br.com.michel.hercules.repository.ProfileRepository;
import br.com.michel.hercules.repository.ResponsibleRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ResponsibleControllerTest {

	@Autowired
	private MockMvc mmvc;
	@Autowired
	private ResponsibleRepository respRepo;
	@Autowired
	private EmployeeRepository empRepo;
	@Autowired
	private ProfileRepository profRepo;
	
	private static JSONParser parser = new JSONParser();
	
	private String auth(String email, String password) throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", email);
		json.put("password", password);
		MvcResult result = mmvc.perform(MockMvcRequestBuilders.post(new URI("/api/auth"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString()))
			.andReturn();
		return (String) ((JSONObject)parser.parse(result.getResponse().getContentAsString())).get("token");
	}
	
	@BeforeEach
	public void arrange() {
		
		Profile proEmp = new Profile();
		Profile proResp = new Profile();
		
		proEmp.setAuthority("ROLE_EMPLOYEE");
		proResp.setAuthority("ROLE_RESPONSIBLE");
		
		profRepo.saveAll(List.of(proEmp, proResp));
		
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		
		user1.setAndEncodePassword("secret");
		user1.setEmail("email@email.com");
		user2.setAndEncodePassword("secret");
		user2.setEmail("imail@imail.com");
		user3.setAndEncodePassword("secret");
		user3.setEmail("omail@omail.com");
		
		Responsible r = new Responsible();
		Responsible r2 = new Responsible();
		Employee e = new Employee();
		

		Adress a1 = new Adress();
		a1.setComplement("oi");
		a1.setNeighborhood("nei");
		a1.setStreet("little stit");
		Adress a2 = new Adress();
		a2.setComplement(a1.getComplement());
		a2.setNeighborhood(a1.getNeighborhood());
		a2.setStreet(a1.getStreet());
		
		user1.addProfile(proResp);
		r.setLogin(user1);
		r.setCpf("31818084074");
		r.setName("Great Name");
		r.setAdress(a1);
		
		user3.addProfile(proResp);
		r2.setLogin(user3);
		r2.setAdress(a2);
		r2.setName("Shit Name");
		r2.setCpf("80202842061");
		
		user2.addProfile(proEmp);
		e.setLogin(user2);
		e.setContractDate(LocalDate.now());
		e.setCpf("20299747069");
		e.setName("Real Name");
		
		respRepo.saveAll(List.of(r, r2));
		empRepo.save(e);
	}
	
	@AfterEach
	public void delete() {
		respRepo.deleteAll();
		empRepo.deleteAll();
		profRepo.deleteAll();
	}
	
	@Test
	void testGetResponsibleShouldReturn200() throws Exception {
		String token = auth("email@email.com", "secret");
		mmvc.perform(MockMvcRequestBuilders.get(new URI("/api/responsible/Great%20Name"))
				.header("Authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	void testGetResponsibleShouldReturn200WithEmployee() throws Exception {
		String token = auth("imail@imail.com", "secret");
		mmvc.perform(MockMvcRequestBuilders.get(new URI("/api/responsible/Great%20Name"))
				.header("Authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isOk());
		
		mmvc.perform(MockMvcRequestBuilders.get(new URI("/api/responsible/Shit%20Name"))
				.header("Authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	void testGetResponsibleShouldReturn401() throws Exception {
		String token = auth("email@email.com", "secret");
		mmvc.perform(MockMvcRequestBuilders.get(new URI("/api/responsible/Shit%20Name"))
				.header("Authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isUnauthorized());
	}

	@Test
	void testNewResponsibleShouldReturn201() throws Exception {
		JSONObject json = new JSONObject();
		json.put("name", "naime");
		json.put("cpf", "55983331043");
		json.put("street", "on top of the world");
		json.put("neighborhood", "godness");
		
		String token = auth("imail@imail.com", "secret");
		mmvc.perform(MockMvcRequestBuilders.post(new URI("/api/responsible"))
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString()))
			.andDo(print())
			.andExpect(status().isCreated());
	}
	
	@Test
	void testNewResponsibleShouldReturn401() throws Exception {
		JSONObject json = new JSONObject();
		json.put("name", "naime");
		json.put("cpf", "55983331043");
		json.put("street", "on top of the world");
		json.put("neighborhood", "godness");
		
		String token = auth("email@email.com", "secret");
		mmvc.perform(MockMvcRequestBuilders.post(new URI("/api/responsible"))
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString()))
			.andDo(print())
			.andExpect(status().isUnauthorized());
	}

	@Test
	void testDeleteResponsibleShouldReturn200() throws Exception {
		String token = auth("imail@imail.com", "secret");
		mmvc.perform(MockMvcRequestBuilders.delete(new URI("/api/responsible/Great%20Name"))
				.header("Authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isOk());
		
		mmvc.perform(MockMvcRequestBuilders.delete(new URI("/api/responsible/Shit%20Name"))
				.header("Authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteResponsibleShouldReturn401() throws Exception {
		String token = auth("email@email.com", "secret");
		mmvc.perform(MockMvcRequestBuilders.delete(new URI("/api/responsible/Great%20Name"))
				.header("Authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isUnauthorized());
		
		mmvc.perform(MockMvcRequestBuilders.delete(new URI("/api/responsible/Shit%20Name"))
				.header("Authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isUnauthorized());
	}

}
