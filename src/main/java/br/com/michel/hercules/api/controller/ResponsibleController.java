package br.com.michel.hercules.api.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.michel.hercules.api.controller.dto.ResponsibleDto;
import br.com.michel.hercules.api.controller.form.ResponsibleForm;
import br.com.michel.hercules.exceptions.NotFoundException;
import br.com.michel.hercules.model.Responsible;
import br.com.michel.hercules.repository.ProfileRepository;
import br.com.michel.hercules.repository.ResponsibleRepository;

@RestController
@RequestMapping("/api")
public class ResponsibleController {
	
	@Autowired
	private ResponsibleRepository responsibleRepository;
	@Autowired
	private ProfileRepository profileRepository;
	
	@GetMapping("/responsible/{responsibleName}")
	public ResponsibleDto getResponsible(
			@PathVariable String responsibleName
	) {
		Optional<Responsible> optional = responsibleRepository.findByName(responsibleName);
		
		if(optional.isEmpty())
			throw new NotFoundException("Responsible");
		
		return new ResponsibleDto(optional.get());
	}
	
	@PostMapping("/responsible")
	public ResponseEntity<ResponsibleDto> newResponsible(
			@RequestBody @Valid ResponsibleForm form
	) {
		Responsible responsible = form.toResponsible(profileRepository);
		responsibleRepository.save(responsible);
		
		URI uri = URI.create("/responsible/" + responsible.getName());
		return ResponseEntity.created(uri).body(new ResponsibleDto(responsible));
	}
	
	@DeleteMapping("/responsible/{responsibleName}")
	public ResponseEntity<?> deleteResponsible(
			@PathVariable String responsibleName
	) {
		Optional<Responsible> optional = responsibleRepository.findByName(responsibleName);
		
		if(optional.isEmpty())
			throw new NotFoundException("Responsible");
		
		responsibleRepository.delete(optional.get());
		
		return ResponseEntity.ok().build();
	}
}
