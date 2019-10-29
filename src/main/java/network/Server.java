package network;

import helperclasses.JSONBuilder;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Server {

    private static Logger logger = Logger.getLogger(Server.class);

    //Default values for ip address and port
    private String address = "127.0.0.1";
    private int port = 5555;

    private BlockingQueue<JSONBuilder> queue;

    public Server() {
    }

    public Server(int port) {
        this.port = port;
    }

    public Server(String address, int port, BlockingQueue<JSONBuilder> queue) {
        this.address = address;
        this.port = port;
        this.queue = queue;
    }

    /**
     * This method starts a new server and is responsible for handling incoming connection attempts.
     * For every new client, a new thread is started using the class ClientHandler.
     */
    public void startServer() {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket();
            if (address != null)
                serverSocket.bind(new InetSocketAddress(address, port));
            else
                serverSocket.bind(new InetSocketAddress(port));
            if (serverSocket.isBound()) {
                logger.info("Server socket started on "
                        + serverSocket.getInetAddress() + ":"
                        + serverSocket.getLocalPort());
            } else {
                logger.debug("Binding server socket on "
                        + serverSocket.getInetAddress()
                        + ":" + serverSocket.getLocalPort()
                        + " failed");
            }
        } catch (IOException ioe) {
            logger.debug("Could not create server, stopping application. Address in use");
            System.exit(0);
        }

        while (true) {
            Socket s = null;

            try {
                s = serverSocket.accept();
                logger.debug("A new client is connected : " + s);

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                logger.info("Assigning new thread for this client");

                Thread clientHandler = new ClientHandler(s, dis, dos, queue);
                clientHandler.start();

            } catch (Exception e) {
                try {
                    s.close();
                } catch (NullPointerException npe) {
                    logger.debug("Could not find socket to close", npe);
                } catch (IOException ioe) {
                    logger.debug("Failed to close socket", ioe);
                }
                logger.debug("Failed to connect to socket", e);
            }
        }
    }
}