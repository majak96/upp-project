package upp.project.services;

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

@Service
public class SaveMagazineService implements JavaDelegate{
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	ScientificAreaService scientificAreasService;
	
	@Autowired
	UserService userService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
						
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("newMagazineFormValues");
		
		//create and save a new magazine
		Magazine newMagazine = createMagazine(formValues);			
		magazineService.save(newMagazine);		
		
	}
	
	private Magazine createMagazine(List<FormValueDTO> formValues) {
		HashMap<String, String> valuesMap = new HashMap<String,String>();
		
		for(FormValueDTO value : formValues) {
			valuesMap.put(value.getId(), value.getValue());
		}
		
		//create the magazine
		Magazine magazine = new Magazine(valuesMap.get("form_name"), valuesMap.get("form_issn"));
		
		
		//set payment type
		if(valuesMap.get("form_payment").equals("authors")) {
			magazine.setPaymentType(PaymentType.AUTHORS);
		}
		else {
			magazine.setPaymentType(PaymentType.READERS);
		}
		
		//set chosen scientific areas
		String [] areas = valuesMap.get("form_scientific_area").split(",");
		
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
