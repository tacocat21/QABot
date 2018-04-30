package qa;

import elasticsearch.Hit;

public class Answer {
	
	/** the final answer */
	private String answer = "";
	
	/** the original query performed by the user */
	private String userQuery = "";
	
	/** the most similar question inside the dataset to the original query*/
	private String matchedQuestion = "";
	
	/** the list of hits returned by elastic reach*/
	private Hit[] hits;
	
	public String getAnswer(){
		return this.answer;
	}
	
	public String getUserQuery(){
		return this.userQuery;
	}
	
	public String getMatchedQuestion(){
		return this.matchedQuestion;
	}
	
	public Hit[] getHits(){
		return this.hits;
	}
	
	public void setUserQuery(String userQuery){
		this.userQuery = userQuery;
	}
	
	public void setHits(Hit[] hits){
		this.hits = hits.clone();
	}
}
