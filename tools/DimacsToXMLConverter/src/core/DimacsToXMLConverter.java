 package core;

import java.io.File;

import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;
import de.ovgu.featureide.fm.core.io.manager.FileHandler;
import de.ovgu.featureide.fm.core.io.manager.SimpleFileHandler;

public class DimacsToXMLConverter {

	private static String INPUT_PATH = "./Testdata/kconfig.dimacs"; 
	private static String OUTPUT_PATH_DIMACS = "./Testdata/clean.dimacs";
	private static String OUTPUT_PATH_XML = "./Testdata/model.xml";
	
	public static void main(String[] args) {
		if(args.length == 3) {
			new DimacsToXMLConverter(args[0], args[1], args[2]); 
		}
		else {
			System.out.println("No suitable arguments provided for run. Provide the path to the base directory as single string");
			System.out.println("Order to provide arguments: csvPath, Model1, sample1, model2, sample2");
			new DimacsToXMLConverter(INPUT_PATH, OUTPUT_PATH_DIMACS, OUTPUT_PATH_XML);
		}
	}
	
	public DimacsToXMLConverter(String inputPath, String outputPathDimacs, String outputPathXML) {
		startConversion(inputPath, outputPathDimacs, outputPathXML);
	}
	
	private void startConversion(String inputPath, String outputPathDimacs, String outputPathXML) {
		// clean kclause dimacs 
		DimacsCleaner cleaner = new DimacsCleaner(); 
		cleaner.CleanDimacs(inputPath, outputPathDimacs);
		
		// use previously cleaned dimacs as new input for conversion
		File inputDimacs = new File(outputPathDimacs); 
		File outputXML = new File(outputPathXML);
		// create core feature model in outputpath
		BasicFeatureModelWriter bfmw = new BasicFeatureModelWriter(); 
		bfmw.createFmFile(outputXML.getPath());
		
		final FileHandler<IFeatureModel> fileHandlerDimacsIn = FeatureModelManager.load(inputDimacs.toPath());
		final FileHandler<IFeatureModel> fileHandlerXMLOut = FeatureModelManager.load(outputXML.toPath()); 
		
		SimpleFileHandler.convert(inputDimacs.toPath(), outputXML.toPath(), fileHandlerXMLOut.getObject(), fileHandlerDimacsIn.getFormat(), fileHandlerXMLOut.getFormat());
		
	}

}
