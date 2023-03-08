import java.rmi.RemoteException;

public interface Agent extends java.rmi.Remote {

	public String set(String value, String modif, String commu) throws RemoteException;

	public String get(String value, String commu) throws RemoteException;

	public String getNext(String key, String commu) throws RemoteException;

}