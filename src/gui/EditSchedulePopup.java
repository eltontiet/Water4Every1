package gui;

import exceptions.GoToSleepException;
import exceptions.NoTimeExistsException;
import exceptions.TimeOverlapException;
import model.Schedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.Map;

// Represents the popup to edit schedule
public class EditSchedulePopup extends JFrame implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    Schedule schedule;
    JList<Map.Entry<LocalTime, LocalTime>> list;
    DefaultListModel<Map.Entry<LocalTime, LocalTime>> model;

    // EFFECTS: initializes schedule, and renders frame
    public EditSchedulePopup(Schedule schedule) {
        super("Edit Schedule");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.schedule = schedule;

        model = new DefaultListModel<>();

        updateModel();

        list = new JList<>(model);

        list.setCellRenderer(new ActivityRenderer());

        JScrollPane jScrollPane = new JScrollPane(list);

        add(jScrollPane);

        renderButtonPanel();

        pack();
        centreOnScreen();
        setVisible(true);
    }

    // EFFECTS: renders button panel
    private void renderButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(WIDTH, 62));

        JPanel space = new JPanel();
        space.setPreferredSize(new Dimension(25, 0));

        JButton addActivityButton = new JButton("Add Activity");
        addActivityButton.setActionCommand("addActivity");
        addActivityButton.addActionListener(this);
        addActivityButton.setPreferredSize(new Dimension(WIDTH/2 - 100, 50));
        buttonPanel.add(addActivityButton, BorderLayout.CENTER);

        buttonPanel.add(Box.createRigidArea(new Dimension(25,0)));

        JButton removeActivityButton = new JButton("Remove Activity");
        removeActivityButton.setActionCommand("removeActivity");
        removeActivityButton.addActionListener(this);
        removeActivityButton.setPreferredSize(new Dimension(WIDTH/2 - 100, 50));
        buttonPanel.add(removeActivityButton);

        add(buttonPanel,BorderLayout.SOUTH);
    }

    // EFFECTS: updates model with schedule
    private void updateModel() {
        model.clear();
        for (Map.Entry<LocalTime,LocalTime> e : schedule.getTimes().entrySet()) {
            model.addElement(e);
        }
    }

    // EFFECTS: removes the selected activity
    private void removeActivity() {
        try {
            schedule.removeTime(list.getSelectedValue().getKey().getHour(), list.getSelectedValue().getKey().getMinute());
        } catch (NoTimeExistsException e) {
            e.printStackTrace();
        }

        model.remove(list.getSelectedIndex());
    }

    // EFFECTS: creates an addActivityFrame
    private void addActivity() {
        new AddActivityFrame();
    }

    // MODIFIES: this
    // EFFECTS:  sets frame position to center of desktop
    private void centreOnScreen() {
        Dimension desktopSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((desktopSize.width - getWidth()) / 2, (desktopSize.height - getHeight()) / 2);
    }

    // EFFECTS: creates or removes an activity from the list
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addActivity")) {
            addActivity();
        } else if (e.getActionCommand().equals("removeActivity")) {
            removeActivity();
        }
    }

    public static void main(String[] args) throws TimeOverlapException, GoToSleepException {
        Schedule schedule = new Schedule();
        schedule.addTime(12,0,13,0);
        schedule.addTime(13,0,14,0);
        schedule.addTime(14,0,15,0);
        schedule.addTime(15,0,16,0);
        schedule.addTime(16,0,17,0);
        schedule.addTime(17,0,18,0);
        schedule.addTime(18,0,19,0);
        schedule.addTime(19,0,20,0);
        schedule.addTime(20,0,21,0);

        new EditSchedulePopup(schedule);
    }



    class ActivityRenderer extends JLabel implements ListCellRenderer<Map.Entry<LocalTime, LocalTime>> {

        @Override
        public Component getListCellRendererComponent(JList<? extends Map.Entry<LocalTime, LocalTime>> list, Map.Entry<LocalTime, LocalTime> value, int index, boolean isSelected, boolean cellHasFocus) {

            setText(getTimeString(value));

            setFont(new Font(null, Font.PLAIN, 20));

            setHorizontalAlignment(SwingConstants.CENTER);

            if (isSelected) {
                setBackground(new Color(0xACCEF7));
            } else {
                setBackground(Color.white);
            }

            setOpaque(true);
            return this;
        }

        private String getTimeString(Map.Entry<LocalTime, LocalTime> value) {

            int startHour = value.getKey().getHour();
            int startMinute = value.getKey().getMinute();

            int endHour = value.getValue().getHour();
            int endMinute = value.getValue().getMinute();

            return getHourString(startHour) + ":" + getMinuteString(startMinute) +
                    " - " + getHourString(endHour) + ":" + getMinuteString(endMinute);
        }

        private String getMinuteString(int minute) {
            if (minute < 10) {
                return "0" + minute;
            } else {
                return Integer.toString(minute);
            }
        }

        private String getHourString(int hour) {
            if (hour < 10) {
                return "0" + hour;
            } else {
                return Integer.toString(hour);
            }
        }
    }

    // Represents an activity frame
    private class AddActivityFrame extends JFrame implements ActionListener {

        JTextField startHourTextField;
        JTextField startMinuteTextField;
        JTextField endHourTextField;
        JTextField endMinuteTextField;

        // EFFECTS: creates the AddActivityFrame
        public AddActivityFrame() {
            super("Add Activity");

            setPreferredSize(new Dimension(50, 200));

            addTextFields();

            JPanel buttonPanel = new JPanel();
            buttonPanel.setPreferredSize(new Dimension(50, 50));
            JButton addActivity = new JButton("Add Activity");
            addActivity.addActionListener(this);
            buttonPanel.add(addActivity);

            add(buttonPanel,BorderLayout.SOUTH);

            setBackground(Color.WHITE);

            pack();
            centreOnScreen();
            setVisible(true);
        }

        // EFFECTS: renders textFields
        private void addTextFields() {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            JLabel label1 = new JLabel("Start hour:");
            startHourTextField = new JTextField();
            startHourTextField.setColumns(3);
            panel.add(label1);
            panel.add(startHourTextField);

            JLabel label2 = new JLabel("Start minute:");
            startMinuteTextField = new JTextField();
            startMinuteTextField.setColumns(3);
            panel.add(label2);
            panel.add(startMinuteTextField);

            JLabel label3 = new JLabel("End hour:");
            endHourTextField = new JTextField();
            endHourTextField.setColumns(3);
            panel.add(label3);
            panel.add(endHourTextField);

            JLabel label4 = new JLabel("End minute:");
            endMinuteTextField = new JTextField();
            endMinuteTextField.setColumns(3);
            panel.add(label4);
            panel.add(endMinuteTextField);

            add(panel);
        }

        // MODIFIES: this
        // EFFECTS:  sets frame position to center of desktop
        private void centreOnScreen() {
            Dimension desktopSize = Toolkit.getDefaultToolkit().getScreenSize();
            setLocation((desktopSize.width - getWidth()) / 2, (desktopSize.height - getHeight()) / 2);
        }

        // EFFECTS: adds the activity to the schedule, if not possible, open an error screen
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                createActivity();
                setVisible(false);
                dispose();
            } catch(GoToSleepException gtse) {
                JFrame goToSleep = new JFrame();
                goToSleep.setPreferredSize(new Dimension(75,100));
                goToSleep.add(new JLabel("Go To Sleep"));

                JButton confirmButton = new JButton("Okay, I will");
                confirmButton.addActionListener(al -> {
                    goToSleep.setVisible(false);
                    goToSleep.dispose();
                });

                goToSleep.add(confirmButton,BorderLayout.SOUTH);

                goToSleep.pack();
                goToSleep.setVisible(true);
                Dimension desktopSize = Toolkit.getDefaultToolkit().getScreenSize();
                goToSleep.setLocation((desktopSize.width - goToSleep.getWidth()) / 2, (desktopSize.height - goToSleep.getHeight()) / 2);

            } catch (Exception exc) {
                JFrame errorFrame = new JFrame();
                errorFrame.setPreferredSize(new Dimension(700,200));
                errorFrame.add(new JLabel("  An error occurred, make sure your time is between 00:00 to 23:59, and that the end time is later than the start time"));

                JButton confirmButton = new JButton("Confirm");
                confirmButton.addActionListener(al -> {
                    errorFrame.setVisible(false);
                    errorFrame.dispose();
                });

                errorFrame.add(confirmButton,BorderLayout.SOUTH);

                errorFrame.pack();
                errorFrame.setVisible(true);
                Dimension desktopSize = Toolkit.getDefaultToolkit().getScreenSize();
                errorFrame.setLocation((desktopSize.width - errorFrame.getWidth()) / 2, (desktopSize.height - errorFrame.getHeight()) / 2);


            }
        }

        // MODIFIES: schedule
        // EFFECTS: creates an activity and adds it to schedule
        private void createActivity() throws TimeOverlapException, GoToSleepException {
            int startHour = Integer.parseInt(startHourTextField.getText());
            int startMinute = Integer.parseInt(startMinuteTextField.getText());
            int endHour = Integer.parseInt(endHourTextField.getText());
            int endMinute = Integer.parseInt(endMinuteTextField.getText());

            schedule.addTime(startHour,startMinute,endHour,endMinute);
            updateModel();
        }
    }
}
