import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CancellationForm extends JFrame implements ActionListener {
    private JTextField pnrField;
    private JTextArea bookingDetails;
    private JButton searchButton, cancelButton;

    private String foundRecord;
    private File reservationFile = new File("reservations.txt");

    public CancellationForm() {
        setTitle("Cancel Reservation");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel pnrLabel = new JLabel("Enter PNR:");
        pnrField = new JTextField(15);
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);

        topPanel.add(pnrLabel);
        topPanel.add(pnrField);
        topPanel.add(searchButton);

        bookingDetails = new JTextArea();
        bookingDetails.setEditable(false);

        cancelButton = new JButton("Cancel Ticket");
        cancelButton.setEnabled(false);
        cancelButton.addActionListener(e -> cancelBooking());

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(bookingDetails), BorderLayout.CENTER);
        add(cancelButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String pnr = pnrField.getText().trim();
        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a PNR number!");
            return;
        }

        foundRecord = null;
        bookingDetails.setText("");
        cancelButton.setEnabled(false);

        try (BufferedReader br = new BufferedReader(new FileReader(reservationFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(pnr + ",")) {
                    foundRecord = line;
                    bookingDetails.setText("Booking Found:\n" + line.replace(",", " | "));
                    cancelButton.setEnabled(true);
                    break;
                }
            }
            if (foundRecord == null) {
                JOptionPane.showMessageDialog(this, "No booking found for this PNR.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading reservations!");
        }
    }

    private void cancelBooking() {
        if (foundRecord == null) return;

        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this ticket?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            try {
                List<String> allBookings = new ArrayList<>();
                try (BufferedReader br = new BufferedReader(new FileReader(reservationFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (!line.equals(foundRecord)) {
                            allBookings.add(line);
                        }
                    }
                }

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(reservationFile))) {
                    for (String booking : allBookings) {
                        bw.write(booking);
                        bw.newLine();
                    }
                }

                JOptionPane.showMessageDialog(this, "Ticket Cancelled Successfully!");
                bookingDetails.setText("");
                pnrField.setText("");
                cancelButton.setEnabled(false);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error updating reservations!");
            }
        }
    }
}