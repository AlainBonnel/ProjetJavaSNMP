import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class AgentImp extends UnicastRemoteObject implements Agent, Serializable,Runnable {

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
    

	@Override
	public void run() {
		// Dï¿½marre le rmiregistry
    	try {
		LocateRegistry.createRegistry(1099);
    	}
    	catch(Exception e) {
    	}
		// Crï¿½e et installe un gestionnaire de sï¿½curitï¿½
		// inutile si on ne tï¿½lï¿½charge pas les classes des stubs et parametres
		// System.setSecurityManager(new RMISecurityManager());
		try {
			Naming.rebind(this.nom, this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("A dé½claré auprès du serveur de noms");
	}

	}
