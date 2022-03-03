package br.com.michel.hercules;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import br.com.michel.hercules.model.SchoolClass;
import br.com.michel.hercules.model.Student;
import br.com.michel.hercules.model.User;
import br.com.michel.hercules.repository.EmployeeRepository;
import br.com.michel.hercules.repository.ProfileRepository;
import br.com.michel.hercules.repository.ResponsibleRepository;
import br.com.michel.hercules.repository.SchoolClassRepository;
import br.com.michel.hercules.repository.StudentRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentsControllerTest {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ResponsibleRepository responsibleRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private SchoolClassRepository schoolClassRepository;
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private MockMvc mmvc;
	
	@BeforeEach
	private void arrange() {
		User user1 = new User();
		user1.setAndEncodePassword("secretpassword");
		user1.setEmail("email@email.com");
		User user2 = new User();
		user2.setAndEncodePassword("passwordsecret");
		user2.setEmail("gmail@gmail.com");
		User user3 = new User();
		user3.setAndEncodePassword("nicepassword");
		user3.setEmail("yahoo@yahoo.com");
		User user4 = new User();
		user4.setAndEncodePassword("shitnicepass");
		user4.setEmail("outlook@outlook.com");
		
		Profile profileEmployee = new Profile();
		Profile profileStudent = new Profile();
		Profile profileResponsible = new Profile();
		
		profileEmployee.setAuthority("ROLE_EMPLOYEE");
		profileResponsible.setAuthority("ROLE_RESPONSIBLE");
		profileStudent.setAuthority("ROLE_STUDENT");
		
		profileRepository.save(profileEmployee);
		profileRepository.save(profileResponsible);
		profileRepository.save(profileStudent);
		
		Employee employee = new Employee();
		Student student = new Student();
		Responsible responsible = new Responsible();
		Responsible responsible2 = new Responsible();
		
		employee.setContractDate(LocalDate.now());
		employee.setCpf("83492361080");
		employee.setLogin(user1);
		user1.addProfile(profileEmployee);
		employee.setName("Nice Name");
		
		student.setBirthDay(LocalDate.now());
		student.setCpf("51783947047");
		student.setLogin(user2);
		user2.addProfile(profileStudent);
		student.setName("Real Nice Name");
		student.setRegister("00000");
		student.setResponsible(responsible);
		student.setSchoolClass(new SchoolClass());
		student.setStudentPicture("/pics/picture");
		
		responsible.setAdress(new Adress());
		responsible.setCpf("01190735016");
		responsible.setLogin(user3);
		user3.addProfile(profileResponsible);
		responsible.setName("Shit this is a nice Name");
		responsible.setStudents(List.of(student));
		
		responsible2.setAdress(new Adress());
		responsible2.setCpf("01190735016");
		responsible2.setLogin(user4);
		user4.addProfile(profileResponsible);
		responsible2.setName("Shit this is a nice Name");
		
		schoolClassRepository.save(student.getSchoolClass());
		responsibleRepository.save(responsible);
		responsibleRepository.save(responsible2);
		studentRepository.save(student);
		employeeRepository.save(employee);
	}

	@AfterEach
	private void clear() {
		studentRepository.deleteAll();
		responsibleRepository.deleteAll();
		employeeRepository.deleteAll();
		schoolClassRepository.deleteAll();
		profileRepository.deleteAll();
	}
	
	@Test
	void testFindStudentShouldReturn401() throws Exception {
		String token = TestUtil.auth("outlook@outlook.com", "shitnicepass", mmvc);
		mmvc.perform(MockMvcRequestBuilders.get(new URI("/api/student/00000"))
				.header("Authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	void testFindStudentShouldReturn200WithStudent() throws Exception {
		String token = TestUtil.auth("gmail@gmail.com", "passwordsecret", mmvc);
		mmvc.perform(MockMvcRequestBuilders.get(new URI("/api/student/00000"))
				.header("Authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	void testFindStudentShouldReturn200WithResponsible() throws Exception {
		String token = TestUtil.auth("yahoo@yahoo.com", "nicepassword", mmvc);
		mmvc.perform(MockMvcRequestBuilders.get(new URI("/api/student/00000"))
				.header("Authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void testDeleteStudentShouldReturn200() throws Exception {
		String token = TestUtil.auth("email@email.com", "secretpassword", mmvc);
		mmvc.perform(MockMvcRequestBuilders.delete(new URI("/api/student/00000"))
				.header("Authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteStudentShouldReturn401() throws Exception {
		String token = TestUtil.auth("gmail@gmail.com", "passwordsecret", mmvc);
		mmvc.perform(MockMvcRequestBuilders.delete(new URI("/api/student/00000"))
				.header("Authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isUnauthorized());
	}

}
