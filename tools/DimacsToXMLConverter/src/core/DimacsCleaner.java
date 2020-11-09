package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DimacsCleaner {
	
	private String INPUT_PATH = "C:\\Users\\t.pett\\Documents\\Research\\2019\\Sampling_Stability_Paper\\FIDE_Workspace\\2010-05-04_15-45-25\\kconfig.dimacs"; 
	private String OUTPUT_PATH = "C:\\Users\\t.pett\\Documents\\Research\\2019\\Sampling_Stability_Paper\\FIDE_Workspace\\2010-05-04_15-45-25\\clean.dimacs";
	private final StringBuilder BUILDER = new StringBuilder();
	
	public DimacsCleaner() {
		
	}
	
	public void CleanDimacs(String input, String output) {
		INPUT_PATH = input; 
		OUTPUT_PATH = output;
		readFile(); 
		writeFile();
	}
	
	private void readFile() {
		try (BufferedReader br = Files.newBufferedReader(Paths.get(INPUT_PATH))) {
            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                BUILDER.append(filterLine(line)).append("\n");
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        System.out.println(BUILDER);
	}
	
	private String filterLine(String unfilteredLine) {
		String filteredLine = ""; 
		if(unfilteredLine.startsWith("c")) {
			String[] stringArray = unfilteredLine.split(" "); 
			filteredLine = stringArray[0] + " " + stringArray[1] + " " + stringArray[2]; 
		}
		else {
			filteredLine = unfilteredLine; 
		}
		return filteredLine;
	}
	
	private void writeFile() {
		try (FileWriter writer = new FileWriter(OUTPUT_PATH	);
	             BufferedWriter bw = new BufferedWriter(writer)) {

	            bw.write(BUILDER.toString());

	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
	}

}
