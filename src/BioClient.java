// Required for connecting to the RMI registry on the server
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BioClient {
    public static void main(String[] args) {
        try {
            // Locate the RMI registry running on localhost + port 1099
            // This is how the client discovers remote services that have been registered
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Look up the remote object by the name it was registered under
            BioService service = (BioService) registry.lookup("BioService");

            // The client adds/updates a bio entry remotely
            System.out.println("Adding bio for mark.jackson...");
            service.updateBio("mark.jackson", "Mark Jackson is a Digital Strategist based in London.");

            // The client then requests the bio that was just added
            System.out.println("Requesting bio we added:");
            System.out.println(service.getBio("mark.jackson"));

            // The client attempts to retrieve a bio for a name that doesn't exist
            System.out.println("\nRequesting Non-existent Bio:");
            System.out.println(service.getBio("nobody"));

            // Catch any exception that occurs during remote communication
            // This includes RemoteException, NotBoundException, or other possible failures
        } catch (Exception e) {
            // Print the full stack trace to help debug issues
            e.printStackTrace();
        }
    }
}
