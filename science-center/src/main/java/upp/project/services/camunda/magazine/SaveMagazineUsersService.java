package upp.project.services.camunda.magazine;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;
import upp.project.model.Magazine;
import upp.project.model.RegisteredUser;
import upp.project.model.Role;
import upp.project.services.MagazineService;
import upp.project.services.ScientificAreaService;
import upp.project.services.UserService;

@Service
public class SaveMagazineUsersService implements JavaDelegate{
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	ScientificAreaService scientificAreasService;
	
	@Autowired
	UserService userService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("MAG | saving editors and reviewers for a magazine");
						
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("formData");
		
		HashMap<String, Object> valuesMap = new HashMap<String,Object>();
		
		for(FormValueDTO value : formValues) {
			valuesMap.put(value.getId(), value.getValue());
		}
		
		Long magazineId = (Long) execution.getVariable("magazineId");		
		Magazine magazine = magazineService.findById(magazineId);
		
		if(magazine != null) {
			List<String> reviewers = ((List<String>) valuesMap.get("form_reviewers"));
			List<String> editors = ((List<String>) valuesMap.get("form_editors"));
			
			//add reviewers
			for(String reviewer : reviewers) {
				RegisteredUser reviewerUser = userService.findByUsername(reviewer);
				
				if(reviewerUser != null && reviewerUser.getAuthority().getRole() == Role.ROLE_REVIEWER) {
					magazine.getReviewers().add(reviewerUser);
				}
			}
			
			//add editors
			for(String editor : editors) {
				RegisteredUser editorUser = userService.findByUsername(editor);
				
				if(editorUser != null && editorUser.getAuthority().getRole() == Role.ROLE_EDITOR) {
					magazine.getEditors().add(editorUser);
				}
			}			
			
			magazineService.save(magazine);		
		}		
	}	
}