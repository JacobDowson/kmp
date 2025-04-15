import java.rmi.server.UnicastRemoteObject; // Makes the object remotely accessible
import java.rmi.RemoteException; // Required for RMI remote methods
import java.util.HashMap;
import java.util.Map;

// Server-side implementation of the BioService interface
public class BioServiceImpl extends UnicastRemoteObject implements BioService {

    // In-memory "database" to store bios
    private final Map<String, String> bios = new HashMap<>();

    // Constructor sets up the remote object (via UnicastRemoteObject)
    protected BioServiceImpl() throws RemoteException {
        super();
    }

    // Returns the bio for a given name or a default message
    @Override
    public String getBio(String name) {
        return bios.getOrDefault(name, "No biography found.");
    }

    // Adds or updates a bio for a given name
    public void updateBio(String name, String bio) {
        bios.put(name, bio);
        System.out.println("Updated bio for: " + name); // Optional logging
    }
}
