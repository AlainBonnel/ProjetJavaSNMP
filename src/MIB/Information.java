package MIB;

import java.rmi.RemoteException;

import trap.Trap;

public class Information {
	private String nom; // nom de l'information
	private String valeur; // valeur de l'information
	private String[] droit; // 0 = communauté de lecture, 1 = communauté d'écriture
	private Trap trap; // trap associé à l'information

	public Information(String n, String v, String commuR, String commuRW) {
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

	public void setValeur(String valeur) throws RemoteException {
		this.valeur = valeur;
		// on envoie un trap si l'information a été modifiée
		if (trap != null)
			try {
				trap.trap("L'information " + nom + " a été modifiée");
			} catch (RemoteException e) {
				trap = null;
			}
	}

	public String[] getDroit() {
		return this.droit;
	}

	public void setDroit(String[] droit) {
		this.droit = droit;
	}

	public Trap getTrap() {
		return this.trap;
	}

	public void setTrap(Trap trap) {
		this.trap = trap;
	}

	public static void main(String[] args) { // tests de fonctionalite
		System.out.println("Hello world !");
	}
}