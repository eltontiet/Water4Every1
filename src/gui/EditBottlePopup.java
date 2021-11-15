package gui;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class EditBottlePopup implements ActionListener {
    private User user;
    private JFrame frame;
    private JPanel panel;
    private BorderLayout layout;
    private JTextField capacityText;
    private JButton changeButton;

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 200;
    private static final int VERTICAL_SPACING = 20;
    private static final Font FONT = new Font(null, Font.PLAIN, 25);
    private static final Dimension BUTTON_DIMENSION = new Dimension (200, 40);

    //creates a new popup
    public EditBottlePopup(User user) {
        this.user = user;
        initialize();
    }

    //test
    public static void main(String[] args) {
        User user = new User("Melissa");
        user.editBottle(1000);
        user.drinkWater(200);
        new EditBottlePopup(user);
    }

    private void initialize() {
        initializeFrameAndPanels();
        initializeCapacityText();
        initializeChangeButton();
        frame.setVisible(true);
    }

    private void initializeFrameAndPanels() {
        frame = new JFrame();

        layout = new BorderLayout();
        layout.setVgap(VERTICAL_SPACING);
        frame.setLayout(layout);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        panel = new JPanel();
        frame.add(panel);
    }

    private void initializeCapacityText() {
        capacityText = new JTextField(15);
        Integer i = user.getBottle().getCapacity();
        capacityText.setText(i.toString());
        panel.add(capacityText);
        capacityText.addActionListener(this);
    }

    private void initializeChangeButton() {
        changeButton = new JButton("Change Bottle Size");
        changeButton.setFocusable(false);
        changeButton.addActionListener(this);
        panel.add(changeButton);
        changeButton.setPreferredSize(BUTTON_DIMENSION);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == changeButton) {
            Integer i = parseInt(capacityText.getText());
            user.getBottle().changeCapacity(i);
            panel.removeAll();
            initialize();
        }
    }
}
