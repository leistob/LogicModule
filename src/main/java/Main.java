import helper.PropertyHelper;
import helperclasses.JSONBuilder;
import network.Server;
import org.apache.log4j.Logger;
import thread.LogicModule;
import ui.GUI;

import javax.swing.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {

    private final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws Exception{

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException |
                InstantiationException | IllegalAccessException e) {
            logger.debug("Could not force system look and feel.");
        }

        String currentIP = PropertyHelper.readProperty(PropertyHelper.PROPERTY_GENERAL_IP);
        int currentPort = Integer.parseInt(PropertyHelper.readProperty(PropertyHelper.PROPERTY_GENERAL_PORT));

        String clientIP = PropertyHelper.readProperty(PropertyHelper.PROPERTY_ANIMATION_IP);
        int clientPort = Integer.parseInt(PropertyHelper.readProperty(PropertyHelper.PROPERTY_ANIMATION_PORT));

        GUI gui = new GUI();

        //Responsible for inter-thread communication
        final BlockingQueue<JSONBuilder> queue = new LinkedBlockingDeque<>(1000);

        //Interprets incoming messages from the other threads, filters them and sends messages to the 3D animation.
        Thread logicModule = new LogicModule(queue, clientIP, clientPort, gui);
        logicModule.start();

        //Responsible for setting up connections with the other modules.
        Thread thread = new Thread(() -> {
            logger.info("Server thread Running");
            Server server = new Server(currentIP, currentPort, queue);
            server.startServer();
        });
        thread.start();


        //MqttClient client = new MqttClient("127.0.0.1", generateClientId());
    }
}