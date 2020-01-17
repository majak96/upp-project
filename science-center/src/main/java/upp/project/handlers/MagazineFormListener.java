package upp.project.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;
import upp.project.model.RegisteredUser;
import upp.project.model.Role;
import upp.project.model.ScientificArea;
import upp.project.services.ScientificAreaService;
import upp.project.services.UserService;

@Service
public class MagazineFormListener implements TaskListener{ 
	
	@Autowired
	UserService userService;
	
	@Autowired
	ScientificAreaService scientificAreaService;
	
	public void notify(DelegateTask delegateTask) {
	
		TaskFormData formData = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
		List<FormField> formFields = formData.getFormFields();
		
		List<FormValueDTO> formValues = (List<FormValueDTO>) delegateTask.getExecution().getVariable("newMagazineFormValues");

		HashMap<String, String> valuesMap = new HashMap<String,String>();
		
		for(FormValueDTO value : formValues) {
			valuesMap.put(value.getId(), value.getValue());
		}
		
		//find chosen scientific areas
		String[] areas = valuesMap.get("form_scientific_area").split(",");
		
		List<ScientificArea> scientificAreas = new ArrayList<ScientificArea>();
		for(String area : areas) {
			scientificAreas.add(scientificAreaService.findByName(area));
		}
		
		//find editors and reviewers for chosen scientific areas
		List<RegisteredUser> editorList = userService.findByScientificAreas(scientificAreas, Role.ROLE_EDITOR);
		List<RegisteredUser> reviewerList = userService.findByScientificAreas(scientificAreas, Role.ROLE_REVIEWER);
		
		if(formFields != null) {				
			for(FormField field : formFields) {			
				if(field.getId().equals("form_reviewers")) {
					Map<String, String> map = (LinkedHashMap<String, String>) field.getType().getInformation("values");
					
					map.clear();
					
					// fill enum field with reviewers
					for(RegisteredUser reviewer : reviewerList) {
						map.put(reviewer.getId().toString(), reviewer.getUsername());
					}
				}
				
				if(field.getId().equals("form_editors")) {
					Map<String, String> map = (LinkedHashMap<String, String>) field.getType().getInformation("values");
					
					map.clear();
					
					// fill enum field with editors
					for(RegisteredUser editor : editorList) {
						map.put(editor.getId().toString(), editor.getUsername());
					}
				}
			}
		}
	}
}
