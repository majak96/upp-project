package upp.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.ScientificArea;
import upp.project.repositories.ScientificAreaRepository;

@Service
public class ScientificAreaService {

	@Autowired
	ScientificAreaRepository scientificAreaRepository;
	
	public List<ScientificArea> findAll() {
		
		return scientificAreaRepository.findAll();
	}
	
	public ScientificArea findByName(String name) {
		
		return scientificAreaRepository.findByName(name);
	}
	
	public ScientificArea findById(Long id) {
		
		return scientificAreaRepository.getOne(id);
	}

}
