package upp.project.services.camunda.paper;

import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Paper;
import upp.project.services.PaperService;

@Service
public class SettingDOIService implements JavaDelegate {
	
	@Autowired
	PaperService paperService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | setting the DOI");
		
		Long paperId = (Long) execution.getVariable("paperId");		
		Paper paper = paperService.findById(paperId);
		
		if(paper != null) {
			//generate and set the DOI
			paper.setDOI(createDOI());
			
			//save paper acceptance
			paper.setActive(true);
			
			//save the paper
			paperService.save(paper);
		}
	}
	
	public String createDOI() {
		
		String DOI = "";
		
		for(int i = 0; i < 2; i ++) {
			DOI += getRandNumber();
		}
		
		DOI += ".";
		
		for(int i = 0; i < 4; i ++) {
			DOI += getRandNumber();
		}
		
		DOI += "/";
		
		for(int i = 0; i < 3; i ++) {
			DOI += getRandNumber();
		}
		
		return DOI;		
	}
	
	public String getRandNumber() {		
		Random rand = new Random();
		int num = rand.nextInt(10) + 1;

		return Integer.toString(num);
	}
}
