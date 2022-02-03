package br.com.michel.hercules.repository;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.michel.hercules.model.Responsible;

public interface ResponsibleRepository extends JpaRepository<Responsible, Long> {

	Responsible findByCpf(String responsibleCpf);

}
