import java.rmi.Remote; // Required for all RMI interfaces
import java.rmi.RemoteException; // Required because remote methods can throw this

// Remote interface that defines methods the client can call over the network
public interface BioService extends Remote {
    String getBio(String name) throws RemoteException; // get a bio by name
    void updateBio(String name, String bio) throws RemoteException; // add or update a bio
}
