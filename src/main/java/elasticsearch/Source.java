package elasticsearch;

public class Source{
	String question;
	String answer;
	String label;
	String stemmedQuestion;
	
	public String getStemmedQuestion() {
		return stemmedQuestion;
	}
	public void setStemmedQuestion(String stemmedQuestion) {
		this.stemmedQuestion = stemmedQuestion;
	}
	public String getLabel() {
		return label;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getLablel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
}
