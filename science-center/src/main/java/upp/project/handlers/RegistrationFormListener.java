package upp.project.handlers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.ScientificArea;
import upp.project.services.ScientificAreaService;

@Service
public class RegistrationFormListener implements TaskListener{ 
	
	@Autowired
	ScientificAreaService scientificAreaService;
	
	public void notify(DelegateTask delegateTask) {
			
		List<ScientificArea> scientificAreas = scientificAreaService.findAll();
				
		TaskFormData formData = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
		List<FormField> formFields = formData.getFormFields();
		
		if(formFields != null) {				
			for(FormField field : formFields) {			
				if(field.getId().equals("form_scientific_area")) {
					Map<String, String> valuesMap = (LinkedHashMap<String, String>) field.getType().getInformation("values");
					
					// fill enum field with scientific areas
					for(ScientificArea sciArea : scientificAreas) {
						valuesMap.put(sciArea.getId().toString(), sciArea.getName());
					}
				}
			}
		}
	
	}

}
