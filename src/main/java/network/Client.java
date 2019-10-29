package network;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Responsible for the communication between the parse&logic module and the 3D animation program.
 * Acts as a TCP socket client that sends the processed commands to the 3D animation program.
 */
public class Client {

    private static Logger logger = Logger.getLogger(Client.class);

    //Default values for ip address and port
    private String address = "127.0.0.1";
    private int port = 5556;
    private int socketTimeout = 50;

    private Socket socket;
    private PrintWriter outputStream;
    //private BufferedReader bufferedReader;

    public Client() {}

    public Client(int port) {
        this.port = port;
    }

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Initializes a connection to the animation server.
     */
    public void startClient() {
        logger.debug("Trying to initialize socket client");

        try {
            socket = new Socket(address, port);
            socket.setSoTimeout(socketTimeout);

            outputStream = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            //bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            logger.debug("Connected to server " + address + "/" + port);
        } catch (IOException ioe) {
            logger.debug("Could not connect to server " + address + "/" + port);
            logger.debug("Attempting to reconnect when messages are sent.");
        }
    }

    /**
     * Sends the given command via the socket connection to the animation program.
     * @param command String to send to server
     */
    public void sendCommand(String command) {
        try {
            if(!(outputStream==null) & !outputStream.checkError()) {
                outputStream.write(command + "\n");
                outputStream.flush();
            } else {
                logger.info("Could not send string, trying to reconnect.");
                startClient();
            }
        } catch (Exception ioe) {
            logger.info("Could not send string, trying to reconnect.");
            startClient();
        }
    }

    /**
     * Sends the given command and waits the defined amount of milliseconds for an answer.
     * @param command String to send to server
     * @param millis duration to wait for answer
     * @return received answer from server
     */
    public String sendCommand(String command, int millis) {
        String answer = null;
        try{

            int timeout = socket.getSoTimeout();

            socket.setSoTimeout(millis);

            sendCommand(command);
            answer = readAnswer();

            socket.setSoTimeout(timeout);

        } catch (SocketException se) {
            logger.debug("Could not change socket timeout setting to " + millis);
        }

        return answer;
    }

    /**
     * Helper method that tries to read in the incoming answer from the server.
     * @return waits the defined time intervall for an answer from the server and gives it back if received.
     */
    private String readAnswer() {
        logger.info("Trying to read answer");

        String received = null;
        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            received = bufferedReader.readLine();

        } catch (SocketTimeoutException e) {
            logger.info("No response from serversocket received in time.", e);
        } catch (IOException ioe) {
            logger.info("Error in socket connection", ioe);
        }
        return received;
    }

    /**
     * Breaks up the connection to the server.
     */
    public void stopConnection() {
        String logMessage = String.format("Trying to stop connection to server %s:%d", this.address, this.port);
        logger.info(logMessage);
    }
}
