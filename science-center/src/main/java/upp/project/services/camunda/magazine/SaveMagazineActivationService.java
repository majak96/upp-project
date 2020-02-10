package upp.project.services.camunda.magazine;

import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Issue;
import upp.project.model.Magazine;
import upp.project.services.IssueService;
import upp.project.services.MagazineService;

@Service
public class SaveMagazineActivationService implements JavaDelegate{
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	IssueService issueService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("MAG | activating the magazine");
		
		Long magazineId = (Long) execution.getVariable("magazineId");
		
		Magazine magazine = magazineService.findById(magazineId);
		
		if(magazine != null) {
			//activate the magazine
			magazine.setActive(true);
			magazineService.save(magazine);
			
			//create the first issue after activation
			Date date = new Date();
			DateTime originalDateTime = new DateTime(date);
			DateTime publishingDateTime = originalDateTime.plusMonths(1);
			
			Issue issue = new Issue(magazine, 1, publishingDateTime.toDate());
			Issue savedIssue = issueService.save(issue);			
		}
	}
}