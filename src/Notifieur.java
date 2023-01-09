import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

class Notifieur extends UnicastRemoteObject implements IntPing,Serializable {
	Notifieur() throws RemoteException{
		super();
		
	}
public void notifier() {
	System.out.println("Le retrait dépasse le seuil convenu");
}
}