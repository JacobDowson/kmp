import java.rmi.server.UnicastRemoteObject; // Makes the object remotely accessible
import java.rmi.RemoteException; // Required for RMI remote methods
import java.util.HashMap;
import java.util.Map;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;


// Server-side implementation of the BioService interface
public class BioServiceImpl extends UnicastRemoteObject implements BioService {

    // "database" to store bios (in-memory for now)
    private final Map<String, String> bios = new HashMap<>();

    // constructor sets up the remote object
    protected BioServiceImpl() throws RemoteException {
        super();
    }

    // returns the bio for a given name or a default message
    @Override
    public String getBio(String name) {
        return bios.getOrDefault(name, "No biography found.");
    }

    // adds or updates a bio for a given name
    @Override
    public void updateBio(String name, String bio) {
        bios.put(name, bio);
        System.out.println("Updated bio for: " + name);

        //send JMS message via activeMQ
        try {

            // connect to the activeMQ broker
            ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = factory.createConnection();
            connection.start();

            // create working environment within the connection
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //creating topic
            Topic topic = session.createTopic("BioUpdatesTopic");

            //broadcasting
            MessageProducer producer = session.createProducer(topic);
            TextMessage message = session.createTextMessage(name + " bio was updated.");
            producer.send(message);

            //confirmation
            System.out.println("JMS message sent for: " + name);

            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
