package gui;

import javax.swing.*;
import java.awt.*;

public class Notification extends JFrame {
    public static final int WIDTH = 350;
    public static final int HEIGHT = 75;
    private static final Font FONT_LARGE = new Font(null, Font.PLAIN, 25);

    private JPanel notificationPanel;

    //EFFECTS: constructs a small notification pop up that reminds user to drink water
    public Notification() {
        super("Notification");
        initializeGraphics();
    }

    //test
    public static void main(String[] args) {
        new Notification();
    }
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        displayNotificationPanel();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void displayNotificationPanel() {
        notificationPanel = new JPanel();
        JLabel notificationText1 = new JLabel("REMINDER:" + "\nDRINK WATER");
        notificationText1.setFont(FONT_LARGE);
        notificationPanel.add(notificationText1);
        notificationPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        notificationPanel.setLocation(0, HEIGHT/2);
        notificationPanel.setVisible(true);
        add(notificationPanel, BorderLayout.CENTER);
    }


}
