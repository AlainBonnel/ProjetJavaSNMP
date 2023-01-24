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
}
