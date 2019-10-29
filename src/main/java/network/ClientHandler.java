package network;

import helperclasses.JSONBuilder;
import org.apache.log4j.Logger;
import org.json.JSONException;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * Class responsible for handling the connection to the other modules (gesture, voice, 3D mouse).
 * With every connection from this modules a new ClientHandler object is created, that is only responsible for this
 * connection. It receives JSON-strings from these modules and checks them for formatting issues. If the json passes
 * the test, the ClientHandler hands over the JSON to the main thread (via a BlockingQueue), that handles the further
 * analysis of the JSON and the interaction with the 3D animation module.
 */
public class ClientHandler extends Thread {

    private static Logger logger = Logger.getLogger(ClientHandler.class);

    public static final String STATUS_DISCONNECTED = "DISCONNECTED";
    public static final String STATUS_CONNECTED = "CONNECTED";
    public static final String STATUS_ALREADY_IN_USE = "IN USE";
    public static final String STATUS_CRITICAL_FAILURE = "ERROR";

    private final InputStream inputStream;
    private final PrintWriter outputStream;
    private final Socket s;
    private final BlockingQueue<JSONBuilder> queue;

    ClientHandler(Socket s, InputStream inputStream, DataOutputStream outputStream,
                         BlockingQueue<JSONBuilder> queue) {
        this.s = s;
        this.inputStream = inputStream;
        this.outputStream = new PrintWriter(outputStream, true);
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("Receiving data from " + s.getInetAddress().getHostAddress() + ":" + s.getPort());
        String receivedString;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        //TODO catch init message

        while (true) {
            try {
                while ((receivedString = reader.readLine()) != null) {

                    JSONBuilder json;
                    try {
                        json = new JSONBuilder(receivedString);
                        //TODO OFFER instead of put
                        queue.put(json);
                    } catch (JSONException | InterruptedException je) {
                        logger.debug("Invalid JSON string", je);
                        //continue;
                    }
                    //answer = "TEST";
                    //outputStream.write(answer + "\n");
                    //outputStream.flush();
                }
            } catch (IOException e) {
                logger.debug("Failed to get data, closing thread.");
                break;
            }
        }
        logger.info("Shutting down thread: "+ s.getInetAddress().getHostAddress() + ":" + s.getPort());
        //gui.setModuleConnected(this.module, false);
    }
}
