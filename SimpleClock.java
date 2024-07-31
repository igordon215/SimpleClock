//package SimpleClock;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;


public class SimpleClock extends JFrame implements Runnable {

    Calendar calendar;
    SimpleDateFormat timeFormat;
    SimpleDateFormat dayFormat;
    SimpleDateFormat dateFormat;

    JLabel timeLabel;
    JLabel dayLabel;
    JLabel dateLabel;
    JButton formatButton;
    JButton timeZoneButton;
    String time;
    String day;
    String date;

    boolean is24HourFormat = false;
    boolean isGMT = false;

    SimpleClock() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Digital Clock");
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.setSize(350, 220);
        this.setResizable(false);

        timeFormat = new SimpleDateFormat("hh:mm:ss a");
        dayFormat = new SimpleDateFormat("EEEE");
        dateFormat = new SimpleDateFormat("dd MMMMM, yyyy");
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("SANS_SERIF", Font.PLAIN, 40));
        timeLabel.setBackground(Color.BLACK);
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setOpaque(true);
        dayLabel = new JLabel();
        dayLabel.setFont(new Font("Ink Free", Font.BOLD, 34));

        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Ink Free", Font.BOLD, 30));
        //new button 24hr
        formatButton = new JButton("Switch to 24-hour");
        dateLabel.setFont(new Font("Ink Free", Font.BOLD, 34));
        formatButton.addActionListener(e -> toggleTimeFormat());
        //new button gmt
        timeZoneButton = new JButton("Switch to GMT");
        timeZoneButton.addActionListener(e -> toggleTimezone());

        this.add(formatButton);
        this.add(timeZoneButton);
        this.add(timeLabel);
        this.add(dayLabel);
        this.add(dateLabel);
        this.setVisible(true);

        Thread t = new Thread(this);
        t.start();
    }


    public void run() {
        while (true) {
            updateTime();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateTime() {
        Calendar localCalendar = Calendar.getInstance();

        if (isGMT) {
            localCalendar.add(Calendar.HOUR_OF_DAY, -4);
        }

        timeFormat.applyPattern(is24HourFormat ? "HH:mm:ss" : "hh:mm:ss a");
        time = timeFormat.format(localCalendar.getTime());
        timeLabel.setText(time);

        day = dayFormat.format(localCalendar.getTime());
        dayLabel.setText(day);

        date = dateFormat.format(localCalendar.getTime());
        dateLabel.setText(date);
    }

    private void toggleTimeFormat() {
        is24HourFormat = !is24HourFormat;
        formatButton.setText(is24HourFormat ? "Switch to 12-hour" : "Switch to 24-hour");
        updateTime();
    }

    private void toggleTimezone() {
        isGMT = !isGMT;
        timeZoneButton.setText(isGMT ? "Switch to Local Time" : "Switch to GMT (-4 hours)");
        updateTime();
    }


    public static void main(String[] args) {
        new SimpleClock();
    }
}
