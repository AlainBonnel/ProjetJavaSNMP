import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class main {
	public static void main(String args[]) throws RemoteException, MalformedURLException {
		AgentImp a1 = new AgentImp("Agent1", "192.168.12.51");
		AgentImp a2 = new AgentImp("Agent2", "192.168.12.52");
		AgentImp a3 = new AgentImp("Agent3", "192.168.12.53");

		Manager m1 = new Manager("Manager1", "192.168.12.11");

		Thread t1 = new Thread(a1);
		Thread t2 = new Thread(a2);
		Thread t3 = new Thread(a3);

		Thread t4 = new Thread(m1);

		t1.start();
		t2.start();
		t3.start();
		t4.start();

	}
}
