package qa;

import org.codehaus.jackson.annotate.JsonIgnore;

import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Morphology;

import edu.stanford.nlp.ling.Word;
import java.io.StringReader;
import java.util.Iterator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;

import java.io.FileReader;
import java.io.IOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


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

	public String getOptimizedQuery(){
		return this.optimizedQuery;
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

	/**
	 * Iterates through the dataset.json and applies the stemSentence function to every question
	 * The result is saved in stemmedQuestion key
	 * @param filename: name of the file to process
	 * @param outputFilename: output filename
	 */
	public static void stemDatabaseQuestions(String filename, String outputFilename) {
		try {
			FileWriter fw = new FileWriter(outputFilename);
			JsonParser parser = new JsonParser();
			File file = new File(filename);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int idx = 0;
			while ((line = bufferedReader.readLine()) != null) {
				// only get the question documents not the index
				if(idx++%2==0){
					fw.write(line + "\n");
					continue;
				}
				JsonObject json = parser.parse(line).getAsJsonObject();
				String question = json.get("question").getAsString();
				String stemmedQuestion = stemSentence(question);
				json.addProperty("stemmedQuestion", stemmedQuestion);
				fw.write(json.toString() + "\n");
			}
			fw.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
