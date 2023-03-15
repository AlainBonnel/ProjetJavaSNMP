package agent;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Set;

import MIB.Information;
import MIB.MIB;
import exceptions.ElementInexistant;
import trap.Trap;

public class AgentImp extends UnicastRemoteObject implements Agent, Serializable, Runnable {

	private MIB mib;

	public AgentImp(MIB mib) throws RemoteException {
		super();
		this.mib = mib;
	}

	@Override
	public synchronized String set(String value, String modif, String commu, String manager)
			throws RemoteException, ElementInexistant {
		Set<? extends Map.Entry<String, Information>> entries = this.mib.getHashmap().entrySet();
		// on cherche dans la hashmap si l'élément est présent
		for (Map.Entry<String, Information> entry : entries) {
			if (entry.getValue().getNom().equalsIgnoreCase(value)) {
				// si l'élément est présent on vérifie que l'utilisateur à choisi une communauté
				// ayant les droits d'écriture
				if (entry.getValue().getDroit()[1].equals(commu)) {
					entry.getValue().setValeur(modif, manager);
					return "Modification effectuée";
				} else {
					return "Vous n'avez pas les droits d'écriture sur cette information";
				}
			}
		}
		return "L'élément cherché n'existe pas";
	}

	@Override
	public synchronized String get(String value, String commu, String manager)
			throws RemoteException, ElementInexistant {
		Set<? extends Map.Entry<String, Information>> entries = this.mib.getHashmap().entrySet();
		// on cherche dans la hashmap si l'élément est présent
		for (Map.Entry<String, Information> entry : entries) {
			if (entry.getValue().getNom().equalsIgnoreCase(value)) {
				// si l'élément est présent on vérifie que l'utilisateur à choisi une communauté
				// ayant les droits d'accès
				if (entry.getValue().getDroit()[0].equals(commu) || entry.getValue().getDroit()[1].equals(commu)) {
					return entry.getValue().getValeur();
				} else {
					return "Vous n'avez pas les droits d'accès à cette information";
				}
			}
		}
		return "L'élément cherché n'existe pas";
	}

	@Override
	public synchronized String getNext(String key) throws RemoteException, ElementInexistant {
		// on verifie que l'OID existe
		if (this.mib.getHashmap().containsKey(key)) {
			// on incremente l'OID de 1, on doit le cast en int pour l'incrementer et le
			// remettre en String
			String[] tab = key.split("\\.");
			tab[tab.length - 1] = Integer.toString((Integer.parseInt(tab[tab.length - 1]) + 1));
			key = String.join(".", tab);
			// on verifie alors qu'il y a un suivant
			if (this.mib.getHashmap().containsKey(key)) {
				// on renvoie l'OID suivant
				return key;
			}
		} else {
			return "Pas de prochain element";
		}
		return "Cette OID n'existe pas";
	}

	@Override
	public synchronized void ajouterTrap(String element, Trap t, String manager)
			throws RemoteException, ElementInexistant {
		Information i = (Information) this.mib.getHashmap().get(element);
		if (i.getTrap().containsKey(manager)) {
			System.out.println("Le trap est deja actif");
		} else {
			i.setTrap(manager, t);
			System.out.println("Le trap est activé");
			System.out.println("Voici la liste des abonnés :");
			i.afficherAbo();
		}
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
			Information i = (Information) this.mib.getHashmap().get("0.2");
			System.out.println(i.getValeur());
			Naming.rebind(i.getValeur(), this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("A declare aupres du serveur de noms");
	}

	// public static void main(String args[]) throws IOException {
	// AgentImp a1 = new AgentImp(new MIB(
	// "/Users/alainbonnel/Documents/UPSSITECH/S8/Java/ProjetSNMP/ProjetJavaSNMP/src/MIB/mibagent1.txt"));
	// AgentImp a2 = new AgentImp(new MIB(
	// "/Users/alainbonnel/Documents/UPSSITECH/S8/Java/ProjetSNMP/ProjetJavaSNMP/src/MIB/mibagent2.txt"));
	// AgentImp a3 = new AgentImp(new MIB(
	// "/Users/alainbonnel/Documents/UPSSITECH/S8/Java/ProjetSNMP/ProjetJavaSNMP/src/MIB/mibagent3.txt"));

	// Thread t1 = new Thread(a1);
	// Thread t2 = new Thread(a2);
	// Thread t3 = new Thread(a3);

	// t1.start();
	// t2.start();
	// t3.start();
	// }

}
