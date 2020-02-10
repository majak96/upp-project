package upp.project.services.camunda.paper;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Magazine;
import upp.project.model.PaymentType;
import upp.project.services.MagazineService;

@Service
public class SavePaperMagazineService implements JavaDelegate{
	
	@Autowired
	MagazineService magazineService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | saving the magazine for the paper.");
		
		String chosenMagazine = (String) execution.getVariable("form_magazine");		
		Long magazineId = Long.parseLong(chosenMagazine);
		
		Magazine magazine = magazineService.findById(magazineId);
		
		//check if magazine is open access
		if(magazine != null) {
			
			execution.setVariable("open_access", magazine.getPaymentType() == PaymentType.AUTHORS ? true : false);
		}
	}
}
