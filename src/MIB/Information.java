package MIB;

public class Information {
	private String nom;
	private String valeur;
	private String[] droit;

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

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public String[] getDroit() {
		return this.droit;
	}

	public void setDroit(String[] droit) {
		this.droit = droit;
	}

	// public String getNext(String i) { // retourne le parametre suivant i
	// String r = "null";
	// if (list.contains(i)) {
	// for (int a = 0; a < list.size() - 1; a++) { // -1 car le dernier element
	// d'une chaine ne peut avoir de
	// // suivant
	// if (list.get(a) == i) {
	// r = list.get(a + 1).toString();
	// }
	// }
	// }
	// return r;
	// }

	public static void main(String[] args) { // tests de fonctionalite
		System.out.println("Hello world !");
		String key = "test comme ça . oui bonjour . dzaezae . 2";
		System.out.println(key);
		String[] tab = key.split("\\.");
		System.out.println(tab.length);
		tab[tab.length - 1] = Integer.toString((Integer.parseInt(tab[tab.length - 1]) + 1));
		key = String.join(".", tab);
		System.out.println(key);
	}

}