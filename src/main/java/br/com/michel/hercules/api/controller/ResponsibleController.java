package br.com.michel.hercules.api.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.michel.hercules.api.controller.form.ResponsibleForm;
import br.com.michel.hercules.exceptions.NonExistentResponsible;
import br.com.michel.hercules.model.Responsible;
import br.com.michel.hercules.repository.ResponsibleRepository;

@RestController
@RequestMapping("/api")
public class ResponsibleController {
	
	@Autowired
	private ResponsibleRepository responsibleRepository;
	
	@GetMapping("/responsible/{responsibleName}")
	public ResponsibleDto getResponsible(
			@PathVariable String responsibleName
	) {
		Optional<Responsible> optional = responsibleRepository.findByName(responsibleName);
		
		if(optional.isEmpty())
			throw new NonExistentResponsible();
		
		return new ResponsibleDto(optional.get());
	}
	
	@PostMapping("/responsible")
	public ResponseEntity<ResponsibleDto> newResponsible(
			@RequestBody @Valid ResponsibleForm form
	) {
		Responsible responsible = form.toResponsible();
		responsibleRepository.save(responsible);
		
		URI uri = URI.create("/responsible/" + responsible.getName());
		return ResponseEntity.created(uri).body(new ResponsibleDto(responsible));
	}
}
