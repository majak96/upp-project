package upp.project.services.camunda.paper;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;
import upp.project.model.Paper;
import upp.project.services.PaperService;

@Service
public class SaveCorrectedPaperService implements JavaDelegate {
	
	@Autowired
	PaperService paperService;
		
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | save the corrected paper.");
		
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("formData");
						
		Long paperId = (Long) execution.getVariable("paperId");			
		Paper paper = paperService.findById(paperId);
		
		//update the paper with corrected values
		for(FormValueDTO value : formValues) {
			if(value.getId().equals("form_pdf")) {
				paper.setPdf((String) value.getValue());
			}
			else if (value.getId().equals("form_title")) {
				paper.setTitle((String) value.getValue());
			}
			else if(value.getId().equals("form_keywords")) {
				paper.setKeywords((String) value.getValue());
			}
			else if(value.getId().equals("form_abstract")) {
				paper.setPaperAbstract((String) value.getValue());
			}
		}
		
		//save the corrected paper
		paperService.save(paper);

	}

}
