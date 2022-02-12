package br.com.michel.hercules;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.michel.hercules.repository.StudentRepository;

@DataJpaTest
@ActiveProfiles("test")
class StudentRepositoryTest {

	@Autowired
	private StudentRepository studentRepository;
	
	@Test
	void testFindAll() {
		Assertions.assertTrue(studentRepository.findAll().isEmpty());
	}

}
