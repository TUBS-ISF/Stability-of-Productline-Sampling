package de.tu_bs.cs.isf.pett.headlessincling.core;

import java.util.List;

import de.ovgu.featureide.fm.core.analysis.cnf.LiteralSet;
import de.ovgu.featureide.fm.core.job.monitor.ConsoleMonitor;
import de.ovgu.featureide.fm.core.job.monitor.IMonitor;

public class HeadlessIncLing {

	private final int maxSolutionCount = Integer.MAX_VALUE; 
	
	private static final String baseFMPath = "model.xml"; 
	private static final String baseSamplePath = "./Testdata/IncLing/";
	private static final String basePath = "./Testdata/"; 
	
	public static void main(String[] args) {
		if(args.length == 3) {
			new HeadlessIncLing(args[0], args[1], args[2]); 
		}
		else {
			System.out.println("No suitable arguments provided for run. Provide the path to the base directory as single string");
			new HeadlessIncLing(basePath, baseFMPath, baseSamplePath); 
		}
	}
	
	public HeadlessIncLing(String basePath, String baseFMPath, String baseSamplePath) {
		IMonitor<List<LiteralSet>> monitor = new ConsoleMonitor<List<LiteralSet>>();
		PreservativeHandler handler = new PreservativeHandler(basePath, baseFMPath, baseSamplePath, maxSolutionCount, monitor); 
		handler.execut();

	}
}
