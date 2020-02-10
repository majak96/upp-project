package upp.project.services.camunda.paper;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class PaymentOfMembershipService implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | postavljam procesnu varijablu");
		
		execution.setVariable("redirect_link", "https://localhost:4210/#/");
	}
}
