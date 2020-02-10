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
public class SaveMagazineService implements JavaDelegate {
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	ScientificAreaService scientificAreasService;
	
	@Autowired
	UserService userService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("MAG | saving the magazine");
						
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("formData");
		
		//create and save a new magazine
		Magazine newMagazine = createMagazine(formValues);			
		magazineService.save(newMagazine);		
		
		//save magazine id as a process variable
		execution.setVariable("magazineId", newMagazine.getId());
		
	}
	
	private Magazine createMagazine(List<FormValueDTO> formValues) {
		HashMap<String, Object> valuesMap = new HashMap<String, Object>();
		
		for(FormValueDTO value : formValues) {
			valuesMap.put(value.getId(), value.getValue());
		}
		
		//create the magazine
		Magazine magazine = new Magazine((String)valuesMap.get("form_name"), (String)valuesMap.get("form_issn"), (Integer)valuesMap.get("form_price"));
		
		//set payment type
		if(valuesMap.get("form_payment").equals("authors")) {
			magazine.setPaymentType(PaymentType.AUTHORS);
		}
		else {
			magazine.setPaymentType(PaymentType.READERS);
		}
		
		//set chosen scientific areas
		List<String> areas = (List<String>) valuesMap.get("form_scientific_area");		
		for(String area : areas) {
			magazine.getScientificAreas().add(scientificAreasService.findByName(area));
		}
		
		//set logged in user as the editor in chief
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		RegisteredUser loggedInUser = userService.findByUsername(username);
		
		if(loggedInUser != null && loggedInUser.getAuthority().getRole() == Role.ROLE_EDITOR) {
			magazine.setEditorInChief(loggedInUser);
		}
					
		return magazine;
	}

}
