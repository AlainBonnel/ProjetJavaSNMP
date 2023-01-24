import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Agent extends UnicastRemoteObject implements AgentRMI,Serializable{

    private String nom;

    private String adresse;

    public Agent(String n, String a)throws RemoteException{
    	super();
        this.nom = n;
        this.adresse = a;
    }

    public String getNom() throws RemoteException{
        return this.nom;
    }

    public void setNom(String nom) throws RemoteException{
        this.nom = nom;
    }

    public String getAdresse() throws RemoteException{
        return this.adresse;
    }

    public void setAdresse(String adresse)throws RemoteException {
        this.adresse = adresse;
    }
}