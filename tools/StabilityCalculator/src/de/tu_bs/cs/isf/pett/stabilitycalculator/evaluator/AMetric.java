package de.tu_bs.cs.isf.pett.stabilitycalculator.evaluator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.prop4j.Node;
import org.prop4j.analyses.CoreDeadAnalysis;
import org.prop4j.solver.SatInstance;

import de.ovgu.featureide.fm.core.base.FeatureUtils;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.editing.AdvancedNodeCreator;
import de.ovgu.featureide.fm.core.editing.AdvancedNodeCreator.CNFType;
import de.ovgu.featureide.fm.core.filter.AbstractFeatureFilter;
import de.ovgu.featureide.fm.core.functional.Functional;
import de.ovgu.featureide.fm.core.job.LongRunningWrapper;
import de.ovgu.featureide.fm.core.job.monitor.ConsoleMonitor;
import de.ovgu.featureide.fm.core.job.monitor.IMonitor;

public abstract class AMetric implements IMetric{

	protected IMonitor monitor = new ConsoleMonitor();
	protected Set<String> core_old;
	protected Set<String> dead_old;
	
	protected Set<String> core_new;
	protected Set<String> dead_new;
	
	protected Set<Set<String>> sample_old;
	protected Set<Set<String>> sample_new; 
	
	protected void removeCoreDead(Set<Set<String>> sample) {
		for(Set<String> set : sample) {
			set.removeAll(core_old);
			set.removeAll(core_new);
			set.removeAll(dead_old);
			set.removeAll(dead_new);
		}
	}
	
	protected void getCoreDead(int olNewIdentifier, IFeatureModel fm) {
		List<List<String>> coreDead1 = ckeckCoreDead(getSat(fm), monitor);
		Set<String> core = ListToSet(coreDead1.get(1));
		Set<String> dead = ListToSet(coreDead1.get(0)); 
		
		if(olNewIdentifier == 1) { 
			core_old = core;
			dead_old = dead;
		}
		else if(olNewIdentifier == 2) {
			core_new = core;
			dead_new = dead;
		}	
	}
	
	public static Set<Set<String>> intersect(Set<Set<String>> sample1, Set<Set<String>> sample2) {
		Set<Set<String>> intersection = new HashSet<>(); 
		for(Set<String> conf1 : sample1) {
			for(Set<String> conf2 : sample2) {
				if(conf1.size() == conf2.size()) {
					Set<String> checkSet = new HashSet<>(); 
					checkSet.addAll(conf1); 
					checkSet.removeAll(conf2); 
					if(checkSet.size() == 0) {
						intersection.add(conf1); 
						break; 
					}
				}
			}
		}
		
		return intersection; 
	}
	
	protected Set<Set<String>> union(Set<Set<String>> sample1, Set<Set<String>> sample2) {
		Set<Set<String>> union = new HashSet<>();
		union.addAll(sample1);
		union.addAll(sample2); 
//		System.out.println(union.size() + " // " + union.toString());
//			for(Set<String> conf2 : sample2) {
//				if(!union.contains(conf2)) {
//					union.add(conf2); 
//				}
//			}
		return union; 
	}
	
	public static List<List<String>> ckeckCoreDead(SatInstance sat, IMonitor monitor){
		List<List<String>> coreDead = new ArrayList<List<String>>();
		List<String> core = new ArrayList<>();
		List<String> dead = new ArrayList<>();
		
		final int[] solution2 = LongRunningWrapper.runMethod(new CoreDeadAnalysis(sat), monitor.subTask(0));
		monitor.checkCancel();
		for (int i = 0; i < solution2.length; i++) {
			monitor.checkCancel();
			final int var = solution2[i];
			final String feature = (String) sat.getVariableObject(var);
			if (var < 0) {
				dead.add(feature);
			} else {
				core.add(feature);
			}
		}
		coreDead.add(dead); 
		coreDead.add(core); 
		return coreDead; 
	}
	
	protected Set<String> ListToSet(List<String> list){
		Set<String> set = new HashSet<>();  
		for(String s : list) {
			set.add(s); 
		}
		return set; 
	}
	
	protected SatInstance getSat(IFeatureModel fm) {
		final AdvancedNodeCreator advancedNodeCreator = new AdvancedNodeCreator(fm, new AbstractFeatureFilter());
		advancedNodeCreator.setCnfType(CNFType.Regular);
		advancedNodeCreator.setIncludeBooleanValues(false);

		final Node createNodes = advancedNodeCreator.createNodes();
		return new SatInstance(createNodes, Functional.toList(FeatureUtils.getConcreteFeatureNames(fm)));
	}
	
	protected Set<Set<String>> SampleListToSet(List<List<String>> sample) {
	Set<Set<String>> sampleSet = new HashSet<>();
	for(List<String> conf : sample) {
		Set<String> setConf = new HashSet<String>();
		for(String f : conf) {
			setConf.add(f);
		}
		sampleSet.add(setConf); 
	}
	return sampleSet; 
}
	
}
