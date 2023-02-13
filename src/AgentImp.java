import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class AgentImp extends UnicastRemoteObject implements Agent, Serializable, Runnable {

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
		// Demarre le rmiregistry
		try {
			LocateRegistry.createRegistry(1099);
		} catch (Exception e) {
		}
		// Cree et installe un gestionnaire de securite
		// inutile si on ne telecharge pas les classes des stubs et parametres
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
		System.out.println("A d�clar� aupr�s du serveur de noms");
	}

	public static void main(String args[]) throws RemoteException, MalformedURLException {
		AgentImp a1 = new AgentImp("Agent1", "192.168.12.51");
		AgentImp a2 = new AgentImp("Agent2", "192.168.12.52");
		AgentImp a3 = new AgentImp("Agent3", "192.168.12.53");

		Thread t1 = new Thread(a1);
		Thread t2 = new Thread(a2);
		Thread t3 = new Thread(a3);

		t1.start();
		t2.start();
		t3.start();
	}

}
