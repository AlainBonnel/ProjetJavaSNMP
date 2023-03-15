package manager;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import agent.Agent;
import exceptions.ElementInexistant;
import exceptions.TrapPresent;
import trap.Trap;
import trap.TrapImp;

public class ManagerImp extends UnicastRemoteObject implements Manager, Runnable {

    private String nom;

    private String commu;

    private String adresse;

    private int hierarchie;

    public ManagerImp(String n, String a, String c, int h) throws RemoteException {
        this.nom = n;
        this.adresse = a;
        this.commu = c;
        this.hierarchie = h;
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
        return this.commu;
    }

    public void setCommu(String c) {
        this.commu = c;
    }

    public int getHierarchie() {
        return this.hierarchie;
    }

    public void setHierarchie(int h) {
        this.hierarchie = h;
    }

    public void recuperationNomAgent(String commu) {
        try {
            String[] names = Naming.list("rmi://localhost/");
            for (String name : names) {
                Remote obj = Naming.lookup(name);
                if (obj instanceof Manager) {
                    Manager manager = (Manager) obj;
                    System.out.println("Found Manager: " + manager.getNom());
                } else if (obj instanceof Agent) {
                    Agent agent = (Agent) obj;
                    System.out.println("Found SNMP agent: " + agent.get("SysName", commu, this.nom));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String recuperationInfo(String commande, Agent a, String value, String modif, String commu) {
        String message = "";
        try {
            switch (commande.toLowerCase()) {
                case "get":
                    // Appel d'une methode sur l'objet distant
                    message = a.get(value, commu, this.nom);
                    break;
                case "set":
                    // Appel d'une methode sur l'objet distant
                    message = a.set(value, modif, commu, this.nom);
                    break;
                case "getnext":
                    // Appel d'une methode sur l'objet distant
                    message = a.getNext(value);
                    break;
                case "ajoutertrap":
                    a.ajouterTrap(value, new TrapImp(), this.nom);
                    message = "Trap ajoutée";
                    break;

                default:
                    message = "Choix non valide";
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "Erreur : " + e.getMessage();
        }
        return message;
    }

    @Override
    public String set(String value, String modif, String commu, String manager)
            throws RemoteException, ElementInexistant {
        String message = this.set(value, modif, commu, this.nom);

        return message;
    }

    @Override
    public String get(String value, String commu, String manager) throws RemoteException, ElementInexistant {

        String message = this.get(value, commu, this.nom);

        return message;
    }

    @Override
    public String getNext(String key) throws RemoteException, ElementInexistant {

        String message = this.getNext(key);

        return message;
    }

    @Override
    public void ajouterTrap(String agent, Trap t, String manager)
            throws RemoteException, TrapPresent, ElementInexistant {

        this.ajouterTrap(agent, t, this.nom);
    }

    public String gestionTraitementDemandeAgent(Manager m, boolean accesDistant) {
        Scanner scanner = new Scanner(System.in);
        String chaine = "";
        String value = "";
        Agent agent = null;
        try {
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
                    if (chaine.equalsIgnoreCase("set")) {
                        System.out.println("Saisir la nouvelle valeur :");
                        String modif = scanner.nextLine();
                        if (accesDistant) {
                            System.out
                                    .println(m.recuperationInfo(chaine, agent, value, modif, m.getCommu()));
                        } else {
                            System.out
                                    .println(recuperationInfo(chaine, agent, value, modif, this.commu));
                        }
                    } else {
                        if (accesDistant) {
                            System.out.println(m.recuperationInfo(chaine, agent, value, null, m.getCommu()));
                        } else {
                            System.out.println(recuperationInfo(chaine, agent, value, null, this.commu));
                        }
                    }
                    System.out.println("Changer d'agent ? O/N (taper FIN pour quitter): ");
                    chaine = scanner.nextLine();
                } while (chaine.equalsIgnoreCase("N"));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        scanner.close();
        return chaine;
    }

    @Override
    public void run() {
        try {
            Naming.rebind(this.nom, this);
            Scanner scanner = new Scanner(System.in);
            recuperationNomAgent("mdpR");
            String chaine = "";
            Manager manager = null;
            do {
                System.out.println("Voulez vous sélectionner un agent ou un manager ? ");
                chaine = scanner.nextLine();
                if (chaine.equalsIgnoreCase("agent")) {
                    chaine = gestionTraitementDemandeAgent(null, false);
                } else if (chaine.equalsIgnoreCase("manager")) {
                    System.out.println("Selectionner un manager (taper FIN pour quitter): ");
                    chaine = scanner.nextLine();
                    if (!chaine.equalsIgnoreCase("FIN")) {
                        manager = (Manager) Naming.lookup(chaine);
                        if (this.hierarchie < manager.getHierarchie()) {
                            System.out.println("Vous n'avez pas les droits pour effectuer cette action");
                        } else {
                            chaine = gestionTraitementDemandeAgent(manager, true);
                        }
                    }
                }
            } while (!chaine.equalsIgnoreCase("FIN"));
            scanner.close();
        } catch (Exception e) {
            System.out.println("ici run");
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public static void main(String args[]) throws RemoteException, MalformedURLException {
        ManagerImp m1 = new ManagerImp("Manager3", "192.168.12.12", "mdpRW", 3);

        Thread t = new Thread(m1);

        t.start();
    }
}