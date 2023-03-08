import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import MIB.Information;
import javax.management.Notification;

import MIB.MIB;

public class AgentImp extends UnicastRemoteObject implements Agent, Serializable, Runnable {

	private MIB mib;

	public AgentImp(MIB mib) throws RemoteException {
		super();
		this.mib = mib;
	}

	@Override
	public void set(String value, String commu) throws RemoteException {
		System.out.println("uiset");
	}

	@Override
	public String get(String value, String commu) throws RemoteException {
		Information i = (Information) this.mib.getHashmap().get(value);
		boolean b = false;
		Set<? extends Map.Entry<String, Information>> entries = this.mib.getHashmap().entrySet();
		for (Map.Entry<String, Information> entry : entries) {
			if(entry.getValue().getNom().equalsIgnoreCase(value);

		}
		

		if (commu.equalsIgnoreCase(i.getDroit()[0])) {
			String[] tab = value.split("\\.");
			switch (tab[tab.length]) {
				case "1":

					break;
				case "2":

					break;
				case "3":

					break;

				default:
					break;
			}
		}
		return "ui";
	}

	@Override
	public String getNext(String key, String commu) {
		if (this.mib.getHashmap().containsKey(key)) {
			// on incremente l'OID de 1, on doit le cast en int pour l'incrementer et le
			// remettre en String
			String[] tab = key.split("\\.");
			tab[tab.length - 1] = Integer.toString((Integer.parseInt(tab[tab.length - 1]) + 1));
			key = String.join(".", tab);
			// on verifie alors qu'il y a un suivant
			if (this.mib.getHashmap().containsKey(key)) {
				// on recupere l'objet Information correspondant a la clef pour afficher toutes
				// ses informations
				// apres avoir verifier que l'utilisateur ait les droits
				if (this.mib.lectureAutorise(key, commu)) {
					return "Je renvoie toutes les informations ici";
				} else {
					return "Vous n'avez pas les droits d'acces pour cette OID";
				}
			} else {
				return "Pas de prochain element";
			}
		} else {
			return "Cette OID n'existe pas";
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

	public static void main(String args[]) throws IOException {
		AgentImp a1 = new AgentImp(new MIB(
				"/Users/alainbonnel/Documents/UPSSITECH/S8/Java/ProjetSNMP/ProjetJavaSNMP/src/MIB/mibagent1.txt"));
		AgentImp a2 = new AgentImp(new MIB(
				"/Users/alainbonnel/Documents/UPSSITECH/S8/Java/ProjetSNMP/ProjetJavaSNMP/src/MIB/mibagent2.txt"));
		AgentImp a3 = new AgentImp(new MIB(
				"/Users/alainbonnel/Documents/UPSSITECH/S8/Java/ProjetSNMP/ProjetJavaSNMP/src/MIB/mibagent3.txt"));

		Thread t1 = new Thread(a1);
		Thread t2 = new Thread(a2);
		Thread t3 = new Thread(a3);

		t1.start();
		t2.start();
		t3.start();
	}

}
