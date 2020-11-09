package de.tu_bs.cs.isf.pett.headlessincling.core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import de.ovgu.featureide.fm.core.io.FileSystem;

public class SampleWriter {
	
	private String baseSamplePath = null;
	private StringBuilder statisticsBuilder = new StringBuilder(); 
	
	public SampleWriter(String basePath) {
		this.baseSamplePath = basePath; 
	}
	
	public String writeFolder(String name) {
		String folderPath = "";
		try {
			System.out.println("Creting Directory");
			folderPath = baseSamplePath + "/products_"+name.replaceAll(".xml", ""); 
			FileSystem.mkDir(Paths.get(folderPath));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return folderPath;
	}
	
	public void writeConfiguration(String folderPath, List<String> list, int solutionNumber) {
		String prefix = getPrefix((int)solutionNumber);
		String filePath = folderPath +"/"+  prefix + ".config";
		StringBuilder sb = new StringBuilder();
		
		for(String s : list) {
			sb.append(s); 
			sb.append("\n"); 
		}
		if(sb.length() > 0) {
			sb.replace(sb.length()-1, sb.length(), "");
		}
		 
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filePath)); 
			bw.write(sb.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private String getPrefix(int number) {
		String count = String.valueOf(number);
		String prefix = "00000"; 
		switch (count.length()) {
		case 1: 
			prefix = "0000"+count; 
			break;
		case 2: 
			prefix = "000"+count; 
			break;
		case 3: 
			prefix = "00"+count; 
			break; 
		case 4: 
			prefix = "0"+count; 
			break; 
		default:
			prefix = count; 
			break;
		}
		return prefix; 
	}
	
	public void writeHeader() {
		statisticsBuilder.append("Name;Duration;Conf;Efficiency \n"); 
	}
	
	public void writeStatistics(String modelName, long duration, long confcount, long newCalculatedConf, long preAna) {
			double secDuration = ((double) duration/1000); 
			double effAll = (double) confcount / (double) secDuration; 
			String out = modelName + ";" + secDuration + ";" + confcount + ";" + effAll;
			statisticsBuilder.append(out); 
			statisticsBuilder.append("\n"); 
	}
	
	public void writeToFile(String modelName) {
		System.out.println("Writing Statistics");
		BufferedWriter bw;
		statisticsBuilder.replace(statisticsBuilder.length()-1, statisticsBuilder.length(), ""); 
		try {
			bw = new BufferedWriter(new FileWriter(baseSamplePath + "/statistics_" + "preservative" + ".csv"));
			bw.append(statisticsBuilder.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
