package upp.project.services.camunda.paper;

import java.util.List;
import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Paper;
import upp.project.model.RegisteredUser;
import upp.project.model.ScientificArea;
import upp.project.services.EmailService;
import upp.project.services.PaperService;
import upp.project.services.UserService;

@Service
public class NotifyScientificAreaEditorService implements JavaDelegate {
	
	@Autowired
	PaperService paperService;
	
	@Autowired
	UserService userService;
	
	@Autowired
    EmailService emailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | notifying the scientific area editor about paper submission.");
		
		Long paperId = (Long) execution.getVariable("paperId");		
		Paper paper = paperService.findById(paperId);
		
		if(paper != null) {			
			ScientificArea paperScientificArea = paper.getScientificArea();
			
			//find magazine editors for the chosen scientific areas
			List<RegisteredUser> scientificAreaEditors = userService.findMagazineEditorsForScientificArea(paper.getIssue().getMagazine(), paperScientificArea);
			
			RegisteredUser chosenEditor;
			
			//if scientific area editor exists
			if(scientificAreaEditors.size() != 0) {
				//choose a random scientific area editor
				chosenEditor = scientificAreaEditors.get(new Random().nextInt(scientificAreaEditors.size()));
				
				//send an email to notify the chosen scientific area editor
				sendMailToEditor(chosenEditor, paper);
			}
			//if not choose the editor in chief
			else {
				chosenEditor = paper.getIssue().getMagazine().getEditorInChief();
			}
			
			//save chosen editor's username as a process variable
			execution.setVariable("chosen_editor", chosenEditor.getUsername());
		}
		
	}
	
	private void sendMailToEditor(RegisteredUser editor, Paper paper) {
		
		String messageText = "<div>"
				   + "<p>"
				   + "Dear " + editor.getFirstName() + " " + editor.getLastName() + ", <br><br>"
				   + "A paper titled \"" + paper.getTitle() + "\" was submitted to " + paper.getIssue().getMagazine().getName() + ". "
				   + "Please log in to ScienceCenter to choose reviewers for the paper. <br><br>"
				   + "Best regards, <br>"
				   + "ScienceCenter"
				   + "<p>"
				   + "</div>";

		emailService.sendEmail(editor.getEmail(), paper.getIssue().getMagazine().getName() + " Paper Submission", messageText);		
	}

}
