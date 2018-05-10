package elasticsearch;

import qa.Query;

import java.util.ArrayList;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class ElasticSearch {
	
	private Client client;
	private String hostNamePort;
	
	public ElasticSearch(String hostNamePort){
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		this.client = Client.create(clientConfig);
		this.hostNamePort = hostNamePort;
	}
	
	/**
	 Create a connections to elastic search and run the query. 
	 */
	public ArrayList<Hit> runQuery(String indexName, Query query){
		/** Prepare a query content*/
		String data = this.matchQueryBuilder(query);
		
		/** Create a connection with elastic search and run the query.*/
		WebResource webResource = client.resource(this.hostNamePort + indexName + "/_search?&pretty");
		ClientResponse response = webResource.type("application/json")
											 .accept("application/json")
				   							 .post(ClientResponse.class, data);
		
		/** If elastic search is reachable returns the result*/
		if(response.getStatus() == 200){
			ESResult result = response.getEntity(ESResult.class);
			return result.getHits().getHits();
		}
		else{
			System.err.println("Wrong result from elastic search: status " + response.getStatus());
		}
		return null;
	}
	
	/**
	 * Generete a match query 
	 */
	private String matchQueryBuilder(Query query){
		String s = "{" + 
				   "\"query\": {\"match\": { \"question\": \"" + query.getOriginalQuery() +"\" } }" + 
				   "}";
		return s;
	}
}
