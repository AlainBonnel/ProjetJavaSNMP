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

    public void recuperationNomAgent() {
        try {
            String[] names = Naming.list("rmi://localhost/");
            for (String name : names) {
                Agent snmpService = (Agent) Naming.lookup(name);
                System.out.println("Found SNMP agent: " + snmpService.getNom());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            recuperationNomAgent();
            String chaine = "";
            Agent agent = null;
            do {
                System.out.println("Voulez vous affichez la liste des agents ? O/N");
                chaine = scanner.nextLine();
                if (chaine.equalsIgnoreCase("O")) {
                    recuperationNomAgent();
                }
                System.out.println("Selectionner un agent (taper FIN pour quitter): ");
                chaine = scanner.nextLine();
                if (!chaine.equalsIgnoreCase("FIN")) {
                    agent = (Agent) Naming.lookup(chaine);
                    do {
                        System.out
                                .println("Choisir l'information que à laquelle vous voulez accéder (nom or adresse): ");
                        chaine = scanner.nextLine();
                        System.out.println(recuperationInfo(chaine, agent));
                        System.out.println("Changer d'agent ? O/N (taper FIN pour quitter): ");
                        chaine = scanner.nextLine();
                    } while (chaine.equalsIgnoreCase("N"));
                }
            } while (!chaine.equalsIgnoreCase("FIN"));
            scanner.close();
        } catch (

        Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }

    }

    public static void main(String args[]) throws RemoteException, MalformedURLException {
        Manager m1 = new Manager("Manager1", "192.168.12.11");

        Thread t = new Thread(m1);

        t.start();
    }
}
