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
public class SaveMagazineUsersService implements JavaDelegate{
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	ScientificAreaService scientificAreasService;
	
	@Autowired
	UserService userService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
						
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("newMagazineUsersFormValues");
		
	}
	
}