package manager;

import java.rmi.Remote;
import java.rmi.RemoteException;

import agent.Agent;

public interface Manager extends Agent, Remote {

    public String controleDistant(String commande, Agent a, String value, String modif, String commu)
            throws RemoteException;

    public String getNom() throws RemoteException;

    public int getHierarchie() throws RemoteException;

    public String getCommu() throws RemoteException;

}
