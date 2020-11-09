package de.tu_bs.cs.isf.pett.headlessincling.core;

import java.util.List;

import de.ovgu.featureide.fm.core.analysis.cnf.CNF;
import de.ovgu.featureide.fm.core.analysis.cnf.CNFCreator;
import de.ovgu.featureide.fm.core.analysis.cnf.LiteralSet;
import de.ovgu.featureide.fm.core.analysis.cnf.generator.configuration.PairWiseConfigurationGenerator;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.job.monitor.IMonitor;

public class PreservativeInclingGenerator{
	
	private IMonitor<List<LiteralSet>> monitor = null; 
	private PreservativeHandler handler = null; 
	
	private PairWiseConfigurationGenerator pairGen = null; 
//	private SatSolver satSolver = SatSolverFactory.
//	private SatInstance satInstance = null;
	
	private boolean isAnalysisFinished = false; 
	/**
	 * @return the isAnalysisFinished
	 */
	public boolean isAnalysisFinished() {
		return isAnalysisFinished;
	}

	private boolean isReadcomplete = false;
	
	private IFeatureModel featureModel; 
	
	CNFCreator cnfCreator; 
	CNF cnf;
	
	/**
	 * @return the isReadcomplete
	 */
	public boolean isReadcomplete() {
		return isReadcomplete;
	}

	public PreservativeInclingGenerator(IMonitor<List<LiteralSet>> monitor, PreservativeHandler handler) {
		this.monitor = monitor; 
		this.handler = handler; 
	}
	
		public void constructGenerator(int maxSolutionCount, List<List<String>> confs) {
		
//		final AdvancedNodeCreator advancedNodeCreator = new AdvancedNodeCreator(MyUtils.getFeatureModel(), new AbstractFeatureFilter());
//		advancedNodeCreator.setCnfType(CNFType.Regular);
//		advancedNodeCreator.setIncludeBooleanValues(false);
//
//		final Node createNodes = advancedNodeCreator.createNodes();
//		satInstance = new SatInstance(createNodes, Functional.toList(FeatureUtils.getConcreteFeatureNames(MyUtils.getFeatureModel())));
		featureModel = MyUtils.getFeatureModel(); 
		cnfCreator = new CNFCreator(featureModel); 
		cnf = cnfCreator.createNodes(); 
		pairGen = new PairWiseConfigurationGenerator(cnf, 300); 
		
		// set predefined configuration for incling calculation
//		Collection<int[]> predefinedConfs = new ArrayList<>();  
//		for(List<String> conf : confs) {
//			int[] array = satInstance.convertToInt(conf);
//			predefinedConfs.add(array); 
//		}
//		if(predefinedConfs.size() > 0) {
//			pairGen.setPredefinedConfigurations(predefinedConfs);
//		}
	}
	
	public void setAnalysisIsFinished() {
		isAnalysisFinished = true; 
	}
	
	// create threat that calculates configurations
	public Thread getAnalyzeThread() {
		Thread analysisThread = new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					pairGen.analyze(monitor);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("Analysis Finished");
				isAnalysisFinished = true;
			}
		}; 
		return analysisThread; 
	}
	
	// create thread that reads created configurations from queue and writes out
	public Thread getReaderThread() {
		Thread readerThread = new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					while(true) {
						System.out.println(pairGen.getResultQueue().size());
						System.out.println(pairGen.getResultQueue().take());
//						generateConfiguration(pairGen.getResultQueue().take()); 
								//satInstance.convertToString(pairGen.q.take().getModel())); 
						// thread runs until no confiugration remains in queue
						if(pairGen.getResultQueue().size() == 0 && isAnalysisFinished) {
							break; 
						}
//						Thread.sleep(100);
						//3.5.5
//						System.out.println(pairGen.q.size());
//						generateConfiguration(satInstance.convertToString(pairGen.q.take().getModel())); 
//						// thread runs until no confiugration remains in queue
//						if(pairGen.q.size() == 0 && isAnalysisFinished) {
//							break; 
//						}
//						Thread.sleep(100);
					}
					System.out.println("Queue pull finished");
					isReadcomplete = true; 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			private void generateConfiguration(List<String> solution) {
					java.util.Collections.sort(solution);
					if(solution.isEmpty()) {
					}
					else {
						if(!isDuplicate(solution)) {
							handler.addToConfList(solution);
							handler.writeConf(solution, handler.getConfList().indexOf(solution));
						}
					}
			}
			
			private boolean isDuplicate(List<String> solution) {
				for(List<String> conf : handler.getConfList()) {
					java.util.Collections.sort(conf);
					if(conf.equals(solution)) {
						return true; 
					}
				}
				return false;
			}
		}; 
		
		return readerThread; 
	}
}
