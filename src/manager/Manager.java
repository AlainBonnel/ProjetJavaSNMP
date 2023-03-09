package manager;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

import agent.Agent;
import trap.TrapImp;

public class Manager implements Runnable {

    private String nom;

    private String commu;

    private String adresse;

    public Manager(String n, String a, String m) throws RemoteException {
        this.nom = n;
        this.adresse = a;
        this.commu = m;
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

    public String getCommu() {
        return commu;
    }

    public void setCommu(String mdp) {
        this.commu = mdp;
    }

    public void recuperationNomAgent(String commu) {
        try {
            String[] names = Naming.list("rmi://localhost/");
            for (String name : names) {
                Agent snmpService = (Agent) Naming.lookup(name);
                System.out.println("Found SNMP agent: " + snmpService.get("SysName", commu, this.nom));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String recuperationInfo(String chaine, Agent a, String value, String modif, String commu) {
        String message = "";
        try {
            if (chaine.equalsIgnoreCase("get")) {
                // Appel d'une methode sur l'objet distant
                message = a.get(value, commu, this.nom);
            } else if (chaine.equalsIgnoreCase("set")) {
                // Appel d'une methode sur l'objet distant
                message = a.set(value, modif, commu, this.nom);
            } else if (chaine.equalsIgnoreCase("getnext")) {
                // Appel d'une methode sur l'objet distant
                message = a.getNext(value);
            } else if (chaine.equalsIgnoreCase("ajouterTrap")) {
                // Appel d'une methode sur l'objet distant
                a.ajouterTrap(value, new TrapImp(), this.nom);
                message = "Trap ajoutée";
            } else {
                message = "Choix non valide";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ici recuperationInfo");
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
                // System.out.println("Voulez vous affichez la liste des agents ? O/N");
                // chaine = scanner.nextLine();
                // if (chaine.equalsIgnoreCase("O")) {
                // recuperationNomAgent("mdpR");
                // }
                System.out.println("Selectionner un agent (taper FIN pour quitter): ");
                chaine = scanner.nextLine();
                if (!chaine.equalsIgnoreCase("FIN")) {
                    agent = (Agent) Naming.lookup(chaine);
                    do {
                        System.out
                                .println("Quelle action voulez vous faire : get , set , getnext, ajouterTrap ?");
                        chaine = scanner.nextLine();
                        System.out.println("Pour quel élément ? :");
                        value = scanner.nextLine();
                        System.out.println("Veuillez saisir votre communauté :");
                        commu = scanner.nextLine();
                        if (chaine.equalsIgnoreCase("set")) {
                            System.out.println("Saisir la nouvelle valeur :");
                            String modif = scanner.nextLine();
                            System.out
                                    .println(recuperationInfo(chaine, agent, value, modif, commu));
                        } else {
                            System.out.println(recuperationInfo(chaine, agent, value, null, commu));
                        }
                        System.out.println("Changer d'agent ? O/N (taper FIN pour quitter): ");
                        chaine = scanner.nextLine();
                    } while (chaine.equalsIgnoreCase("N"));
                }
            } while (!chaine.equalsIgnoreCase("FIN"));
            scanner.close();
        } catch (Exception e) {
            System.out.println("ici run");
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public static void main(String args[]) throws RemoteException, MalformedURLException {
        Manager m1 = new Manager("Manager2", "192.168.12.12", "test1");

        Thread t = new Thread(m1);

        t.start();
    }
}