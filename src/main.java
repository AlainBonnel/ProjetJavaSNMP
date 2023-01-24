import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class main {

	public static void main(String args[]) throws Exception, MalformedURLException {
		// D�marre le rmiregistry
		LocateRegistry.createRegistry(1099);
		// Cr�e et installe un gestionnaire de s�curit�
		// inutile si on ne t�l�charge pas les classes des stubs et parametres
		// System.setSecurityManager(new RMISecurityManager());
		AgentImp a = new AgentImp("AgentA", "192.168.10.1");
		Naming.rebind("A", a);
		System.out.println("A d�clar� aupr�s du serveur de noms");
	}
}
