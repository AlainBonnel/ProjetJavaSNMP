import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class main {

	public static void main(String args[]) throws Exception,MalformedURLException {
		// Démarre le rmiregistry
		LocateRegistry.createRegistry(1099);
		// Crée et installe un gestionnaire de sécurité
		 // inutile si on ne télécharge pas les classes des stubs et parametres
		// System.setSecurityManager(new RMISecurityManager());
		Agent a = new Agent("AgentA", "192.168.10.1");
		Naming.rebind("A", a);
		System.out.println("A déclaré auprès du serveur de noms");
		 }
		}
