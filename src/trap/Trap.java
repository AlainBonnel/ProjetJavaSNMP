package trap;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Trap extends Remote {
    public void trap(String message) throws RemoteException;
}
