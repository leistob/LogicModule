package thread;

import org.apache.log4j.Logger;

import java.util.*;

public class LightController {

    private static Logger logger = Logger.getLogger(LightController.class);
    private Map<String, List<String>> clients;

    public LightController() {
        clients = new HashMap<>();
        clients.put("led1", Arrays.asList("127.0.0.1", "5555"));
        clients.put("led2", Arrays.asList("127.0.0.1", "5555"));
        clients.put("led3", Arrays.asList("127.0.0.1", "5555"));
        clients.put("led4", Arrays.asList("127.0.0.1", "5555"));

        initialize();
    }

    public void initialize() {
        for (Map.Entry<String, List<String>> stringListEntry : clients.entrySet()) {
            stringListEntry.getKey();
            List<String> address = stringListEntry.getValue();
            String ip = address.get(0);
            int port = Integer.parseInt(address.get(1));
            //TODO: connect
        }
    }

    public void setColor(String module, int[] rgb) {

    }
}
