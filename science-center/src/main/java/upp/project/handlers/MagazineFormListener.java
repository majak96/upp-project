package upp.project.handlers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import upp.project.model.Magazine;
import upp.project.model.RegisteredUser;
import upp.project.model.Role;
import upp.project.model.ScientificArea;
import upp.project.services.MagazineService;
import upp.project.services.ScientificAreaService;
import upp.project.services.UserService;

@Service
public class MagazineFormListener implements TaskListener{ 
	
	@Autowired
	UserService userService;
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	ScientificAreaService scientificAreaService;
	
	public void notify(DelegateTask delegateTask) {
	
		TaskFormData formData = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
		List<FormField> formFields = formData.getFormFields();
				
		//get the magazine
		Long magazineId = (Long) delegateTask.getExecution().getVariable("magazineId");		
		Magazine magazine = magazineService.findById(magazineId);
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		RegisteredUser loggedInUser = userService.findByUsername(username);
				
		//get chosen scientific areas
		List<ScientificArea> scientificAreas =  magazine.getScientificAreas().stream().collect(Collectors.toList()); 

		if(magazine != null) {
			//find editors and reviewers for chosen scientific areas
			List<RegisteredUser> editorList = userService.findByScientificAreas(scientificAreas, Role.ROLE_EDITOR, loggedInUser);
			List<RegisteredUser> reviewerList = userService.findByScientificAreas(scientificAreas, Role.ROLE_REVIEWER, loggedInUser);
			
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
							map.put(editor.getUsername(), editor.getUsername());
						}
					}
				}
			}
		}
	}
}
