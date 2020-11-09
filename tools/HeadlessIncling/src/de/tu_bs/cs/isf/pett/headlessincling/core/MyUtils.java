package de.tu_bs.cs.isf.pett.headlessincling.core;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import de.ovgu.featureide.fm.core.analysis.cnf.formula.FeatureModelFormula;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.impl.DefaultFeatureModelFactory;
import de.ovgu.featureide.fm.core.base.impl.FMFactoryManager;
import de.ovgu.featureide.fm.core.base.impl.FMFormatManager;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.ConfigurationAnalyzer;
import de.ovgu.featureide.fm.core.configuration.FeatureNotFoundException;
import de.ovgu.featureide.fm.core.configuration.Selection;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;
import de.ovgu.featureide.fm.core.io.manager.FileHandler;
import de.ovgu.featureide.fm.core.io.xml.XmlFeatureModelFormat;

public class MyUtils {

	private static IFeatureModel featureModel = null;

	public static void setFeatueModel(Path fmPath) {
		// 3.5.5
//		FileHandler<IFeatureModel> fmHandler = FeatureModelManager.load(fmPath);
//		IFeatureModel model = null; 
//		if(fmHandler != null) {
//			model = fmHandler.getObject();
//		}
//		featureModel = model;
		System.out.println(fmPath);

		XmlFeatureModelFormat format = new XmlFeatureModelFormat();
		FMFormatManager.getInstance().addExtension(format);

		FMFactoryManager.getInstance().addExtension(DefaultFeatureModelFactory.getInstance());
		FMFactoryManager.getInstance().setWorkspaceLoader(null);
		FMFactoryManager.getInstance().addFactoryWorkspace(fmPath);

		FileHandler<IFeatureModel> fmHandler = FeatureModelManager.getFileHandler(fmPath);
		IFeatureModel model = fmHandler.getObject();
//		IFeatureModel model = FeatureModelManager.load(fmPath);
		if (model == null) {
			System.out.println("Model is null");
		}
		featureModel = model;
	}

	public static Configuration ListToConfig(List<String> list) {
		final Configuration configuration = new Configuration(new FeatureModelFormula(featureModel));
		for (final String selection : list) {
			configuration.setManual(selection, Selection.SELECTED);
		}
		return configuration;
	}

	public static List<String> ConfToString(Configuration c) {
		List<String> list = new ArrayList<>();
		for (IFeature sf : c.getSelectedFeatures()) {
			list.add(sf.getName());
		}
		return list;
	}

	public static IFeatureModel getFeatureModel() {
		return featureModel;
	}

	public static List<List<String>> getValidConf(List<List<String>> sample) {
		List<List<String>> validConfs = new ArrayList<>();

		for (List<String> c : sample) {
			try {
				Configuration conf = ListToConfig(c);
				ConfigurationAnalyzer confAnalyzer = new ConfigurationAnalyzer(conf.getFeatureModelFormula(), conf);
				if (confAnalyzer.isValid()) {
					List<String> featureList = ConfToString(conf);
					validConfs.add(featureList);
				} else {
					System.out.println("Invalid Conf found");
				}
			} catch (FeatureNotFoundException fnfEx) {
				System.out.println(
						"Tried to validate configuration against feature model. Failed because of Feature not found exception during validation");
				fnfEx.printStackTrace();
				continue;
			}
		}
		return validConfs;
	}

}
