package upp.project.services.camunda.magazine;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;
import upp.project.model.Magazine;
import upp.project.model.PaymentType;
import upp.project.model.RegisteredUser;
import upp.project.model.Role;
import upp.project.services.MagazineService;
import upp.project.services.ScientificAreaService;
import upp.project.services.UserService;

@Service
public class UpdateMagazineService implements JavaDelegate{
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	ScientificAreaService scientificAreasService;
	
	@Autowired
	UserService userService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("MAG | updating the magazine");
		
		Long magazineId = (Long) execution.getVariable("magazineId");

		Magazine magazine = magazineService.findById(magazineId);
						
		if(magazine != null) {
			String name = (String) execution.getVariable("form_name");
			String issn = (String) execution.getVariable("form_issn");
			String payment = (String) execution.getVariable("form_payment");
			
			//update values
			magazine.setName(name);
			magazine.setISSN(issn);

			if(payment.equals("authors")) {
				magazine.setPaymentType(PaymentType.AUTHORS);
			}
			else {
				magazine.setPaymentType(PaymentType.READERS);
			}
			
			//save magazine
			magazineService.save(magazine);
		}		
	}
	

}
