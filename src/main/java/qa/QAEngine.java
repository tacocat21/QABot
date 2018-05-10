package qa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import elasticsearch.ElasticSearch;
import elasticsearch.Hit;
import semantic.SemanticAnalyzer;
import semantic.Vector;


public class QAEngine {
	/** Elastic Search interface */
	public final static ElasticSearch elasticSearch = new ElasticSearch("http://localhost:9200/");
	public final static SemanticAnalyzer semantic = new SemanticAnalyzer("http://localhost:5000/");
	public final static String INDEX_NAME = "qas";

	/**
	 * Pre process a query before executing it.
	 */
	public static Query preProcessQuery(Query originalQuery){
		Query optimizedQuery = new Query(originalQuery);
		//modify here optimized query
		optimizedQuery.stemQuery();

		//Generate a the question type for the query
		String label = semantic.getLabel(optimizedQuery);
		// Should figure out if it returns null
		if(label == null) {
			return null;
		}
		optimizedQuery.setLabel(label);
		return optimizedQuery;
	}

	/**
	 * Run a query: find the matching documents using elastic search and then filter 
	 * them to find the best one.
	 */
	public static Answer executeQuery(Query query) {
		// Send the query to elasticsearch
		ArrayList<Hit> hits = elasticSearch.runQuery(INDEX_NAME, query);
		// Should figure out if hits returns null
		if(hits == null) {
			return null;
		}
		// Postprocess the result selecting the best answer
		Answer answer = postProcessHits(query, hits);
		return answer;
	}
	
	/**
	 * Given a list of hits (document retrieved from elastic search)
	 * returns the best answer.
	 */
	private static Answer postProcessHits(Query query, ArrayList<Hit> hits){
		Answer answer = new Answer();
		
		//compute the semantic similarity 
		Vector vector = semantic.getVector(query);
		for(Hit hit : hits){
			Vector hitVector = semantic.getVector(hit);
			double score = vector.cosineSimilarity(hitVector);
			hit.set_semantic_score(score);
		}
		Collections.sort(hits);
		answer.setUserQuery(query.getOriginalQuery());
		answer.setHits(hits);
		return answer;
	}
}
