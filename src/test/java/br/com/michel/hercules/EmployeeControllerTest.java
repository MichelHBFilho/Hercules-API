package br.com.michel.hercules;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.michel.hercules.model.Employee;
import br.com.michel.hercules.model.Profile;
import br.com.michel.hercules.repository.EmployeeRepository;
import br.com.michel.hercules.repository.ProfileRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeControllerTest {

	@Autowired
	private MockMvc mmvc;
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@BeforeAll
	void arrange() {
		Profile profile = new Profile();
		profile.setAuthority("ROLE_TEACHER");
		profileRepository.save(profile);
		Profile profile2 = new Profile();
		profile.setAuthority("ROLE_EMPLOYEE");
		profileRepository.save(profile2);
	}
	
	@AfterEach
	void deleteEmployees() {
		employeeRepository.deleteAll();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testNewEmployee() throws Exception {
		JSONObject json = new JSONObject();
		json.put("name", "john");
		json.put("cpf", "05668586065");
		json.put("email", "john@gmail.com");
		
		mmvc.perform(MockMvcRequestBuilders.post(new URI("/api/employee"))
					.contentType(MediaType.APPLICATION_JSON)
					.content(json.toString()))
			.andDo(print())
			.andExpect(status().isCreated());
		
		Employee employee = employeeRepository.findByCpf("05668586065").get();
		
		mmvc.perform(MockMvcRequestBuilders.get(new URI("/api/employee/" + employee.getId())))
			.andDo(print())
			.andExpect(status().isOk());
		
		json.clear();
		json.put("email", "john@gmail.com");
		json.put("password", "05668586065");
		
		mmvc.perform(MockMvcRequestBuilders.post(new URI("/api/auth"))
				.content(json.toString())
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	void testNewTeacher() throws Exception {
		JSONObject json = new JSONObject();
		json.put("name", "carla");
		json.put("cpf", "35386441019");
		
		mmvc.perform(MockMvcRequestBuilders.post(new URI("/api/employee"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.param("teacher", "yes"))
			.andDo(print())
			.andExpect(status().isCreated());
		
		Employee employee = employeeRepository.findByCpf("35386441019").get();
		
		mmvc.perform(MockMvcRequestBuilders.get(new URI("/api/employee/" + employee.getId())))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void deleteEmployee() throws Exception {
		JSONObject json = new JSONObject();
		json.put("name", "carla");
		json.put("cpf", "35386441019");
		
		mmvc.perform(MockMvcRequestBuilders.post(new URI("/api/employee"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.param("teacher", "yes"))
			.andDo(print())
			.andExpect(status().isCreated());
		
		Employee employee = employeeRepository.findByCpf("35386441019").get();
		
		mmvc.perform(MockMvcRequestBuilders.delete(new URI("/api/employee/" + employee.getId())))
			.andDo(print())
			.andExpect(status().isOk());
	}
}
