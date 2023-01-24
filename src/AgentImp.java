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
		// D�marre le rmiregistry
    	try {
		LocateRegistry.createRegistry(1099);
    	}
    	catch(Exception e) {
    	}
		// Cr�e et installe un gestionnaire de s�curit�
		// inutile si on ne t�l�charge pas les classes des stubs et parametres
		// System.setSecurityManager(new RMISecurityManager());
		try {
			AgentImp a = new AgentImp("AgentA", "192.168.10.1");
			Naming.rebind("A", a);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("A d�clar� aupr�s du serveur de noms");
	}

	}
