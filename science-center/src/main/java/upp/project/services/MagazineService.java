package upp.project.services;

import java.util.List;

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
	
	public List<Magazine> getActiveMagazines() {
		
		return magazineRepository.findByActive(true);
	}
	
	public Magazine findByISSN(String ISSN) {
		
		return magazineRepository.findByISSN(ISSN);
	}
	
	public Magazine findById(Long id) {
		
		return magazineRepository.getOne(id);
	}

}
