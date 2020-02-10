package upp.project.services.camunda.paper;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;
import upp.project.model.Paper;
import upp.project.model.PaperRecommendation;
import upp.project.model.PaperReview;
import upp.project.model.RegisteredUser;
import upp.project.services.PaperReviewService;
import upp.project.services.PaperService;
import upp.project.services.UserService;

@Service
public class SavePaperReviewService implements JavaDelegate{
		
	@Autowired
	PaperService paperService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PaperReviewService paperReviewService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | save paper review");
						
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("formData");
		
		HashMap<String, Object> valuesMap = new HashMap<String,Object>();
		
		for(FormValueDTO value : formValues) {
			valuesMap.put(value.getId(), value.getValue());
		}
		
		Long paperId = (Long) execution.getVariable("paperId");			
		Paper paper = paperService.findById(paperId);
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		RegisteredUser reviewer = userService.findByUsername(username);
				
		if(paper != null && reviewer != null) {
			String authorComment = ((String) valuesMap.get("form_rev_a_comment"));
			String editorComment = ((String) valuesMap.get("form_rev_u_comment"));
			
			String rec = valuesMap.get("form_rev_enum") != null ? (String) valuesMap.get("form_rev_enum") : (String) valuesMap.get("form_repl_enum");
			PaperRecommendation recommendation = PaperRecommendation.valueOf(rec);
			
			//save the review
			PaperReview rev = new PaperReview(paper, reviewer, authorComment, editorComment, recommendation);
			paperReviewService.save(rev);
			
			
			Object var = execution.getVariable("form_rev_u_comments");
			if(var != null) {
				String textReview = (String) var;
				
				textReview += rev.getReviewer().getUsername() + ": \n";
				textReview += "Comment: " +  rev.getCommentforEditor() + "\n";
				textReview += "Recommendation: " + rev.getRecommendation().getText() + "\n";
				
				execution.setVariable("form_rev_u_comments", textReview);
			}
			
			
			//clear the fields
			execution.setVariable("form_rev_a_comment", "");
			execution.setVariable("form_rev_u_comment", "");
		}		
	}	
}
