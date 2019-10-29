package ui;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static helperclasses.JSONBuilder.*;

public class GUI extends JFrame implements ChangeListener {

    private static Logger logger = Logger.getLogger(GUI.class);

    private static final String iconPath = "/Logo_Healthineers.png";
    private static final String frameName = "Parse&Logic module";
    private java.util.List<String> colors = Arrays.asList(COLOR_RED, COLOR_GREEN, COLOR_BLUE);

    private static final Dimension labelDim = new Dimension(120, 50);

    private ArrayList<JSlider> sliders;
    private JTextField debugField;
    private JLabel colorLabel;

    public GUI() {
        this.setTitle(frameName);
        this.setLayout(new BorderLayout());
        this.setBounds(350, 300, 550, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIconImage();
        this.setLayout(new BorderLayout());

        this.add(createControlPanel(), BorderLayout.CENTER);
        this.add(new JPanel(), BorderLayout.NORTH);

        debugField = new JTextField();
        debugField.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray));
        debugField.setFocusable(false);
        debugField.setEditable(false);
        debugField.setBackground(Color.white);
        this.add(debugField, BorderLayout.SOUTH);

        centerLocation();

        this.setDebugText("Waiting for connections..");
        this.setVisible(true);
        logger.info("GUI built.");
    }

    private void setIconImage() {
        try{
            InputStream imageInputStream = this.getClass().getResourceAsStream(iconPath);
            BufferedImage bufferedImage = ImageIO.read(imageInputStream);
            this.setIconImage(bufferedImage);
        } catch (Exception ioe) {
            logger.debug("No icon image found", ioe);
        }
    }

    private void centerLocation() {
        Dimension frameSize = this.getSize();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int xCoordinate = (int) (screenSize.getWidth() / 2 - frameSize.getWidth() / 2);
        int yCoordinate = (int) (screenSize.getHeight() / 2 - frameSize.getHeight() / 2);
        this.setLocation(xCoordinate, yCoordinate);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(createSliderPanel());
        return panel;
    }

    private JPanel createSliderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        sliders = new ArrayList<>();

        for(String color : colors) {
            JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
            slider.addChangeListener(this);
            slider.setBorder(
                    BorderFactory.createEmptyBorder(0,0,10,0));
            Font font = new Font("Serif", Font.ITALIC, 15);
            slider.setFont(font);
            panel.add(slider);
            sliders.add(slider);
        }
        colorLabel = new JLabel(" ");
        colorLabel.setMinimumSize(labelDim);
        colorLabel.setMaximumSize(labelDim);
        colorLabel.setPreferredSize(labelDim);
        colorLabel.setOpaque(true);
        panel.add(colorLabel);

        return panel;
    }

    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////   Public interaction functions    /////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    public void setDebugText(String text) {
        debugField.setText(" " + text);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        // System.out.println("changed");
        java.util.List<Integer> colors = new ArrayList<Integer>();
        for(JSlider slider : sliders) {
            colors.add(slider.getValue());
        }

        colorLabel.setBackground(new Color(colors.get(0), colors.get(1), colors.get(2)));
    }

    public void setSliderValX(int val) {
        sliders.get(0).setValue(val);
    }
    public void setSliderValY(int val) {
        sliders.get(1).setValue(val);
    }
    public void setSliderValZ(int val) {
        sliders.get(2).setValue(val);
    }
}