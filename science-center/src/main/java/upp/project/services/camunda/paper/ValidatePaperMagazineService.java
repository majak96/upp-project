package upp.project.services.camunda.paper;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Magazine;
import upp.project.services.MagazineService;

@Service
public class ValidatePaperMagazineService implements JavaDelegate {

	@Autowired
	MagazineService magazineService;
	
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | validation of the magazine chosen for the paper");
		
		boolean validation = true; 
		
		String chosenMagazine = (String) execution.getVariable("form_magazine");
		
		// check for required fields
		if(chosenMagazine == null) {
			validation = false;
		}
		else {		
			Long magazineId = Long.parseLong(chosenMagazine);
			
			//check if magazine exists
			Magazine magazine = magazineService.findById(magazineId);
			
			if(magazine == null) {
				validation = false;
			}			
		}
			
		if(!validation) {
			execution.setVariable("data_check", false);
		}
		else {
			execution.setVariable("data_check", true);
		}			
	}		
}
