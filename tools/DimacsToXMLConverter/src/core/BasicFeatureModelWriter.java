package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BasicFeatureModelWriter {
	
	private static final String ROOT_IDENTIFIER = "__Root__";
	
	/**
	 * @return the rootIdentifier
	 */
	public static String getRootIdentifier() {
		return ROOT_IDENTIFIER;
	}
	
	public BasicFeatureModelWriter() {
		
	}
	
	public void createFmFile(String path) {
		StringBuilder sb = new StringBuilder(); 
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"); 
		sb.append("\t<featureModel>");
		sb.append("\t<properties/>");
		sb.append("\t<struct>");
		sb.append("\t\t<and abstract=\"true\" mandatory=\"true\" name=\"Root\">");
		sb.append("\t\t\t<feature name=\"Base\"/>");
		sb.append("\t\t</and>");
		sb.append("\t</struct>");
		sb.append("\t<constraints/>");
		sb.append("\t\t<calculations Auto=\"true\" Constraints=\"true\" Features=\"true\" Redundant=\"true\" Tautology=\"true\"/>");
		sb.append("\t<comments/>");
		sb.append("\t<featureOrder userDefined=\"false\"/>");
		sb.append("</featureModel>");
		
		writeBasicModel(sb, path);
		
	}
	
	private void writeBasicModel(StringBuilder sb, String path) {
		File file = new File(path);
		BufferedWriter writer = null; 
		try {
			writer = new BufferedWriter(new FileWriter(file)); 
			writer.append(sb); 
		} catch (IOException e) {
			System.out.println("Problems file opening output path.");
			System.out.println("shutting down system");
			e.printStackTrace();
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					System.out.println("Problems file opening output path.");
					System.out.println("shutting down system");
					e.printStackTrace();
				}
			}
		}
	}
	
}
