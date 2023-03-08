import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Manager implements Runnable {

    private String nom;

    private String mdp;

    private String adresse;

    public Manager(String n, String a, String m) throws RemoteException {
        this.nom = n;
        this.adresse = a;
        this.mdp = m;
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

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void recuperationNomAgent(String commu) {
        try {
            String[] names = Naming.list("rmi://localhost/");
            for (String name : names) {
                Agent snmpService = (Agent) Naming.lookup(name);
                System.out.println("Found SNMP agent: " + snmpService.get("SysName", commu));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String recuperationInfo(String chaine, Agent a, String value, String commu) {
        String message = "";
        try {
            if (chaine.equalsIgnoreCase("get")) {
                // Appel d'une methode sur l'objet distant
                // message = a.getNom();
                System.out.println("get choisi, à modifier");
            } else if (chaine.equalsIgnoreCase("set")) {
                // Appel d'une methode sur l'objet distant
                // message = a.getAdresse();
                System.out.println("set choisi, à modifier");
            } else if (chaine.equalsIgnoreCase("getnext")) {
                message = a.getNext(value, commu);
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
            recuperationNomAgent("mdpR");
            String chaine = "";
            String value = "";
            String commu = "";
            Agent agent = null;
            do {
                System.out.println("Voulez vous affichez la liste des agents ? O/N");
                chaine = scanner.nextLine();
                if (chaine.equalsIgnoreCase("O")) {
                    recuperationNomAgent("mdpR");
                }
                System.out.println("Selectionner un agent (taper FIN pour quitter): ");
                chaine = scanner.nextLine();
                if (!chaine.equalsIgnoreCase("FIN")) {
                    agent = (Agent) Naming.lookup(chaine);
                    do {
                        System.out
                                .println("Quelle action voulez vous faire : get , set , getnext");
                        chaine = scanner.nextLine();
                        System.out.println("Pour quel élément ? :");
                        value = scanner.nextLine();
                        System.out.println("Veuillez saisir votre communauté :");
                        commu = scanner.nextLine();
                        System.out.println(recuperationInfo(chaine, agent, value, commu));
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
        Manager m1 = new Manager("Manager1", "192.168.12.11", "test1");

        Thread t = new Thread(m1);

        t.start();
    }
}
