package upp.project.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormFieldValidationConstraint;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormFieldDTO;
import upp.project.dtos.FormValueDTO;
import upp.project.dtos.TaskDTO;

@Service
public class ProcessService {
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	/**
	 * Starts the process and returns the first task of the process
	 */
	public Task startProcess(String processId) {
		
		ProcessInstance process = runtimeService.startProcessInstanceByKey(processId);
		Task firstTask = taskService.createTaskQuery().processInstanceId(process.getId()).list().get(0);
		
		return firstTask;
	}
	
	/**
	 * Gets the next available task for the process
	 */
	public String getNextTaskId(String processInstanceId) {

		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		
		if(task != null) {
			return task.getId();
		}
		
		return null;
	}
	
	/**
	 * Returns the list of fields for the user task
	 */
	public List<FormFieldDTO> getFrontendFields(String taskId) { 
		//get form data for the task
		TaskFormData formData = formService.getTaskFormData(taskId);
				
		//get form fields
		List<FormField> formFields = formData.getFormFields();
		
		List<FormFieldDTO> frontendFields = new ArrayList<FormFieldDTO>();

		for(FormField field : formFields) {
			FormFieldDTO frontendField = new FormFieldDTO(field.getId(), field.getLabel(), field.getTypeName());
			
			//check if the field is required
			for(FormFieldValidationConstraint constraint : field.getValidationConstraints()) {
				if(constraint.getName().equals("required")) {
					frontendField.setRequired(true);
				}
			}
			
			//check field properties
			Map<String,String> fieldProperties = field.getProperties();
			if(fieldProperties.keySet().contains("multiple")) {
				frontendField.setMultiple(true);
			}
			else if(fieldProperties.keySet().contains("email")) {
				frontendField.setEmail(true);
			}
			else if(fieldProperties.keySet().contains("password")) {
				frontendField.setPassword(true);
			}
			
			if(fieldProperties.keySet().contains("min")) {
				frontendField.setMinNumber(Integer.parseInt(fieldProperties.get("min")));
			}
			
			if(fieldProperties.keySet().contains("readonly")) {
				frontendField.setReadonly(true);
			}
			
			frontendField.setValue(field.getValue().getValue());
			
			//set enumeration values
			if(field.getType().getName().equals("enum")) {
				Map<String, String> valuesMap = (LinkedHashMap<String, String>) field.getType().getInformation("values");
				
				for(String mapKey : valuesMap.keySet()) {
					frontendField.getValues().put(mapKey, valuesMap.get(mapKey));
				}
			}
			
			frontendFields.add(frontendField);
		}
		
		return frontendFields;
	}

	/**
	 * Submits form values and completes the task
	 */
	public boolean submitFormFields(String taskId, List<FormValueDTO> formValues) throws Exception {
				
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();	
		
		HashMap<String, Object> valuesMap = mapListToDto(formValues);
		
		try {
			formService.submitTaskForm(task.getId(), valuesMap);
		}
		catch(ProcessEngineException pe) {
			throw new Exception("Wrong field values.");
		}
		catch(Exception e) {
			throw new Exception("An error occured.");
		}
				
		return true;
	}
	
	/**
	 * Creates a new process variable
	 */
	public void setProcessVariable(String processInstanceId, String processVariableName, Object processVariableValue) {
			
		runtimeService.setVariable(processInstanceId, processVariableName, processVariableValue);
	}
	
	/**
	 * Gets all available tasks for the user with the username
	 */
	public List<TaskDTO> getTasksForUser(String username) {
		
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).list();
		
		List<TaskDTO> taskDTOs = new ArrayList<TaskDTO>();
		
		for(Task task : tasks) {
			TaskDTO taskDTO = new TaskDTO(task.getId(), task.getName());
			
			taskDTOs.add(taskDTO);
		}
		
		return taskDTOs;
 	}
	
	private HashMap<String, Object> mapListToDto(List<FormValueDTO> formValues) {
		HashMap<String, Object> valuesMap = new HashMap<String,Object>();
		
		for(FormValueDTO value : formValues) {
			valuesMap.put(value.getId(), value.getValue());
		}
		
		return valuesMap;
	}
	
	
}
