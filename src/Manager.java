import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.net.MalformedURLException;

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

    public void unreferenced() {
        // utilise pour liberer des ressources (threads, fichiers)
    }

    public String recuperationInfo(String chaine, Agent a) {
        String message = "";
        try {
            if (chaine.equalsIgnoreCase("nom")) {
                // Appel d'une methode sur l'objet distant
                message = a.getNom();
            } else if (chaine.equalsIgnoreCase("adresse")) {
                // Appel d'une methode sur l'objet distant
                message = a.getAdresse();
            } else {
                message = "Choix non valide";
            }

        } catch (Exception e) {
            message = "Erreur : " + e.getMessage();
        }
        return message;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Selectionner un agent (type FIN to quit): ");
            String chaine = "";
            Agent agent = null;
            chaine = scanner.nextLine();
            agent = (Agent) Naming.lookup(chaine);
            while (!chaine.equalsIgnoreCase("FIN")) {
                if (chaine.equalsIgnoreCase("O")) {
                    System.out.println("Selectionner un agent : ");
                    chaine = scanner.nextLine();
                    agent = (Agent) Naming.lookup(chaine);
                }
                System.out.println("Choisir l'information que à laquelle vous voulez accéder (nom or adresse): ");
                chaine = scanner.nextLine();
                System.out.println(recuperationInfo(chaine, agent));
                System.out.println("Changer d'agent ? O/N (type FIN to quit): ");
                chaine = scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public static void main(String args[]) throws RemoteException, MalformedURLException {
        Manager m1 = new Manager("Manager1", "192.168.12.11");

        Thread t = new Thread(m1);

        t.start();
    }
}
