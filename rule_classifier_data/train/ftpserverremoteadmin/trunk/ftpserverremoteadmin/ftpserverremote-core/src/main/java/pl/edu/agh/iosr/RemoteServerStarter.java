package pl.edu.agh.iosr;

import java.io.FileInputStream;
import java.io.InputStream;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import org.apache.ftpserver.config.PropertiesConfiguration;
import org.apache.ftpserver.ftplet.Configuration;
import pl.edu.agh.iosr.ftpserverremote.facade.ServerFacade;
import pl.edu.agh.iosr.ftpserverremote.facade.ServerSideFacadeImpl;

/**
 * Starts and registers in RMIRegistry remote facade to FTPServer
 * 
 * @author Tomasz Jadczyk
 */
public class RemoteServerStarter {

    private static Registry reg = null;
    
    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("Usage:");
            System.out.println("java " + RemoteServerStarter.class.getName() + " PropertyFile");
            System.exit(-1);
        }

        InputStream in = null;
        
        //remove rmiregistry after program shutdown 
        Runtime.getRuntime().addShutdownHook(
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            UnicastRemoteObject.unexportObject(reg, true);
                        } catch (NoSuchObjectException ex) {
                            //Nothing to do
                        }
                    }
                });

        try {
            //try to start rmi registry on localhost, on default port 
            try {
                reg = LocateRegistry.createRegistry(RMIConfig.RMIREGISTRY_PORT);
            } catch(RemoteException ex) {
                reg = LocateRegistry.getRegistry(RMIConfig.RMIREGISTRY_PORT);
            }

            //Configure ApacheFTPServer (from property file)
            in = new FileInputStream(args[0]);
            Configuration config = new PropertiesConfiguration(in);

            ServerFacade remoteFacade = new ServerSideFacadeImpl(config);
            //Register ServerFacade in rmiregistry
            reg.rebind(ServerFacade.SERVERFACADENAME, remoteFacade);
            System.out.println("ServerFacade avaiable in rmiregistry");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
