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

import upp.project.model.RegisteredUser;
import upp.project.model.Role;
import upp.project.model.ScientificArea;
import upp.project.services.UserService;

@Service
public class MagazineFormListener implements TaskListener{ 
	
	@Autowired
	UserService userService;
	
	public void notify(DelegateTask delegateTask) {
			
		List<RegisteredUser> editorList = userService.findByRole(Role.ROLE_EDITOR);
		List<RegisteredUser> reviewerList = userService.findByRole(Role.ROLE_REVIEWER);
	
		
		TaskFormData formData = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
		List<FormField> formFields = formData.getFormFields();
		
		if(formFields != null) {				
			for(FormField field : formFields) {			
				if(field.getId().equals("form_reviewers")) {
					Map<String, String> valuesMap = (LinkedHashMap<String, String>) field.getType().getInformation("values");
					
					// fill enum field with reviewers
					for(RegisteredUser reviewer : reviewerList) {
						valuesMap.put(reviewer.getId().toString(), reviewer.getFirstName() + " " + reviewer.getLastName());
					}
				}
				
				if(field.getId().equals("form_editors")) {
					Map<String, String> valuesMap = (LinkedHashMap<String, String>) field.getType().getInformation("values");
					
					// fill enum field with editorss
					for(RegisteredUser editor : editorList) {
						valuesMap.put(editor.getId().toString(), editor.getFirstName() + " " + editor.getLastName());
					}
				}
			}

		}
	
	}

}
