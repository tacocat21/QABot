package qa;

import org.codehaus.jackson.annotate.JsonIgnore;

import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Morphology;

import edu.stanford.nlp.ling.Word;
import java.io.StringReader;
import java.util.Iterator;


public class Query {
	private static Morphology stemmer = new Morphology();

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


	/**
	 * Changes the words to stems in the originalQuery
	 */
	public void stemQuery() {
		this.optimizedQuery = stemSentence(this.originalQuery);
	}

	/**
	 *  Returns the stem of every word from the input sentence
	 */
	public static String stemSentence(String sentence) {
		StringReader r = new StringReader(sentence);
		Iterator<Word> it = PTBTokenizer.newPTBTokenizer(r);
		String result = "";
		while (it.hasNext()) {
			Word token = it.next();
			result += stemmer.stem(token).word();
			if (it.hasNext()) {
				result += " ";
			}
		}
		System.err.println(result);
		return result;
	}

	public void setLabel(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return this.label;
	}
}
