package de.tu_bs.cs.isf.pett.stabilitycalculator.evaluator;

import java.util.List;
import java.util.Set;

import de.ovgu.featureide.fm.core.base.IFeatureModel;

public class ROIC extends AMetric{
	
	
	
	public ROIC() {
	}

	@Override
	public double analyze(IFeatureModel fm1, List<List<String>> sample1List, IFeatureModel fm2,
			List<List<String>> sample2List) {
		this.sample_old = SampleListToSet(sample1List); 
		this.sample_new = SampleListToSet(sample2List); 
		getCoreDead(1, fm1);
		getCoreDead(2, fm2);
		
		removeCoreDead(this.sample_old);
		removeCoreDead(this.sample_new);
		
//		System.out.println("### " + this.sample1.toString());
//		System.out.println("### " + this.sample2.toString());
		
//		Set<Set<String>> intersect = Sets.intersection(this.sample1, this.sample2);
		Set<Set<String>> intersect = intersect(this.sample_old, this.sample_new);
//		System.out.println("intersect: " + intersect.toString());
//		Set<Set<String>> union = Sets.union(this.sample1, this.sample2);
		Set<Set<String>> union = union(this.sample_old, this.sample_new);
//		System.out.println("Union size: " + union.size());
		
//		double stability = (double) intersect.size() / (double) union.size();
		// Use max sample size instead of union.size
		double stability = (double) intersect.size() / Math.max(this.sample_old.size(), this.sample_new.size());
		
	return stability; 
	}

}
