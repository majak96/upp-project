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
public class NotifyAuthorAndEditorInChiefService  implements JavaDelegate{
	
	@Autowired
	PaperService paperService;
	
	@Autowired
    EmailService emailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | notifying the author and the editor in chief about paper submission");
		
		Long paperId = (Long) execution.getVariable("paperId");		
		Paper paper = paperService.findById(paperId);
				
		if(paper != null) {		
			//find the author and the editor in chief
			RegisteredUser author = paper.getAuthor();			
			RegisteredUser editorInChief = paper.getIssue().getMagazine().getEditorInChief();
		
			//send mail to the author and the editor in chief
			sendMailToAuthor(author);			
			sendMailToEditorInChief(editorInChief, paper);
		}
	}
	
	private void sendMailToAuthor(RegisteredUser author) {
				
		String messageText = "<div>"
						   + "<p>"
						   + "Dear " + author.getFirstName() + " " + author.getLastName() + ", <br><br>"
						   + "Your paper been received and is currently being reviewed. We will get in touch with you via e-mail "
						   + "as soon as the review process has been completed. <br><br>"
						   + "Best regards, <br>"
						   + "ScienceCenter"
						   + "<p>"
						   + "</div>";
		
        emailService.sendEmail(author.getEmail(), "ScienceCenter Paper Submission", messageText);		
	}
	
	private void sendMailToEditorInChief(RegisteredUser editorInChief, Paper paper) {
		
		String messageText = "<div>"
				   + "<p>"
				   + "Dear " + editorInChief.getFirstName() + " " + editorInChief.getLastName() + ", <br><br>"
				   + "A paper titled \"" + paper.getTitle() + "\" was submitted to " + paper.getIssue().getMagazine().getName() + ". "
				   + "Please log in to ScienceCenter to review the submitted paper. <br><br>"
				   + "Best regards, <br>"
				   + "ScienceCenter"
				   + "<p>"
				   + "</div>";

		emailService.sendEmail(editorInChief.getEmail(), "ScienceCenter Paper Submission", messageText);		
	}
}
