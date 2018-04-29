package elasticsearch;

class Hits{
	int total;
	double max_score;
	Hit[] hits;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public double getMax_score() {
		return max_score;
	}
	public void setMax_score(double max_score) {
		this.max_score = max_score;
	}
	public Hit[] getHits() {
		return hits;
	}
	public void setHits(Hit[] hits) {
		this.hits = hits;
	}
}
