import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class Manager implements Runnable {
    private String nom;
    private String adresse;

    public Manager(String n, String a) throws RemoteException {
        this.nom = n;
        this.adresse = a;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
<<<<<<< HEAD
    
    public static void main(String args[]) {
    	try {
    	
    	 // R�cup�ration d'un proxy sur l'objet
    	 Agent c1 = (Agent) Naming.lookup("Agent1");
    	 Agent c2 = (Agent) Naming.lookup("Agent2");
    	 Agent c3 = (Agent) Naming.lookup("Agent3");
    	 // Appel d'une m�thode sur l'objet distant
    	 String message = c1.getNom();
    	 System.out.println(message);
    	 message = c2.getNom();
    	 System.out.println(message);
    	 message = c3.getNom();
    	 System.out.println(message);
    	} catch (Exception e) {
    	 e.printStackTrace();
    	}
    	}  

	public void unreferenced() {
	 // utilis� pour lib�rer des ressources (threads, fichiers�)
	 }
=======

    public void unreferenced() {
        // utilis� pour lib�rer des ressources (threads, fichiers�)
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Selectionner un agent(taper FIN pour quitter) : ");
            String chaine = "";
            while (!chaine.equalsIgnoreCase("FIN")) {
                // lecture clavier
                chaine = scanner.nextLine();
                // Recuperation d'un proxy sur l'objet
                Agent c = (Agent) Naming.lookup(chaine);
                // Appel d'une methode sur l'objet distant
                String message = c.getNom();
                System.out.println(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
>>>>>>> 03a69b0 (modif manager en thread v1)
}
