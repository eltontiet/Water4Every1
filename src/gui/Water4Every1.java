package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Water4Every1 extends JFrame implements ActionListener {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 500;

    private JPanel servicesPanel;
    private JPanel userPanel;
    private JPanel nextTimePanel;
    private JPanel progressPanel;
    private JPanel drinkButtonPanel;
    private JPanel editServicesPanel;

    private JButton drinkButton;
    private JButton editBottleButton;
    private JButton editScheduleButton;

    // EFFECTS: constructs the Water4Every1 interactive application
    public Water4Every1(){
        super("Water4Every1");
        initializeGraphics();
    }

    // EFFECTS: initializes the main frame and panels of the GUI
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        displayServicesPanels();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: initializes te panels and services within the GUI
    private void displayServicesPanels() {
        servicesPanel = new JPanel();
        servicesPanel.setLayout(new GridLayout(0,1));
        servicesPanel.setLocation(0,0);
        servicesPanel.setSize(350,HEIGHT);
        displayUserPanel();
        displayNextTimePanel();
        displayProgressPanel();
        displayDrinkButtonPanel();
        displayEditServicesPanel();
        servicesPanel.setVisible(true);
        add(servicesPanel, BorderLayout.NORTH);
    }

    // EFFECTS: displays the Welcome "USER_NAME" panel of GUI
    private void displayUserPanel() {
        userPanel = new JPanel();
        userPanel.setLocation(0,0);
        userPanel.setSize(WIDTH,50);
        JLabel userLabel = new JLabel("Welcome "); //TODO add user name
        userPanel.add(userLabel);
        userPanel.setVisible(true);
        servicesPanel.add(userPanel);
    }

    // EFFECTS: displays the next reminder time panel of the GUI
    private void displayNextTimePanel() {
        nextTimePanel = new JPanel();
        nextTimePanel.setLocation(50, 0);
        nextTimePanel.setSize(WIDTH, 200);
        JLabel nextTimeTextLabel = new JLabel("Time to Next:");
//        JLabel nextTimeLabel = new JLabel(); //TODO add the time and then uncomment below
        nextTimePanel.add(nextTimeTextLabel);
//        nextTimePanel.add(nextTimeLabel);
        nextTimePanel.setVisible(true);
        servicesPanel.add(nextTimePanel);
    }

    private void displayProgressPanel(){
        progressPanel = new JPanel();
        progressPanel.setLocation(250,0);
        progressPanel.setSize(WIDTH,50);
        JLabel progressLabel = new JLabel("Progress:");
        JProgressBar progressBar = new JProgressBar(0);
        progressPanel.add(progressLabel);
        progressPanel.add(progressBar);
        progressPanel.setVisible(true);
        servicesPanel.add(progressPanel);
    }

    private void displayDrinkButtonPanel() {
        drinkButtonPanel = new JPanel();
        drinkButtonPanel.setLocation(0, 300);
        drinkButtonPanel.setSize(WIDTH, 300);
        drinkButton = new JButton("Drink");
        drinkButton.addActionListener(this);

        drinkButtonPanel.add(drinkButton);
        servicesPanel.add(drinkButtonPanel);
    }

    private void displayEditServicesPanel() {
        editServicesPanel = new JPanel();
        editServicesPanel.setLayout(new GridLayout(1,0));
        editServicesPanel.setLocation(0,400);
        editServicesPanel.setSize(WIDTH, 100);

        editBottleButton = new JButton("Edit Bottle");
        editBottleButton.addActionListener(this);
        editServicesPanel.add(editBottleButton);

        editScheduleButton = new JButton("Edit Schedule");
        editScheduleButton.addActionListener(this);
        editServicesPanel.add(editScheduleButton);

        editServicesPanel.setVisible(true);
        servicesPanel.add(editServicesPanel);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button == drinkButton) {
            new DrinkWaterPopup();
        } else if (button == editBottleButton) {
            new EditBottlePopup();
        } else if (button == editScheduleButton) {
            new EditSchedulePopup();
        }
    }
}