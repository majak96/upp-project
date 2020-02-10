package upp.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upp.project.model.ScientificArea;
import upp.project.services.ScientificAreaService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/scientificarea", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScientificAreaController {
	
	@Autowired
	ScientificAreaService scientificAreaService;

	@GetMapping("")
	ResponseEntity<?> getAllScientificAreas() {
		
		List<ScientificArea> scientificAreas = scientificAreaService.findAll();
		
		return ResponseEntity.ok(scientificAreas);
	}
}
