package upp.project.handlers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.stereotype.Service;

import upp.project.model.PaperRecommendation;

@Service
public class PaperRecommendationsListener implements TaskListener {
		
	public void notify(DelegateTask delegateTask) {
		
		TaskFormData formData = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
		List<FormField> formFields = formData.getFormFields();
		
		if(formFields != null) {				
			for(FormField field : formFields) {			
				if(field.getId().equals("form_rev_enum") || field.getId().equals("form_repl_enum")) {
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
