import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class BioClient {
    public static void main(String[] args) {
        try {
            // locate the RMI registry running on localhost + port 1099
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // look up the remote object by the name it was registered under
            BioService service = (BioService) registry.lookup("BioService");

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            //loop using boolean to create a basic ui
            while (running) {
                System.out.println("\n=== BioClient Menu ===");
                System.out.println("1. Read Bio");
                System.out.println("2. Add/Update Bio");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        System.out.print("Enter name to read: ");
                        String nameToRead = scanner.nextLine();
                        String bio = service.getBio(nameToRead);
                        System.out.println("Bio: " + bio);
                        break;

                    case "2":
                        System.out.print("Enter name to update: ");
                        String nameToUpdate = scanner.nextLine();
                        System.out.print("Enter new bio: ");
                        String newBio = scanner.nextLine();
                        service.updateBio(nameToUpdate, newBio);
                        System.out.println("Bio updated.");
                        break;

                    case "0":
                        running = false;
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
