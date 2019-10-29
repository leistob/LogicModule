package thread;

import helperclasses.JSONBuilder;
import org.apache.log4j.Logger;
import network.Client;
import ui.GUI;

import java.util.*;
import java.util.concurrent.BlockingQueue;

import static helperclasses.JSONBuilder.*;

/**
 * The main thread that handles all incoming messages with the help of a queue. All messages from the different server
 * threads are directed to this thread.
 */
public class LogicModule extends Thread{

    private static Logger logger = Logger.getLogger(GUI.class);

    private BlockingQueue<JSONBuilder> queue;
    private GUI gui;
    private LightController lightController;
    private Client client;

    public LogicModule(BlockingQueue<JSONBuilder> queue, String address, int port) {
        this.queue = queue;
        this.client = new Client(address, port);
        this.client.startClient();

        logger.info("Logicmodule set up.");
    }
    public LogicModule(BlockingQueue<JSONBuilder> queue, String address, int port, GUI ui) {
        this.queue = queue;
        this.client = new Client(address, port);
        this.client.startClient();
        this.gui = ui;

        logger.info("Logicmodule set up.");
    }

    public void run() {

        String receivedModule;

        while (true) {
            try {

                JSONBuilder json = queue.take();
                logger.info(Thread.currentThread().getName() + " result:" + json.getJSONString());
                receivedModule = json.getModule();

                switch(receivedModule) {
                    case MODULE_GESTURE:
                        interpretGestureCommand(json);
                        break;
                    case MODULE_SMART_PHONE:
                        interpretPhoneCommand(json);
                        break;
                    case MODULE_UI:
                        interpretUICommand(json);
                        break;
                    default:
                        break;
                }

            }catch (Exception e) {
                logger.debug("Wrong json format", e);
            }
        }
    }

    private void interpretGestureCommand(JSONBuilder json) {

        List<String> deflectionList = json.getDeflection();
        List<String> orientationList = json.getOrientation();

        int red = calculateColor_XZ(Double.valueOf(deflectionList.get(0)));
        int green = calculateColor_Y(Double.valueOf(orientationList.get(0)));
        int blue = calculateColor_XZ(Double.valueOf(orientationList.get(2)));
        int[] col_arr = {red, green, blue};

        System.out.println(red);
        gui.setSliderValX(red);
        gui.setSliderValY(green);
        gui.setSliderValZ(blue);
        //lightController.setColor("all", col_arr);
    }

    private void interpretPhoneCommand(JSONBuilder json) {

        switch (json.getMethod()) {
            case CHANGE_ALL_COLORS:
                //break;
            case CHANGE_COLOR_BY_ID:
                //break;
            case FLOW:
                //break;
            default:
                break;
        }
    }

    private void interpretUICommand(JSONBuilder json) {
        lightController.setColor("all", null);
    }

    private static int calculateColor_XZ(double leapVal) {
        //leapVal is normalized between [-1,1], add 1 to range it between [0, 2]
        leapVal += 1;
        //Map the interval [0, 2] to the rgb range [0, 255] and return it
        return (int) (leapVal*127);
    }

    private static int calculateColor_Y(double leapVal) {
        //leapVal in Y axis is normalized between [0,1]
        //Map the interval [0, 1] to the rgb range [0, 255] and return it
        return (int) (leapVal*254);
    }
}
