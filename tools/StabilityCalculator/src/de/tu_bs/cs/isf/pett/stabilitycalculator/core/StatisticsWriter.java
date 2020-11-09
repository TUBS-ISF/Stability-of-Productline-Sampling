package de.tu_bs.cs.isf.pett.stabilitycalculator.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StatisticsWriter {
	
	public StatisticsWriter() {
	}
	
	public void writeStatistics(String path, String metric, String fileName, String entryName, String results) {
		String outputPath = path+"/"+metric+"/"+fileName;
		File file = new File(outputPath); 
		StringBuilder sb = new StringBuilder(); 
		if(file.exists()) {
			try {
				List<String> list = Files.readAllLines(Paths.get(outputPath));
				for(String s : list) {
					sb.append(s + "\n"); 
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		sb.append(entryName + results); 
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(sb.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	

}
