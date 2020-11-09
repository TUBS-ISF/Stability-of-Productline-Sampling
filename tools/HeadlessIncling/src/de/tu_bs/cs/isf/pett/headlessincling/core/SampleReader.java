package de.tu_bs.cs.isf.pett.headlessincling.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SampleReader {

	public SampleReader() {

	}
	
	public List<List<String>> readSample(String path){
		List<List<String>> sample = new ArrayList<>(); 
		File folder = new File(path);
		if(folder.exists()) {
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				String fileName = listOfFiles[i].getName(); 
				if (listOfFiles[i].isFile()) {
				  if(fileName.endsWith(".config")) {
					  List<String> configuration = read(listOfFiles[i].getPath());
					  if(!configuration.isEmpty()) {
						  java.util.Collections.sort(configuration);
						  if(!sample.contains(configuration)) {
							sample.add(configuration);
						  }
					  }
					  
				  }
			  } else if (listOfFiles[i].isDirectory()) {
			    System.out.println("Directory " + listOfFiles[i].getName());
			  }
			}
		}
		return sample; 
	}
	
	private List<String> read(String path){
		List<String> configuration = new ArrayList<>(); 
		try {
			configuration = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return configuration;
	}
}
