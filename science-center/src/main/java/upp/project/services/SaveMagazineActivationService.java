package upp.project.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Magazine;

@Service
public class SaveMagazineActivationService implements JavaDelegate{
	
	@Autowired
	MagazineService magazineService;


	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("Activating magazine");
		
		Long magazineId = (Long) execution.getVariable("magazineId");
		
		Magazine magazine = magazineService.findById(magazineId);
		
		//activate the magazine
		if(magazine != null) {
			magazine.setActive(true);
			magazineService.save(magazine);
		}
		
	}
}