package MIB;
import java.io.*;
import java.util.*;


public class MIBSystem {
	private HashMap Hashmap;
    private LinkedList list;
	public MIBSystem() {
		this.list = new LinkedList<String>();
	}
   
   public void init(){
	   list.add("SysDescr");
	   list.add("SysName");
	   list.add("SysUptime");
	   
	   list.add("Manager1Name");
	   list.add("Manager1MDP");
	   list.add("Manager1Addr");
	   list.add("Manager1Abo");
	   list.add("Manager1Acces");
	   
	   list.add("Manager2Name");
	   list.add("Manager2Mdp");
	   list.add("Manager2Addr");
	   list.add("Manager2Abo");
	   list.add("Manager2Acces");
	   
	   list.add("Interface1Descr");
	   list.add("Interface1Etat");
	   list.add("Interface1MTU");
	   list.add("Interface1Addr");
	   list.add("Interface1Error");
	   
	   list.add("Interface2Descr");
	   list.add("Interface2Etat");
	   list.add("Interface2MTU");
	   list.add("Interface2Addr");
	   list.add("Interface2Error");
   }
   
   public String getNext(String i) {
	   String r ="null";
	   if(list.contains(i)) {
		   for(int a=0;a<list.size()-1;a++) {
			   if(list.get(a) == i) {
				   r = list.get(a+1).toString();
			   }
		   }
	   }
	   return r;
   }
   
   public static void main (String[] args){
	   MIBSystem c = new MIBSystem();
	   c.init();
	   System.out.print(c.getNext("Manager2Abo"));
	   System.out.print(c.getNext("Interface2Error"));
   }
  
}