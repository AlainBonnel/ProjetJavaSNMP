import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class AgentImp extends UnicastRemoteObject implements Agent, Serializable {

    private String nom;

    private String adresse;

    public AgentImp(String n, String a) throws RemoteException {
        super();
        this.nom = n;
        this.adresse = a;
    }

    public String getNom() throws RemoteException {
        return this.nom;
    }

    public void setNom(String nom) throws RemoteException {
        this.nom = nom;
    }

    public String getAdresse() throws RemoteException {
        return this.adresse;
    }

    public void setAdresse(String adresse) throws RemoteException {
        this.adresse = adresse;
    }
    
    public static void main(String args[]) throws Exception, MalformedURLException {
		// Dï¿½marre le rmiregistry
    	try {
		LocateRegistry.createRegistry(1099);
    	}
    	catch(Exception e) {
    	}
		// Crï¿½e et installe un gestionnaire de sï¿½curitï¿½
		// inutile si on ne tï¿½lï¿½charge pas les classes des stubs et parametres
		// System.setSecurityManager(new RMISecurityManager());
		AgentImp a = new AgentImp("AgentA", "192.168.10.1");
		Naming.rebind("A", a);
		System.out.println("A dé½claré auprès du serveur de noms");
	}
}