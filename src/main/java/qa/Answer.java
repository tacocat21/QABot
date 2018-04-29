package qa;

public class Answer {
	
	/** the final answer */
	private String answer = "";
	
	/** the original query performed by the user */
	private String userQuery = "";
	
	/** the most similar question inside the dataset to the original query*/
	private String matchedQuestion = "";
	
	public String getAnswer(){
		return this.answer;
	}
	
	public String getUserQuery(){
		return this.userQuery;
	}
	
	public String getMatchedQuestion(){
		return this.matchedQuestion;
	}
	
	public void setUserQuery(String userQuery){
		this.userQuery = userQuery;
	}
}
