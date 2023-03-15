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

    private String commu; // communauté du manager, ici je n'ai gérer que 2 communautés une de lecture et
                          // une de lecture/écriture

    private String adresse;

    private int hierarchie; // hierarchie du manager (0 = le plus bas)

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

    // méthode permettant de récupérer la liste des agents et managers disponibles
    // sur le registre RMI
    public void affichageObjetDistant(String commu) {
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

    // méthode permettant de traiter la requete saisie par l'utilisateur
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

    // méthode qui traite les commandes saisies par l'utilisateur
    public String gestionDemande(Manager m, boolean accesDistant) {
        Scanner scanner = new Scanner(System.in);
        String chaine = "";
        String value = "";
        Agent agent = null;
        try {
            System.out.println("Selectionner un agent (taper FIN pour quitter): ");
            chaine = scanner.nextLine();
            // si l'utilisateur ne tape pas FIN, on récupère l'agent
            if (!chaine.equalsIgnoreCase("FIN")) {
                agent = (Agent) Naming.lookup(chaine);
                // do while pour permettre à l'utilisateur de changer d'agent
                do {
                    System.out
                            .println("Quelle action voulez vous faire : get , set , getnext, ajouterTrap ?");
                    chaine = scanner.nextLine();
                    System.out.println("Pour quel élément ? :");
                    value = scanner.nextLine();
                    // si la requete est un set, on demande la modification souhaitée
                    if (chaine.equalsIgnoreCase("set")) {
                        System.out.println("Saisir la nouvelle valeur :");
                        String modif = scanner.nextLine();
                        // si l'accès est distant, on appelle la méthode sur le manager distant
                        if (accesDistant) {
                            System.out
                                    .println(m.recuperationInfo(chaine, agent, value, modif, m.getCommu()));
                        } else {
                            System.out
                                    .println(recuperationInfo(chaine, agent, value, modif, this.commu));
                        }
                    } else {
                        // si l'accès est distant, on appelle la méthode sur le manager distant
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
            affichageObjetDistant("mdpR");
            String chaine = "";
            Manager manager = null;
            do {
                System.out.println("Voulez vous sélectionner un agent ou un manager ? ");
                chaine = scanner.nextLine();
                // si l'utilisateur tape agent, l'accès est local donc accesDistant = false
                if (chaine.equalsIgnoreCase("agent")) {
                    chaine = gestionDemande(null, false);
                    // si l'utilisateur tape manager, l'accès est distant donc accesDistant = true
                    // on demande le manager sur lequel on veut se connecter et effectuer les
                    // commandes
                } else if (chaine.equalsIgnoreCase("manager")) {
                    System.out.println("Selectionner un manager (taper FIN pour quitter): ");
                    chaine = scanner.nextLine();
                    if (!chaine.equalsIgnoreCase("FIN")) {
                        manager = (Manager) Naming.lookup(chaine);
                        // ici on vérifie que le manager controleur a une hierarchie supérieure au
                        // manager distant sur lequel il souhaite se connecter
                        if (this.hierarchie < manager.getHierarchie()) {
                            System.out.println("Vous n'avez pas les droits pour effectuer cette action");
                        } else {
                            chaine = gestionDemande(manager, true);
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
        ManagerImp m1 = new ManagerImp("Manager2", "192.168.12.12", "mdpRW", 2);

        Thread t = new Thread(m1);

        t.start();
    }
}