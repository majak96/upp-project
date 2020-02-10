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
public class NotifyAuthorAboutAcceptedPaperService implements JavaDelegate {
	
	@Autowired
	PaperService paperService;
	
	@Autowired
    EmailService emailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | notifying the author about paper acceptance");
		
		Long paperId = (Long) execution.getVariable("paperId");		
		Paper paper = paperService.findById(paperId);
		
		//send an email to notify the author about paper acceptance
		if(paper != null) {			
			RegisteredUser author = paper.getAuthor();			
		
			sendMailToAuthor(author, paper);			
		}
	}
	
	private void sendMailToAuthor(RegisteredUser author, Paper paper) {
				
		String messageText = "<div>"
						   + "<p>"
						   + "Dear " + author.getFirstName() + " " + author.getLastName() + ", <br><br>"
						   + "We are pleased to inform you your paper submission for " + paper.getIssue().getMagazine().getName() + " has been accepted.<br><br>"
						   + "Best regards, <br>"
						   + "ScienceCenter"
						   + "<p>"
						   + "</div>";
		
        emailService.sendEmail(author.getEmail(), "ScienceCenter Paper Acceptance", messageText);		
	}
	
}
