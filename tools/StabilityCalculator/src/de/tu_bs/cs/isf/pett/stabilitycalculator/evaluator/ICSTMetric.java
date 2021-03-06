package de.tu_bs.cs.isf.pett.stabilitycalculator.evaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Sets;

import java.util.Set;

import de.ovgu.featureide.fm.core.base.IFeatureModel;

public class ICSTMetric extends AMetric{
	private HashMap<Integer, Set<String>> sampleMap_old;
	private HashMap<Integer, Set<String>> sampleMap_new;
	
	protected Set<String> combinedFeatureSet; 
	
	protected List<ConfigurationPair> pairList = new ArrayList<>();
	
	//constructor
	public ICSTMetric() {
		
	}
	
	@Override
	public double analyze(IFeatureModel fm1, List<List<String>> sample1List, IFeatureModel fm2,
			List<List<String>> sample2List) {
		// transform sample lists to sets
		this.sample_old = SampleListToSet(sample1List);
		this.sample_new = SampleListToSet(sample2List); 
		//build combined feature set
		combinedFeatureSet = buildCombinedFS(fm1, fm2);
		// find core and dead features for the feature models
		getCoreDead(1, fm1);
		getCoreDead(2, fm2);
		// remove core and dead features from input samples
		removeCoreDead(sample_old);
		removeCoreDead(sample_new);
		// transform set of sets to map of sets
		this.sampleMap_old = generateSampleMap(sample_old); 
		this.sampleMap_new = generateSampleMap(sample_new);
		// find configuration pairs old sample --> new sample
		findConfigurationPairs(sampleMap_old, sampleMap_new);
		//find configuration pairs  ne sample --> old sample
		findConfigurationPairs(sampleMap_new, sampleMap_old);
		// aggregate similarity
		return simAgregation(pairList);
	}
	
	private double simAgregation(List<ConfigurationPair> pairList) {
		double simSum = 0; 
		for(ConfigurationPair pair : pairList) {
			simSum += pair.getSimilarity(); 
		}
		return simSum / pairList.size(); 
	}
	
	private void findConfigurationPairs(HashMap<Integer, Set<String>> sample1, HashMap<Integer, Set<String>> sample2) {
		Set<Integer> keySet1 = new HashSet<>(); 
		keySet1.addAll(sample1.keySet());
		
		Set<Integer> keySet2 = new HashSet<>(); 
		keySet2.addAll(sample2.keySet());
		
		for(int key1 : keySet1) {
			double maxSimilarity = 0;
			ConfigurationPair confPair = new ConfigurationPair(); 
			for(int key2 : keySet2) {
				double confSim = calcConfSim(sample1.get(key1), sample2.get(key2)); 
				if(confSim > maxSimilarity) {
					maxSimilarity = confSim; 
					confPair = new ConfigurationPair(key1, key2, confSim); 
				}
			}
			this.pairList.add(confPair); 
		}
	}
	
	protected double calcConfSim(Set<String> conf1, Set<String> conf2) {
		 
		Set<String> difFSconf1 = Sets.difference(combinedFeatureSet, conf1);
		Set<String> difFSconf2 = Sets.difference(combinedFeatureSet, conf2);
		double absConfIntersec = (double) Sets.intersection(conf1, conf2).size();
		double absDifIntersec = (double) Sets.intersection(difFSconf1, difFSconf2).size(); 
		
		return (absConfIntersec + absDifIntersec) / combinedFeatureSet.size(); 
	}
	
	private HashMap<Integer, Set<String>> generateSampleMap(Set<Set<String>> sample) {
		HashMap<Integer, Set<String>> map = new HashMap<>(); 
		int index = 1; 
		for(Set<String> conf : sample) {
			Integer key = index++; 
			map.put(key, conf); 
		}
		return map; 
	}
	
	private Set<String> buildCombinedFS(IFeatureModel fm1, IFeatureModel fm2){
		
		Set<String> fs1 = ListToSet(fm1.getFeatureOrderList()); 
		Set<String> fs2 = ListToSet(fm2.getFeatureOrderList()); 
		
		return Sets.union(fs1, fs2); 
	}
	
}
