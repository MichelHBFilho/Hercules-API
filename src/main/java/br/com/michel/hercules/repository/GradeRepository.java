package br.com.michel.hercules.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.michel.hercules.model.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {

}
