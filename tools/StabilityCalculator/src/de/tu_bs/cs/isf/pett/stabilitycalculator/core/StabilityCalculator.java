package de.tu_bs.cs.isf.pett.stabilitycalculator.core;

public class StabilityCalculator {

	private static final String metric = "ICST"; 
	private static final String pathFM_old = "./Example/busybox/2010-05-04_15-45-25.xml";
	private static final String pathFM_new = "./Example/busybox/2010-05-05_00-40-15.xml";
	private static final String pathSample_old = "./Example/busybox/Samples/Chvatal/2010-05-04_15-45-25";
	private static final String pathSample_new = "./Example/busybox/Samples/Chvatal/2010-05-05_00-40-15";
	private static final String name1 = "Version1"; 
	private static final String name2 = "Version2";
	
	private static final String csvPath = "./Example/Stability_Results/";
	private static final String csvFileName = "chvatal/gpl_example.csv"; 
	
	public static void main(String[] args) {
		if(args.length == 9) {
			new StabilityCalculator(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8]); 
		}
		else {
			System.out.println("No suitable arguments provided for run. Provide the path to the base directory as single string");
			System.out.println("Order to privide arguments: csvPath, Model1, sample1, model2, sample2");
			new StabilityCalculator(metric, csvPath, csvFileName, pathFM_old, pathSample_old, pathFM_new, pathSample_new, name1, name2); 
		}
	}
	
	public StabilityCalculator(String metric, String csvPath, String csvFileName, String pathFM1, String pathSample1, String pathFM2, String pathSample2, String name1, String name2) {
		EvaluatorCore handler = new EvaluatorCore(metric, csvPath, csvFileName, pathFM1, pathSample1, pathFM2, pathSample2, name1, name2); 
		handler.execut();
	}

}
