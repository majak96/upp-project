package upp.project.model;

public enum PaperRecommendation {
	ACCEPT("Accept"),
	ACCEPT_WITH_MINOR_UPDATES("Accept with minor updates"),
	ACCEPT_WITH_MAJOR_UPDATES("Accept with major updates"),
	REJECT("Reject")
	;
	
	private String text;

    private PaperRecommendation(String text) {
        this.text = text;
    }
    
    public String getText() {
    	return this.text;
    }
}
