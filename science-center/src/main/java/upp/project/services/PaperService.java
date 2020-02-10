package upp.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Paper;
import upp.project.repositories.PaperRepository;

@Service
public class PaperService {
	
	@Autowired
	PaperRepository paperRepository;
	
	public Paper save(Paper paper) {
		
		return paperRepository.save(paper);
	}
	
	public Paper findById(Long id) {
		
		return paperRepository.getOne(id);
	}

}
