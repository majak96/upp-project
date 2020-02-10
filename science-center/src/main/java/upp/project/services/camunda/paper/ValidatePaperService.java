package upp.project.services.camunda.paper;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;

@Service
public class ValidatePaperService implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | validation of paper data");
		
		boolean validation = true; 
		
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("formData");
				
		for(FormValueDTO value : formValues) {
			// check for required fields
			if(value.getValue() == null) {
				validation = false;
				break;
			}
			//check specific fields
			else if(value.getId().equals("form_coauthors_number")) {
				if(!validateCoauthors((Integer) value.getValue())){
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
	
	private boolean validateCoauthors(Integer coauthorsNumber) {
		if(coauthorsNumber >= 0) {
			return true;
		}
		
		return false;
	}
}
