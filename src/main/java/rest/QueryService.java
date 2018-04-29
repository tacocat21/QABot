package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import qa.Answer;
import qa.QAEngine;
import qa.Query;

@Path("/query")
public class QueryService {
	
	@GET
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Answer getQuery(@PathParam("param") String queryTxt) {
		Query query = new Query(queryTxt);
		query = QAEngine.preProcessQuery(query);
		Answer answer = QAEngine.executeQuery(query);
		return answer;
	}
	
}
