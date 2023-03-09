package agent;

import java.rmi.RemoteException;

import exceptions.*;
import trap.Trap;

public interface Agent extends java.rmi.Remote {

	public String set(String value, String modif, String commu) throws RemoteException, ElementInexistant;

	public String get(String value, String commu) throws RemoteException, ElementInexistant;

	public String getNext(String key) throws RemoteException, ElementInexistant;

	public void ajouterTrap(String agent, Trap t) throws RemoteException, TrapPresent, ElementInexistant;

}