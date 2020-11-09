package de.tu_bs.cs.isf.pett.headlessincling.core;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import de.ovgu.featureide.fm.core.analysis.cnf.LiteralSet;
import de.ovgu.featureide.fm.core.job.monitor.IMonitor;

public class PreservativeHandler {

	private List<List<String>> confList = new ArrayList<>();
	private int maxSolutionCount = Integer.MAX_VALUE; 
	private int modelCount = 0;
	
	private String basePath = "./Testdata"; 
	
	private Thread analysisThread = null; 
	private Thread readerThread = null;
	
	private PreservativeInclingGenerator gen = null; 
	private SampleWriter sw = null; 
	
	private String currentFolderPath = ""; 
	
	private long timeOutBorder = 8 * 60 * 60 * 1000;
	
	IMonitor<List<LiteralSet>> monitor; 
	
	private String modelString = ""; 
	
//	private List<String> fms = new ArrayList<>(); 
	
	public PreservativeHandler(String basePath, String modelString, String baseSamplePath, int maxSolutionCount, IMonitor<List<LiteralSet>> monitor) {
		this.basePath = basePath; 
		this.monitor = monitor; 
		this.modelString = modelString; 
//		fms = getFeatureModels(baseFMPath);
		
		if(maxSolutionCount > 0) {
			this.maxSolutionCount = maxSolutionCount;
		}
		sw = new SampleWriter(baseSamplePath); 
	}
	
//	private List<String> getFeatureModels(String path){
//		List<String> fms = new ArrayList<>(); 
//		try {
//			fms = Files.readAllLines(Paths.get(path));
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
//		return fms; 
//	}
	
	public void execut() {
		try {
		sw.writeHeader();
//		long startPreAna = 0; 
//		long durationPreAna = 0; 
//		for(String s : fms) {
//			if(!s.startsWith("#")) {
		
				String fmPath = basePath + modelString; 
				gen = new PreservativeInclingGenerator(monitor, this); 
				System.out.println(fmPath);
//				String modelName = Paths.get(modelString).toFile().getName();
				currentFolderPath = sw.writeFolder(modelString);
				
				MyUtils.setFeatueModel(Paths.get(fmPath));
//				startPreAna = System.currentTimeMillis(); 
				List<List<String>> interConf = new ArrayList<>();
				gen.constructGenerator(maxSolutionCount, interConf);
				confList.clear();
//				confList.addAll(interConf);
//				durationPreAna = System.currentTimeMillis() - startPreAna;
//				long time = System.currentTimeMillis();
				constructThreads();
				manageThreads();
//				long newConfs = (confList.size() - interConf.size()); 
//				sw.writeStatistics(modelName, System.currentTimeMillis() - time, confList.size(), newConfs, durationPreAna);
//			}
//		}
//		sw.writeToFile("statistics");
		}
		catch(RuntimeException rEx) {
			rEx.printStackTrace();
			System.exit(1);
		}
	}
	
	private void manageThreads() {
		analysisThread.start();
		readerThread.start();
		
		long runTime = System.currentTimeMillis(); 
		
		while(true) {
			
			if((System.currentTimeMillis() - runTime) > timeOutBorder && !gen.isAnalysisFinished()) {
				System.out.println("!!!!! timeout !!!!!");
				analysisThread.interrupt();
				gen.setAnalysisIsFinished(); 
			}
			if(gen.isReadcomplete()) {
				break; 
			}
			System.out.println("Duration: " + (System.currentTimeMillis() - runTime));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println("Error while managing Threads");
				e.printStackTrace();
			}
		}
		try {
			readerThread.join();
		} catch (InterruptedException e) {
			System.out.println("Exception while joining the Reader Thread");
			e.printStackTrace();
		}
	}
	
	private void constructThreads() {
		analysisThread = gen.getAnalyzeThread(); 
		readerThread = gen.getReaderThread(); 
	}
	
	public void writeConf(List<String> conf, long confNumber) {
		sw.writeConfiguration(currentFolderPath, conf, (int) confNumber);
	}

	/**
	 * @return the confList
	 */
	public List<List<String>> getConfList() {
		return confList;
	}

	/**
	 * @return the maxSolutionCount
	 */
	public int getMaxSolutionCount() {
		return maxSolutionCount;
	}

	/**
	 * @return the modelcount
	 */
	public int getModelcount() {
		return modelCount;
	}
	
	public void addToConfList(List<String> conf) {
		confList.add(conf);
	}

}
