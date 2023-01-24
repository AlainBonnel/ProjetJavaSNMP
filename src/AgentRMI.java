import java.rmi.RemoteException;

public interface AgentRMI extends java.rmi.Remote
	{
		public void setAdresse(String adresse) throws RemoteException;
		public void setNom(String nom) throws RemoteException;
		public String getAdresse() throws RemoteException;
		public String getNom() throws RemoteException;
	}