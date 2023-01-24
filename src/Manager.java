import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class Manager {
	private String nom;
    private String adresse;
    private List<Agent> agents;
    
    public Manager(String n, String a)throws RemoteException{
        this.nom = n;
        this.adresse = a;
    }
    public void AjouterAgent(Agent a) {
    	agents.add(a);
    }
    public void RetirerAgent(Agent a) {
    	int i = 0;
    	for (Agent b : agents) {
    		if (a == b) {
    			agents.remove(i);
    		}
    		i++;
    	}
    }
    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public static void main(String args[]) {
    	try {
    	 // R�cup�ration d'un proxy sur l'objet
    	 AgentRMI c = (AgentRMI) Naming.lookup("A");
    	 // Appel d'une m�thode sur l'objet distant
    	 String message = c.getNom();
    	 System.out.println(message);
    	} catch (Exception e) {
    	 e.printStackTrace();
    	}
    	}  

	public void unreferenced() {
	 // utilis� pour lib�rer des ressources (threads, fichiers�)
	 }
}
