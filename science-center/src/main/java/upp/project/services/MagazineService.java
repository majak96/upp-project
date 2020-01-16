package upp.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Magazine;
import upp.project.repositories.MagazineRepository;

@Service
public class MagazineService {

	@Autowired
	MagazineRepository magazineRepository;
	
	public Magazine save(Magazine magazine) {
		
		return magazineRepository.save(magazine);
	}

}
