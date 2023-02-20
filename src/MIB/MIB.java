package MIB;

import java.io.*;
import java.util.*;

public class MIB {
	private String Chemin;
	private HashMap Hashmap;
	
	
	MIB(String chemin) throws IOException{
		Chemin = chemin;
		try {
			FileReader fileReader = new FileReader(Chemin);
			BufferedReader reader = new BufferedReader (fileReader);
			String line = reader.readLine();
		    System.out.println(line);
		    if (line == "MIB PROJECT SNMP"){
		    	while(line != null) {
		    	line = reader.readLine();
		    	String[] tab = line.split(",");	
		    	Hashmap.put(tab[0],tab[1]);
		    	}        
		      }} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void lirefichier() {
		Hashmap = new HashMap<String, String[]>();
		
		
	}

}
