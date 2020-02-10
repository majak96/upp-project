package upp.project.services.camunda.paper;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Paper;
import upp.project.model.RegisteredUser;
import upp.project.services.EmailService;
import upp.project.services.PaperService;

@Service
public class NotifyAuthorAboutAcceptedWithChangesService implements JavaDelegate {
	
	@Autowired
	PaperService paperService;
	
	@Autowired
    EmailService emailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | notifying the author about paper acceptance with corrections");
		
		Long paperId = (Long) execution.getVariable("paperId");		
		Paper paper = paperService.findById(paperId);
		
		//send and email to notify the author about paper acceptance with corrections
		if(paper != null) {			
			RegisteredUser author = paper.getAuthor();			
		
			sendMailToAuthor(author, paper);			
		}
	}
	
	private void sendMailToAuthor(RegisteredUser author, Paper paper) {
				
		String messageText = "<div>"
						   + "<p>"
						   + "Dear Mr. " + author.getFirstName() + " " + author.getLastName() + ", <br><br>"
						   + "We are pleased to inform you your paper submission for " + paper.getIssue().getMagazine().getName() + " has been accepted.<br>"
						   + "However, there are still some corrections you need to apply in order for the paper to be published. <br>"
						   + "Please log in to ScienceCenter and correct your paper submission. <br><br>"
						   + "Best regards, <br>"
						   + "ScienceCenter"
						   + "<p>"
						   + "</div>";
		
        emailService.sendEmail(author.getEmail(), "ScienceCenter Paper Acceptance With Corrections", messageText);		
	}
	
}
