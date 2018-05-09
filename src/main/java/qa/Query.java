package qa;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Query {
	/** The original query performed by the user*/
	private String originalQuery;
	
	/** The query used to fetch the data from the dataset*/
	@JsonIgnore
	private String optimizedQuery;
	
	@JsonIgnore
	private String label;
	
	public Query(String query){
		this.originalQuery = query;
	}
	
	public Query(Query q){
		this.originalQuery = q.originalQuery;
		this.optimizedQuery = q.optimizedQuery;
	}
	
	public String getOriginalQuery(){
		return this.originalQuery;
	}
	
	public void setLabel(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return this.label;
	}
}
