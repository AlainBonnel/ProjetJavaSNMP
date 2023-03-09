package MIB;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import trap.Trap;

public class Information {
	private String nom; // nom de l'information
	private String valeur; // valeur de l'information
	private String[] droit; // 0 = communauté de lecture, 1 = communauté d'écriture
	private HashMap<String, Trap> trap; // liste des des managers abonnés à l'information

	public Information(String n, String v, String commuR, String commuRW) throws RemoteException {
		trap = new HashMap<String, Trap>();
		nom = n;
		valeur = v;
		droit = new String[2];
		droit[0] = commuR;
		droit[1] = commuRW;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getValeur() {
		return this.valeur;
	}

	public void setValeur(String valeur, String manager) throws RemoteException {
		this.valeur = valeur;
		// on envoie un trap si l'information a été modifiée
		if (!trap.isEmpty()) {
			try {
				Set<? extends Map.Entry<String, Trap>> entries = trap.entrySet();
				// on affiche la liste des managers abonnés à l'information
				for (Map.Entry<String, Trap> entry : entries) {
					trap.get(entry.getKey()).trap("L'information " + nom + " a été modifiée" + " par " + manager);
				}
			} catch (RemoteException e) {
				trap = null;
			}
		}
	}

	public String[] getDroit() {
		return this.droit;
	}

	public void setDroit(String[] droit) {
		this.droit = droit;
	}

	public HashMap<String, Trap> getTrap() {
		return this.trap;
	}

	public void afficherAbo() {
		Set<? extends Map.Entry<String, Trap>> entries = trap.entrySet();
		// on affiche la liste des managers abonnés à l'information
		for (Map.Entry<String, Trap> entry : entries) {
			System.out.println(entry.getKey());
		}
	}

	public void setTrap(String manager, Trap t) {
		this.trap.put(manager, t);
	}

	public static void main(String[] args) { // tests de fonctionalite
		System.out.println("Hello world !");
	}

}