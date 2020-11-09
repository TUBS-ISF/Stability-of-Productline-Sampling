package de.tu_bs.cs.isf.pett.stabilitycalculator.core;

public enum Metrics {
	ROIC("roic"), 
	MSOC("msoc"),
	FIMDC("fimdc"),
	ICST("icst");
	
	private String metric = ""; 
	
	private Metrics(String metric) {
		this.metric = metric; 
	}
	
	public String getMetricName() {
		return this.metric;
	}
	

}
