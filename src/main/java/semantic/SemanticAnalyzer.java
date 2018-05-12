package semantic;

import java.util.ArrayList;

import javax.ws.rs.core.GenericEntity;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import elasticsearch.Hit;
import qa.Query;

public class SemanticAnalyzer {
	private Client client;
	private String hostNamePort;
	
	public SemanticAnalyzer(String hostNamePort){
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		this.client = Client.create(clientConfig);
		this.hostNamePort = hostNamePort;
	}
	
	/**
	 * Given a Query return the question type
	*/
	public String getLabel(Query query){
		String query_encoded = query.getOriginalQuery().replace(" ", "%20");

		/** Create a connection with elastic search and run the query.*/
		WebResource webResource = client.resource(this.hostNamePort + "label/" + query_encoded);
		ClientResponse response = webResource.type("application/json")
				.accept("application/json")
				.get(ClientResponse.class);
		if(response.getStatus() == 200){
			QuestionType  result = response.getEntity(QuestionType.class);
			return result.getLabel();
		}
		else{
			System.err.println("Wrong result from the classifier: status " + response.getStatus());
		}
		return null;
	}

	/**
	 * Given a Query return the semantic vector rapresaentation
	*/
	public Vector getVector(String question){
		String query_encoded = question.replace(" ", "%20");

		/** Create a connection with elastic search and run the query.*/
		WebResource webResource = client.resource(this.hostNamePort + "vector/" + query_encoded);
		ClientResponse response = webResource.type("application/json")
				.accept("application/json")
				.get(ClientResponse.class);
		if(response.getStatus() == 200){
			Vector result = response.getEntity(Vector.class);
			return result;
		}
		else{
			System.err.println("Wrong result from the classifier: status " + response.getStatus());
		}
		return null;
	}
	
	public Vector getVector(Query query){
		return this.getVector(query.getOriginalQuery());
	}
	
	public Vector getVector(Hit hit){
		return this.getVector(hit.get_source().getQuestion());
	}
    public double getSemanticScore(Query query, Hit hit){
        String query_encoded = query.getOriginalQuery().replace(" ", "%20");
        String hit_encoded   = hit.get_source().getQuestion().replace(" ", "%20");
        /** Create a connection with server classifier.*/ //?a=hello&b=world
        WebResource webResource = client.resource(this.hostNamePort + "semantic/?query=" + query_encoded + "&hit=" + hit_encoded);
        ClientResponse response = webResource.type("application/json")
        .accept("application/json")
        .get(ClientResponse.class);
        if(response.getStatus() == 200){
            SemanticScore result = response.getEntity(SemanticScore.class);
            return result.getScore();
        }
        else{
            System.err.println("Wrong result from the classifier: status " + response.getStatus());
        }
        return 0.0;
    }

}
