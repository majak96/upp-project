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
public class NotifyAuthorAboutRequiredCorrectionService implements JavaDelegate {

	@Autowired
	PaperService paperService;
	
	@Autowired
    EmailService emailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | notifying the author about required correction");
					
		Long paperId = (Long) execution.getVariable("paperId");						
		Paper paper = paperService.findById(paperId);
		
		//send email to the author
		if(paper != null) {			
			RegisteredUser author = paper.getAuthor();					
			
			sendMailToAuthor(author);			
		}
	}
	
	private void sendMailToAuthor(RegisteredUser author) {
		
		String messageText = "<div>"
						   + "<p>"
						   + "Dear " + author.getFirstName() + " " + author.getLastName() + ", <br><br>"
						   + "Your paper been reviewed. Unfortunately, the formatting of your paper is not acceptable. "
						   + "Please log in to ScienceCenter and correct your paper submission. <br><br>"
						   + "Best regards, <br>"
						   + "ScienceCenter"
						   + "<p>"
						   + "</div>";
		
        emailService.sendEmail(author.getEmail(), "ScienceCenter Paper Correction", messageText);		
	}
}
