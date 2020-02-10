package upp.project.handlers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Paper;
import upp.project.model.PaperRecommendation;
import upp.project.model.PaperReview;
import upp.project.services.PaperReviewService;
import upp.project.services.PaperService;

@Service
public class PaperReviewsListener implements TaskListener{
	
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
				textReview += rev.getReviewer().getUsername() + ": \n";
				textReview += "Comment: " +  rev.getCommentforEditor() + "\n";
				textReview += "Recommendation: " + rev.getRecommendation().getText() + "\n";
			}
			
			System.out.println(textReview);
			
			//save string as a process variable
			delegateTask.getExecution().setVariable("form_rev_u_comments", textReview);
		}
		
		//fill enum with possible values for the recommendation
		TaskFormData formData = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
		List<FormField> formFields = formData.getFormFields();
		
		if(formFields != null) {				
			for(FormField field : formFields) {			
				if(field.getId().equals("final_recommendation")) {
					Map<String, String> valuesMap = (LinkedHashMap<String, String>) field.getType().getInformation("values");

					PaperRecommendation recommendations[] = PaperRecommendation.values();
				      
				    // fill enum field with values for recommendation
			        for(PaperRecommendation rec: recommendations) {
						valuesMap.put(rec.toString(), rec.getText());
			        }
				}
			}
		}
	
	}
}
