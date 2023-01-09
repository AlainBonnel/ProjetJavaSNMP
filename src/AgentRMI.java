import java.rmi.RemoteException;

public interface AgentRMI 
	{
		public void setAdresse() throws RemoteException;
		public void setNom() throws RemoteException;
		public String getAdresse() throws RemoteException;
		public String getNom() throws RemoteException;
	}