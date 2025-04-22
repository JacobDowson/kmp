import java.rmi.registry.LocateRegistry; // Used to create or access the RMI registry
import java.rmi.registry.Registry;       // RMI registry interface

// Main class to launch the RMI server
public class BioServerMain {
    public static void main(String[] args) {
        try {
            // create remote service object (includes bios in memory)
            BioService service = new BioServiceImpl();

            // start the RMI registry on port 1099 (standard port)
            Registry registry = LocateRegistry.createRegistry(1099);

            // register the service object under the name "BioService"
            registry.rebind("BioService", service);

            System.out.println("BioService RMI server is running...");
        } catch (Exception e) {
            e.printStackTrace(); // print error if server setup fails
        }
    }
}
