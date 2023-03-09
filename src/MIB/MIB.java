package MIB;

import java.io.*;
import java.util.HashMap;

public class MIB {
	private HashMap map;
	private String Chemin;
	private static final int TAILLEMAX = 5;

	public MIB(String chemin) throws IOException {

		map = new HashMap<String, Information>();
		Chemin = chemin;
		try {
			FileReader fileReader = new FileReader(Chemin);
			BufferedReader reader = new BufferedReader(fileReader);

			String line = reader.readLine();
			if (line.equalsIgnoreCase("MIB PROJECT SNMP")) {
				int taille = 0;
				line = reader.readLine();
				do {
					String[] tab = line.split(",");

					// on rempli la hashmap, ici on regarde le nombre d'éléments présent sur une
					// ligne
					// si les communautés ne sont pas spécifié c'est que personne n'a accès à
					// l'information
					// donc on attribue la valeur null
					if (tab.length < TAILLEMAX) {
						taille = TAILLEMAX - tab.length;
						switch (taille) {
							case 1:
								map.put(tab[0], new Information(tab[1], tab[2], tab[3], ""));
								break;
							case 2:
								map.put(tab[0], new Information(tab[1], tab[2], "", ""));
								break;
							default:
								break;
						}
					} else {
						map.put(tab[0], new Information(tab[1], tab[2], tab[3], tab[4]));
					}
					line = reader.readLine();
				} while (line != null);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap getHashmap() {
		return this.map;
	}

	public void setHashmap(HashMap hashmap) {
		this.map = hashmap;
	}

	public Information getInfo(String key) {
		return (Information) map.get(key);
	}

	public boolean lectureAutorise(String key, String commu) {
		Information i = getInfo(key);
		if (i.getDroit()[0].equals(commu)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean ecritureAutorise(String key, String commu) {
		Information i = getInfo(key);
		if (i.getDroit()[1].equals(commu)) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) throws IOException { // tests de fonctionalite
		MIB m = new MIB(
				"/Users/alainbonnel/Documents/UPSSITECH/S8/Java/ProjetSNMP/ProjetJavaSNMP/src/MIB/mibagent1.txt");
	}
}