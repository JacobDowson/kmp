import java.rmi.registry.LocateRegistry; // Used to create or access the RMI registry
import java.rmi.registry.Registry;       // RMI registry interface

// Main class to launch the RMI server
public class BioServerMain {
    public static void main(String[] args) {
        try {
            // Create the remote service object (includes bios in memory)
            BioService service = new BioServiceImpl();

            // Start the RMI registry on port 1099 (standard port)
            Registry registry = LocateRegistry.createRegistry(1099);

            // Register the service object under the name "BioService"
            registry.rebind("BioService", service);

            System.out.println("BioService RMI server is running...");
        } catch (Exception e) {
            e.printStackTrace(); // Print error if server setup fails
        }
    }
}
