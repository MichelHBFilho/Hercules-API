package br.com.michel.hercules;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.michel.hercules.model.Employee;
import br.com.michel.hercules.model.Profile;
import br.com.michel.hercules.model.SchoolClass;
import br.com.michel.hercules.model.User;
import br.com.michel.hercules.repository.EmployeeRepository;
import br.com.michel.hercules.repository.ProfileRepository;
import br.com.michel.hercules.repository.SchoolClassRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SchoolClassControllerTest {

	@Autowired
	private MockMvc mmvc;
	@Autowired
	private EmployeeRepository empRepo;
	@Autowired
	private SchoolClassRepository schclassRepo;
	@Autowired
	private ProfileRepository profileRepository;
	
	@BeforeEach
	void arrange() {
		Employee teacher = new Employee();
		teacher.setContractDate(LocalDate.now());
		teacher.setCpf("25150477001");
		teacher.setName("nome");
		User user = new User();
		Profile profile = new Profile();
		profile.setAuthority("ROLE_EMPLOYEE");
		profileRepository.save(profile);
		user.setAndEncodePassword("pass");
		user.setEmail("email");
		user.addProfile(profile);
		teacher.setLogin(user);
		
		empRepo.save(teacher);
	}
	
	@AfterEach
	void delete() {
		schclassRepo.deleteAll();
		empRepo.deleteAll();
		profileRepository.deleteAll();
	}
	
	@Test
	void testNewClass() throws Exception {
		String token = TestUtil.auth("email", "pass", mmvc);
		
		JSONObject json = new JSONObject();
		json.put("classNumber", "101");
		json.put("year", "8");
		json.put("room", "First at left");
		
		mmvc.perform(MockMvcRequestBuilders.post(new URI("/api/class"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.header("Authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isCreated());
	}

	@Test
	void testAddTeacher() throws Exception {
		String token = TestUtil.auth("email", "pass", mmvc);
		
		SchoolClass sc = new SchoolClass();
		sc.setClassNumber(101);
		sc.setRoom("at left");
		sc.setYear(9);
		
		schclassRepo.save(sc);
		
		mmvc.perform(MockMvcRequestBuilders.post(new URI("/api/class/101/addTeacher"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("25150477001")
				.header("Authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isOk());
		
		sc = schclassRepo.findByClassNumber(101).get();
	}

	@Test
	void testDeleteTeacher() throws Exception {
		String token = TestUtil.auth("email", "pass", mmvc);
		
		SchoolClass sc = new SchoolClass();
		sc.setClassNumber(101);
		sc.setRoom("at left");
		sc.setYear(9);
		sc.addTeacher(empRepo.findByCpf("25150477001").get());
		
		schclassRepo.save(sc);
		
		mmvc.perform(MockMvcRequestBuilders.delete(new URI("/api/class/101/deleteTeacher"))
				.param("teacherCPF", "25150477001")
				.header("Authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isOk());
		
		sc = schclassRepo.findByClassNumber(101).get();
	}

}
