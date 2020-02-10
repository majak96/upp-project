package upp.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.CoAuthor;
import upp.project.repositories.CoAuthorRepository;

@Service
public class CoAuthorService {

	@Autowired
	CoAuthorRepository coAuthorRepository;
	
	public CoAuthor save(CoAuthor coAuthor) {
		
		return coAuthorRepository.save(coAuthor);
	}
	
}
