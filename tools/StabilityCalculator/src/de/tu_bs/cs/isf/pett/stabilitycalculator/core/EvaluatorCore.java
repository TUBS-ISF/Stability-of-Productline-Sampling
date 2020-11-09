package de.tu_bs.cs.isf.pett.stabilitycalculator.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.FeatureNotFoundException;
import de.ovgu.featureide.fm.core.configuration.Selection;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;
import de.ovgu.featureide.fm.core.io.manager.FileHandler;
import de.tu_bs.cs.isf.pett.stabilitycalculator.evaluator.FIMDC;
import de.tu_bs.cs.isf.pett.stabilitycalculator.evaluator.ICSTMetric;
import de.tu_bs.cs.isf.pett.stabilitycalculator.evaluator.MSOC;
import de.tu_bs.cs.isf.pett.stabilitycalculator.evaluator.ROIC;

public class EvaluatorCore {

	private String pathFM_old;
	private String pathFM_new;
	private String pathSample_old;
	private String pathSample_new;
	private String csvPath;
	private String csvFileName; 
	
	private String name1; 
	private String name2;
	
	private IFeatureModel FM_old; 
	private IFeatureModel FM_new; 
	
	private List<List<String>> sample_old = new ArrayList<>(); 
	private List<List<String>> sample_new = new ArrayList<>();
	
	private String metric = "";
	
	private SampleReader sr = new SampleReader(); 
	private StatisticsWriter sw; 
	
	public EvaluatorCore(String metric, String csvPath, String csvFileName, String pathFM_old, String pathSample_old, String pathFM_new, String pathSample_new, String name1, String name2) {
		this.metric = metric; 
		this.csvPath = csvPath;
		this.csvFileName = csvFileName; 
		this.pathFM_old = pathFM_old;
		this.pathSample_old = pathSample_old;
		this.pathFM_new = pathFM_new;
		this.pathSample_new = pathSample_new;
		
		this.name1 = name1; 
		this.name2 = name2; 
		
		sw = new StatisticsWriter(); 
	}
	
	public void execut() {
		
		FM_old = loadFeatueModel(Paths.get(pathFM_old)); 
		FM_new = loadFeatueModel(Paths.get(pathFM_new));
		
		sample_old = getValidConf(sr.readSample(pathSample_old), FM_old); 
		sample_new = getValidConf(sr.readSample(pathSample_new), FM_new);
		
		System.out.println("### FM_old size " + FM_old.getNumberOfFeatures()); // + " // " + sample1.toString() + "###");
		System.out.println("### FM_new size " + FM_new.getNumberOfFeatures()); // + " // " + sample2.toString() + "###");

		double result = 0;
		if(this.metric.toLowerCase().equals(Metrics.ROIC.getMetricName())) {
			ROIC roic = new ROIC(); 
			result = roic.analyze(FM_old, sample_old, FM_new, sample_new);  
			System.out.println("Result roic: " + result);
		} else if (this.metric.toLowerCase().equals(Metrics.MSOC.getMetricName())) {
			MSOC msoc = new MSOC();
			result = msoc.analyze(FM_old, sample_old, FM_new, sample_new); 
			System.out.println("Result msoc: " + result ); 
		}else if(this.metric.toLowerCase().equals(Metrics.FIMDC.getMetricName())) {
			FIMDC fimdc = new FIMDC(); 
			result = fimdc.analyze(FM_old, sample_old, FM_new, sample_new); 
			System.out.println("Result fimdc: " + result);
		}else if(this.metric.toLowerCase().equals(Metrics.ICST.getMetricName())) {
			ICSTMetric icst = new ICSTMetric(); 
			result = icst.analyze(FM_old, sample_old, FM_new, sample_new); 
			System.out.println("Result icst: " + result);
		}else {
			System.out.println("No metric fit the given parameter");
		}
		
		Pattern p = Pattern.compile("model\\d*"); 
		Matcher m1 = p.matcher(pathFM_old); 
		m1.find();
		
		Matcher m2 = p.matcher(pathFM_new); 
		m2.find();
		
		String entryName = name1 + " to " + name2 + ";";
		String results = result+"";
		
		sw.writeStatistics(csvPath, metric.toLowerCase(), csvFileName, entryName, results);
		
	}
	
	private IFeatureModel loadFeatueModel(Path fmPath) {
		//3.5.5
		FileHandler<IFeatureModel> fmHandler = FeatureModelManager.load(fmPath);
		if(fmHandler == null) {
			System.out.println("Problems while loading FeatureModel from Path: " + fmPath.toString());
			System.exit(0);
		}
		return fmHandler.getObject();
	}
	
	private Configuration ListToConfig(List<String> list, IFeatureModel fm) {
		//3.5.5
		final Configuration configuration = new Configuration(fm, Configuration.PARAM_NONE);
		for (final String selection : list) {
			configuration.setManual(selection, Selection.SELECTED);
			
		} 
		return configuration;
	}
	
	private List<String> ConfToString(Configuration c){
		List<String> list = new ArrayList<>(); 
		for(IFeature sf : c.getSelectedFeatures()) {
			list.add(sf.getName()); 
		}
		return list; 
	}
	
	private List<List<String>> getValidConf(List<List<String>> sample, IFeatureModel fm){
		List<List<String>> validConfs = new ArrayList<>();
		 
		for(List<String> c : sample) {
			try {
				Configuration conf = ListToConfig(c, fm); 
				if(conf.isValid()) {
					List<String> featureList = ConfToString(conf);
					validConfs.add(featureList); 
				}
				else {
					System.out.println("Invalid Conf found");
				}
			}
			catch(FeatureNotFoundException fnfEx) {
				System.out.println("Feature not Found exception");
				continue; 
			}
			
		}
		return validConfs; 
	}
	
	
	

}
