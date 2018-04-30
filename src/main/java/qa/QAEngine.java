package qa;

import elasticsearch.ElasticSearch;
import elasticsearch.Hit;

public class QAEngine {
	/** Elastic Search interface */
	public final static ElasticSearch elasticSearch = new ElasticSearch("http://localhost:9200/");
	public final static String INDEX_NAME = "qas";
	
	/**
	 * Pre process a query before executing it.
	 */
	public static Query preProcessQuery(Query originalQuery){
		Query optimizedQuery = new Query(originalQuery);
		//modify here optimized query
		return optimizedQuery;
	}
	
	/**
	 * Run a query: find the matching documents using elastic search and then filter 
	 * them to find the best one.
	 */
	public static Answer executeQuery(Query query) {
		// Send the query to elasticsearch
		Hit[] hits = elasticSearch.runQuery(INDEX_NAME, query);
		
		// Postprocess the result selecting the best answer
		Answer answer = postProcessHits(query, hits);
		return answer;
	}
	
	/**
	 * Given a list of hits (document retrieved from elastic search)
	 * returns the best answer.
	 */
	private static Answer postProcessHits(Query query, Hit[] hits){
		Answer answer = new Answer();
		
		answer.setUserQuery(query.getOriginalQuery());
		answer.setHits(hits);
		return answer;
	}
}
