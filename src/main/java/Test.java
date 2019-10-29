import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;

import static org.eclipse.paho.client.mqttv3.MqttClient.generateClientId;

public class Test {

    private final static Logger logger = Logger.getLogger(Main.class);
    public static final String address = "127.0.0.1";

    public static void main(String[] args) throws Exception {

        //Responsible for setting up connections with the other modules.

        MqttClient client = new MqttClient(
                "tcp://localhost:1883", "one");

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
            }

            @Override
            public void messageArrived(String t, MqttMessage m) throws Exception {
                System.out.println("Received: " + new String(m.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken t) {
            }
        });

        client.connect();
        client.subscribe("javamagazin/mqttarticle");


        MqttClient client1 = new MqttClient(
                "tcp://localhost:1883", "two");

        client1.connect();

        MqttMessage message = new MqttMessage("Hallo Welt".getBytes());
        client1.publish("javamagazin/mqttarticle", message);
        client1.publish("javamagazin/mqttarticle", message);
        client1.publish("javamagazin/mqttarticle", message);

        //client1.disconnect();
    }
}