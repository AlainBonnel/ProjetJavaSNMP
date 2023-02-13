package MIB;

import java.util.*;

public class MIB {

	private HashMap Hashmap;
	private String[] name;
	private String[] descr;
	private String[] adresse;
	private String[] uptime;
	
	
	MIB(){
		this.lirefichier();
	}
	
	public void lirefichier() {
		Hashmap = new HashMap<String, String[]>();
		
		
	}

}
