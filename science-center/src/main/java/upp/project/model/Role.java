package upp.project.model;

public enum Role {
	ROLE_ADMINISTRATOR("admins"),
	ROLE_REVIEWER("reviewers"),
	ROLE_EDITOR("editors"),
	ROLE_AUTHOR("authors")
	;
	
	private String camundaGroupName;

    private Role(String camundaGroupName) {
        this.camundaGroupName = camundaGroupName;
    }
    
    public String getCamundaGroupName() {
    	return this.camundaGroupName;
    }
}
