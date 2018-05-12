package qa;

import java.util.ArrayList;

import elasticsearch.Hit;

public class Answer {
	
	/** the final answer */
	private String answer = "";
	
	/** the original query performed by the user */
	private String userQuery = "";
	
	/** the most similar question inside the dataset to the original query*/
	private String matchedQuestion = "";
	
	/** the list of hits returned by elastic reach*/
	private ArrayList<Hit> hits;
	
	public String getAnswer(){
		return this.answer;
	}
	
	public String getUserQuery(){
		return this.userQuery;
	}
	
	public String getMatchedQuestion(){
		return this.matchedQuestion;
	}
	
	public ArrayList<Hit> getHits(){
		return this.hits;
	}
	
	public void setUserQuery(String userQuery){
		this.userQuery = userQuery;
	}
	
	public void setHits(ArrayList<Hit> hits){
		this.hits = hits;
	}
    
    public void setAnswer(String answer){
        this.answer = answer;
    }
    
    public void setMatchedQuestion(String matchedQuestion){
        this.matchedQuestion = matchedQuestion;
    }
    
}
