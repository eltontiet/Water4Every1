package gui;

import model.Bottle;
import model.Schedule;
import model.TimeHandler;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class StartupPopup extends JFrame implements ActionListener {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 140;
    private static final Font FONT_LARGE = new Font(null, Font.PLAIN, 25);

    private JPanel panel;
    private JTextField nameText;
    private JTextField bottleSizeText;
    private JButton submitButton;

    public StartupPopup() {
        super("WELCOME TO HYDRATION");
        initialize();
    }

    //test
    public static void main(String[] args) {
        new StartupPopup();
    }

    private void initialize() {
        initializePanel();
        initializeNameText();
        initializeBottleSizeText();
        initializeSubmitButton();
        setLocationRelativeTo(null);
    }

    private void initializePanel() {
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        add(panel);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
    }

    private void initializeNameText() {
        nameText = new JTextField(25);
        nameText.setText("Enter your name here! :)");
        panel.add(nameText);
        nameText.setVisible(true);
    }

    private void initializeBottleSizeText() {
        bottleSizeText = new JTextField(25);
        bottleSizeText.setText("Enter your water bottle capacity in mL here! :)");
        panel.add(bottleSizeText);
        bottleSizeText.setVisible(true);
    }

    private void initializeSubmitButton() {
        submitButton = new JButton();
        submitButton.setFocusable(false);
        panel.add(submitButton);
        submitButton.addActionListener(this);
        submitButton.setText("Submit");
        submitButton.setPreferredSize(new Dimension(100,40));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String name = nameText.getText();
            Integer i = parseInt(bottleSizeText.getText());
            Schedule schedule = new Schedule();
            TimeHandler timeHandler = new TimeHandler(schedule);

            timeHandler.updateCurrTime();
            timeHandler.updateNextDrink();

            new Water4Every1(new User(name, new Bottle(i), schedule), timeHandler);

            setVisible(false);
            dispose();


        }
    }
}
