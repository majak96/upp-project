package upp.project.services.camunda.paper;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Paper;
import upp.project.model.RegisteredUser;
import upp.project.services.PaperService;
import upp.project.services.UserService;

@Service
public class CountMagazineReviewersService implements JavaDelegate {
	
	@Autowired
	PaperService paperService;
	
	@Autowired
	UserService userService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | checking if reviewers for scientific area exist");
		
		Long paperId = (Long) execution.getVariable("paperId");		
		Paper paper = paperService.findById(paperId);
		
		if(paper != null) {
			//find magazine reviewers for the chosen scientific areas
			List<RegisteredUser> reviewers = userService.findMagazineReviewersForScientificArea(paper.getIssue().getMagazine(), paper.getScientificArea()); 
			
			//there must be at least two reviewers
			if(reviewers.size() >= 2) {
				execution.setVariable("rev_exist", true);
			}
			else {
				execution.setVariable("rev_exist", false);
			}
		}
		
	}
}
