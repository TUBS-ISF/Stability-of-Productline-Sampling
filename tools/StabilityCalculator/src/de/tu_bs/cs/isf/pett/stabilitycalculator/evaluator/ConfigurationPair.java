package de.tu_bs.cs.isf.pett.stabilitycalculator.evaluator;

public class ConfigurationPair {
	
	private int key_s1 = 0; 
	private int key_s2 = 0; 
	private double similarity = 0.0; 
	
	public ConfigurationPair(int key_s1, int key_s2, double similarity) {
		this.key_s1 = key_s1; 
		this.key_s2 = key_s2; 
		this.similarity = similarity;  
	}
	
	public ConfigurationPair() {
	}
	
	public int getKey1() {
		return key_s1; 
	}
	public int getKey2() {
		return key_s2; 
	}
	
	public double getSimilarity() {
		return similarity; 
	}

}
