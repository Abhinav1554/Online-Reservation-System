import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Online Reservation System - Main Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1, 10, 10));

        JButton bookBtn = new JButton("Book Ticket");
        JButton cancelBtn = new JButton("Cancel Ticket");

        // Book Ticket button ka action
        bookBtn.addActionListener(e -> {
            dispose(); // current window band
            new ReservationForm(); // Booking form open
        });

        // Cancel Ticket button ka action
        cancelBtn.addActionListener(e -> {
            dispose(); // current window band
            new CancellationForm(); // Cancellation form open
        });

        add(bookBtn);
        add(cancelBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}