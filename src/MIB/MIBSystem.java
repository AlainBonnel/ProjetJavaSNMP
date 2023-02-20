package MIB;
import java.io.*;
import java.util.HashMap;


public class MIBSystem {
	private HashMap Hashmap;
   
   
   public void init(){
	   Hashmap.put("1.0.1","SysDescr");
	   Hashmap.put("1.0.2","SysName");
	   Hashmap.put("1.0.3","SysUptime");
	   
	   Hashmap.put("2.0.1.1","Manager1Name");
	   Hashmap.put("2.0.1.2","Manager1MDP");
	   Hashmap.put("2.0.1.3","Manager1Addr");
	   Hashmap.put("2.0.1.4","Manager1Abo");
	   
	   Hashmap.put("2.0.2.1","Manager2Name");
	   Hashmap.put("2.0.2.2","Manager2Mdp");
	   Hashmap.put("2.0.2.3","Manager2Addr");
	   Hashmap.put("2.0.2.4","Manager2Abo")
	   ;
	   Hashmap.put("3.0.1.1","Interface1Descr");
	   Hashmap.put("3.0.1.2","Interface1Etat");
	   Hashmap.put("3.0.1.3","Interface1MTU");
	   Hashmap.put("3.0.1.4","Interface1Addr");
	   Hashmap.put("3.0.1.5","Interface1Error");
	   
	   Hashmap.put("3.0.2.1","Interface2Descr");
	   Hashmap.put("3.0.2.2","Interface2Etat");
	   Hashmap.put("3.0.2.3","Interface2MTU");
	   Hashmap.put("3.0.2.4","Interface2Addr");
	   Hashmap.put("3.0.2.5","Interface2Error");
   }
}