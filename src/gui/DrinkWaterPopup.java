package gui;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrinkWaterPopup implements ActionListener {
    private User user;
    private JFrame frame;
    private JPanel panel;
    private BorderLayout layout;
    private JLabel waterDrankLabel;
    private JLabel amountInBottleLabel;
    private JTextField amountToDrinkText;
    private JButton drinkWaterButton;
    private JButton drinkBottleButton;

    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 500;
    private static final int VERTICAL_SPACING = 20;
    private static final Font FONT = new Font(null, Font.PLAIN, 25);
    private static final Dimension BUTTON_DIMENSION = new Dimension (300, 50);

    //creates a new popup
    public DrinkWaterPopup(User user) {
        this.user = user;
        initialize();
    }

    //test
    public static void main(String[] args) {
        User user = new User();
        user.editBottle(1000);
        user.drinkWater(200);
        new DrinkWaterPopup(user);
    }

    private void initialize() {
        initializeFrameAndPanels();
        initializeWaterDrankLabel();
        initializeAmountInBottleLabel();
//        initializeAmountToDrinkText();
        initializeDrinkWaterButton();
        initializeDrinkBottleButton();
    }

    private void initializeFrameAndPanels() {
        frame = new JFrame();

        layout = new BorderLayout();
        layout.setVgap(VERTICAL_SPACING);
        frame.setLayout(layout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);
        frame.setResizable(false);

        panel = new JPanel();
        frame.add(panel);
    }

    private void initializeWaterDrankLabel() {
        waterDrankLabel = new JLabel();
        waterDrankLabel.setFont(FONT);
        waterDrankLabel.setText("Current Amount Drank: " + user.getWaterDrank() + "/4000 mL :D");
        waterDrankLabel.setHorizontalTextPosition(JLabel.CENTER);
        panel.add(waterDrankLabel);
    }

    private void initializeAmountInBottleLabel() {
        amountInBottleLabel = new JLabel();
        amountInBottleLabel.setFont(FONT);
        amountInBottleLabel.setText("Current Amount In Bottle: " +
                user.getBottle().getWaterLevel() + "/" + user.getBottle().getCapacity() + " mL NOICE");
        amountInBottleLabel.setHorizontalTextPosition(JLabel.CENTER);
        panel.add(amountInBottleLabel);
    }

    private void initializeAmountToDrinkText() {
        //TODO: figure out why this isn't showing up
        amountToDrinkText = new JTextField(10);
        amountToDrinkText.setText("How much water did you drink?");
        amountToDrinkText.setPreferredSize(new Dimension(600, 30));
        amountToDrinkText.setVisible(true);
        panel.add(amountToDrinkText);
        amountToDrinkText.addActionListener(this);
    }

    private void initializeDrinkWaterButton() {
        drinkWaterButton = new JButton("Drink Water!");
        drinkWaterButton.setFocusable(false);
        drinkWaterButton.addActionListener(this);
        panel.add(drinkWaterButton);
        drinkWaterButton.setPreferredSize(BUTTON_DIMENSION);
    }

    private void initializeDrinkBottleButton() {
        drinkBottleButton = new JButton("Finished Water Bottle!");
        drinkBottleButton.setFocusable(false);
        drinkBottleButton.addActionListener(this);
        panel.add(drinkBottleButton);
        drinkBottleButton.setPreferredSize(BUTTON_DIMENSION);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == drinkWaterButton) {
            //TODO: user.drinkWater(amountToDrinkText.getText());
            //TODO: update display
        } else if (e.getSource() == drinkBottleButton) {
            user.drinkWater(user.getBottle().getCapacity());
            //TODO: update display
            initializeWaterDrankLabel();
        }
    }

}
