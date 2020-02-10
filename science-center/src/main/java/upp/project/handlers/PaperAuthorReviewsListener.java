package upp.project.handlers;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Paper;
import upp.project.model.PaperReview;
import upp.project.services.PaperReviewService;
import upp.project.services.PaperService;

@Service
public class PaperAuthorReviewsListener implements TaskListener{
	
	@Autowired
	PaperService paperService;

	@Autowired
	PaperReviewService paperReviewService;
	
	public void notify(DelegateTask delegateTask) {
				
		Long paperId = (Long) delegateTask.getExecution().getVariable("paperId");		
		Paper paper = paperService.findById(paperId);
		
		if(paper != null) {
			
			List<PaperReview> reviews = paperReviewService.findAllByPaper(paper);

			//create string for reviewer comments
			String textReview = "";

			for(PaperReview rev : reviews) {			
				textReview += "Anonymous reviewer: \n";
				textReview += "Comment: " +  rev.getCommentforAuthor() + "\n";
			}
			
			//save string as a process variable
			delegateTask.getExecution().setVariable("form_rev_a_comments", textReview);
		}
	
	}
}
