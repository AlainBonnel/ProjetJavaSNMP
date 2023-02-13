package MIB;

import java.io.*;
import java.util.*;

public class MIB {
	private String Chemin;
	private HashMap Hashmap;
	private String[] descr;
	private String[] name;
	private String[] adresse;
	private String[] uptime;
	
	
	MIB(String chemin) throws IOException{
		Chemin = chemin;
		try {
			FileReader fileReader = new FileReader(Chemin);
			BufferedReader reader = new BufferedReader (fileReader);
			String line = reader.readLine();
		    System.out.println(line);
		    if (line == "MIB PROJECT SNMP"){
		    	line = reader.readLine();
		    	String[] tab = line.split(",");
		    	name[0] = tab[0];
		    	name[1] = tab[1];
		    	String oid = tab[2];
		    	Hashmap.put(oid,tab);
		    	
		            
		      }} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void lirefichier() {
		Hashmap = new HashMap<String, String[]>();
		
		
	}

}
