import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Agent extends UnicastRemoteObject implements AgentRMI{

    private String nom;

    private String adresse;

    public Agent(String n, String a)throws RemoteException{
    	super();
        this.nom = n;
        this.adresse = a;
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

	@Override
	public void setAdresse() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNom() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}