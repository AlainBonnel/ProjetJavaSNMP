import java.io.IOException;
import MIB.MIB;
import agent.AgentImp;
import manager.ManagerImp;

public class Application {
    public static void main(String[] args) throws IOException {

        AgentImp a1 = new AgentImp(new MIB(
                "/Users/alainbonnel/Documents/UPSSITECH/S8/Java/ProjetSNMP/ProjetJavaSNMP/src/MIB/mibagent1.txt"));
        AgentImp a2 = new AgentImp(new MIB(
                "/Users/alainbonnel/Documents/UPSSITECH/S8/Java/ProjetSNMP/ProjetJavaSNMP/src/MIB/mibagent2.txt"));
        AgentImp a3 = new AgentImp(new MIB(
                "/Users/alainbonnel/Documents/UPSSITECH/S8/Java/ProjetSNMP/ProjetJavaSNMP/src/MIB/mibagent3.txt"));

        Thread t1 = new Thread(a1);
        Thread t2 = new Thread(a2);
        Thread t3 = new Thread(a3);

        t1.start();
        t2.start();
        t3.start();

        ManagerImp m1 = new ManagerImp("Manager1", "192.168.12.12", "mdpRW", 1);

        Thread t = new Thread(m1);

        t.start();
    }
}
