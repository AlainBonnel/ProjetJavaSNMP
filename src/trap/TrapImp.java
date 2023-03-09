package trap;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TrapImp extends UnicastRemoteObject implements Trap {

    public TrapImp() throws RemoteException {
        super();
    }

    @Override
    public void trap(String message) throws RemoteException {
        System.out.println(message);
    }
}
