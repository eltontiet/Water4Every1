package gui;

import model.TimeHandler;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class Water4Every1 extends JFrame implements ActionListener {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 500;
    private static final Font FONT_LARGE = new Font(null, Font.PLAIN, 25);
    private static final Font FONT_NORM = new Font(null, Font.PLAIN, 18);

    private User user;
    private TimeHandler timeHandler;
    private Timer timer;

    private static LocalDate dateOpened;

    private static JsonReader reader;
    private static JsonWriter writer;

    private JLabel nextTimeLabel;

    private JPanel servicesPanel;
    private JPanel userPanel;
    private JPanel nextTimePanel;
    private JPanel progressPanel;
    private JPanel drinkButtonPanel;
    private JPanel editServicesPanel;

    private JButton drinkButton;
    private JButton editBottleButton;
    private JButton editScheduleButton;

    private JProgressBar progressBar;

    // EFFECTS: constructs the Water4Every1 interactive application
    public Water4Every1(User user, TimeHandler timeHandler) {
        super("Water4Every1");

        this.user = user;
        this.timeHandler = timeHandler;

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Taken from https://stackoverflow.com/questions/16372241/run-function-on-jframe-close/16372860
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                save();
                dispose();
                System.exit(0);
            }
        });

        timer = new Timer(1000, e -> doTick());

        timer.start();

        initializeGraphics();
        doTick();
    }

    // EFFECTS: updates GUI every second, and checks of time is passed
    private void doTick() {
        progressBar.setValue(user.getWaterDrank());
        timeHandler.updateCurrTime();

        nextTimeLabel.setText(timeHandler.getDifference());

        if (timeHandler.hasPassed()) {
            new Notification();
        }

        if (LocalDate.now().isAfter(dateOpened)) {
            user.setWaterDrank(0);
        }

        validate();
        repaint();
    }

    // EFFECTS: saves application to file
    private void save() {
        try {
            writer.open();
            writer.write(user, timeHandler);
            writer.close();
        } catch (FileNotFoundException e) {
            // TODO: idek what to do here. error?
        }
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
//        servicesPanel.setLayout(new BoxLayout());
        servicesPanel.setLocation(0,0);
        servicesPanel.setSize(WIDTH,HEIGHT);
        servicesPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
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
        userPanel.setLocation(10,0);
        userPanel.setSize(WIDTH,40);
        userPanel.setPreferredSize(new Dimension(WIDTH, 40));
        JLabel userLabel = new JLabel("Welcome "); //TODO make user name != null exceptions, then add users name to this
        userLabel.setFont(FONT_LARGE);
        userPanel.add(userLabel);
        userPanel.setVisible(true);
        servicesPanel.add(userPanel);
    }

    // EFFECTS: displays the next reminder time panel of the GUI
    private void displayNextTimePanel() {
        nextTimePanel = new JPanel();
        nextTimePanel.setLocation(0, 60);
        nextTimePanel.setSize(WIDTH, 150);
        nextTimePanel.setPreferredSize(new Dimension(WIDTH, 150));
        JLabel nextTimeTextLabel = new JLabel("Time to Next Drink:");
        nextTimeLabel = new JLabel(); //TODO add the time and then uncomment below
        nextTimeTextLabel.setFont(FONT_NORM);
        nextTimeLabel.setFont(FONT_LARGE);
        nextTimePanel.add(nextTimeTextLabel);
        nextTimePanel.add(nextTimeLabel);
        nextTimePanel.setVisible(true);
        servicesPanel.add(nextTimePanel);
    }

    //EFFECTS: displays progress panel which contains progress panel of the GUI
    private void displayProgressPanel(){
        progressPanel = new JPanel();
        progressPanel.setLayout(new FlowLayout());
        progressPanel.setLocation(0,220);
        progressPanel.setSize(WIDTH,50);
        progressPanel.setPreferredSize(new Dimension(WIDTH, 50));
        JLabel progressLabel = new JLabel("Progress:");
        progressLabel.setFont(FONT_LARGE);
        progressBar = new JProgressBar(0,0,4000);
        progressPanel.add(progressLabel);
        progressPanel.add(progressBar);
        progressPanel.setVisible(true);
        servicesPanel.add(progressPanel);
    }

    // EFFECTS: displays panel which contains a button for drink
    private void displayDrinkButtonPanel() {
        drinkButtonPanel = new JPanel();
        drinkButtonPanel.setLayout(new GridLayout(1,0));
        drinkButtonPanel.setLocation(0, 280);
        drinkButtonPanel.setSize(WIDTH, 100);
        drinkButtonPanel.setPreferredSize(new Dimension(WIDTH, 100));
        drinkButton = new JButton("Drink");
        drinkButton.setFont(FONT_NORM);
        drinkButton.addActionListener(this);

        drinkButtonPanel.add(drinkButton);
        servicesPanel.add(drinkButtonPanel);
    }

    // EFFECTS: displays panel which contains button for editing drink and schedule
    private void displayEditServicesPanel() {
        editServicesPanel = new JPanel();
        editServicesPanel.setLayout(new GridLayout(1,0));
        editServicesPanel.setLocation(0,390);
        editServicesPanel.setSize(WIDTH, 100);
        editServicesPanel.setPreferredSize(new Dimension(WIDTH, 100));

        editBottleButton = new JButton("Edit Bottle");
        editBottleButton.addActionListener(this);
        editBottleButton.setFont(FONT_NORM);
        editServicesPanel.add(editBottleButton);

        editScheduleButton = new JButton("Edit Schedule");
        editScheduleButton.addActionListener(this);
        editScheduleButton.setFont(FONT_NORM);
        editServicesPanel.add(editScheduleButton);

        editServicesPanel.setVisible(true);
        servicesPanel.add(editServicesPanel);
    }

    // EFFECTS: buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button == drinkButton) {
            new DrinkWaterPopup(user);
            timeHandler.updateNextDrink();
        } else if (button == editBottleButton) {
            new EditBottlePopup(user);
        } else if (button == editScheduleButton) {
            new EditSchedulePopup(user.getSchedule());
        }
    }

    // EFFECTS: loads from save, and opens the app. If save doesn't exist, then open TODO: StartPopup??
    public static void main(String[] args) {
        reader = new JsonReader("./data/save.json");
        writer = new JsonWriter("./data/save.json");
        User user;
        TimeHandler timeHandler;
        try {
            user = reader.readUser();
            timeHandler = reader.readTimeHandler(user.getSchedule());

            if (LocalDate.now().isAfter(reader.readLastDate())) {
                user.setWaterDrank(0);
            }

            dateOpened = LocalDate.now();


            new Water4Every1(user, timeHandler);
        } catch (IOException e) {
            // then file doesnt exist, StartingPopup should open
            new StartupPopup();
        }
    }
}