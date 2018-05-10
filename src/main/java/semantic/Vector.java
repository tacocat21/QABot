package semantic;

import java.util.ArrayList;

public class Vector {
	
	ArrayList<Float> vector;

	public ArrayList<Float> getVector() {
		return vector;
	}

	public void setVector(ArrayList<Float> vector) {
		this.vector = vector;
	}
	
	/**
	 * Computes the cosine similarity between this vector and another vector
	 */
	public double cosineSimilarity(Vector other){
		if(this.vector.size() != other.vector.size()){
			return 0;
		}
		
		double cosine = 0;
		double norm1 = 0;
		double norm2 = 0;
		for(int i = 0; i < this.vector.size(); i++){
			cosine += this.vector.get(i) * other.vector.get(i);
			norm1 += this.vector.get(i) * this.vector.get(i);
			norm2 += other.vector.get(i) * other.vector.get(i);
		}
		
		norm1 = Math.sqrt(norm1);
		norm2 = Math.sqrt(norm2);
		
		cosine = cosine / (norm1 * norm2);
		return cosine;
	}
	
}
