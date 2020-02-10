package upp.project.services.camunda.magazine;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;
import upp.project.model.Magazine;
import upp.project.services.MagazineService;

@Service
public class ValidateMagazineService implements JavaDelegate{
	
	@Autowired
	MagazineService magazineService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("MAG | validation of magazine data");
		
		Long magazineId = (Long) execution.getVariable("magazineId");
				
		boolean validation = true;
		
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("formData");
						
		for(FormValueDTO formValue : formValues) {
			// check for required fields
			if(formValue.getValue() == null) {
				validation = false;
				break;
			}
			//check specific fields
			else if(formValue.getId().equals("form_issn")) {
				if(!validateISSN((String) formValue.getValue(), magazineId)) {
					validation = false;
					break;
				}
			}
			else if(formValue.getId().equals("form_scientific_area")) {	
				if(!validateScientificAreas((List<String>)formValue.getValue())) {
					validation = false;
					break;
				}
			}
			else if(formValue.getId().equals("form_price")) {
				if(!validatePrice((Integer)formValue.getValue())) {
					validation = false;
					break;
				}
			}
		}
		
		if(!validation) {
			execution.setVariable("data_check", false);
		}
		else {
			execution.setVariable("data_check", true);
		}
	}
	
	private boolean validateScientificAreas(List<String> scientificAreas) {				
		if(scientificAreas.size() >= 1) {
			return true;
		}
		
		return false;
	}
	
	private boolean validatePrice(Integer price) {
		if(price >= 0) {
			
			return true;
		}
		
		return false;
	}
	
	private boolean validateISSN(String ISSN, Long magazineId) {		
		String regex = "[0-9]+";
		
		Pattern pattern = Pattern.compile(regex);	
		Matcher matcher = pattern.matcher(ISSN);
		
		Magazine thisMagazine = null;
		if(magazineId != null) {
		 thisMagazine = magazineService.findById(magazineId);
		}

		Magazine magazine = magazineService.findByISSN(ISSN);
		
		System.out.println(magazine);
		
		if((magazine == null || magazine.equals(thisMagazine)) && ISSN.length() == 8 && matcher.matches()) {
			return true;
		}
		
		return false;
	}

}
