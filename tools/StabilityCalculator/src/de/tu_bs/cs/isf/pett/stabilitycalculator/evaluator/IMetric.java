package de.tu_bs.cs.isf.pett.stabilitycalculator.evaluator;

import java.util.List;

import de.ovgu.featureide.fm.core.base.IFeatureModel;

public interface IMetric {
	
	public double analyze(IFeatureModel fm1, List<List<String>> sample1List, IFeatureModel fm2, List<List<String>> sample2List); 

}
