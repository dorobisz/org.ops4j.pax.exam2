package org.ops4j.pax.exam.regression.pde;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Notifier extends Remote {

    void send(String msg) throws RemoteException;
}
