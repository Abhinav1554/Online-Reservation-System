import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ReservationForm extends JFrame implements ActionListener {
    private JTextField nameField, ageField, trainNoField, trainNameField, fromField, toField, dateField;
    private JComboBox<String> classTypeBox;
    private JButton bookButton, clearButton;

    private HashMap<String, String> trainData = new HashMap<>();

    public ReservationForm() {
        setTitle("Online Reservation System - Booking");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 2, 5, 5));

        // Train data (Train No -> Train Name)
        trainData.put("12345", "Rajdhani Express");
        trainData.put("54321", "Shatabdi Express");
        trainData.put("11111", "Garib Rath");

        JLabel nameLabel = new JLabel("Passenger Name:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel trainNoLabel = new JLabel("Train Number:");
        JLabel trainNameLabel = new JLabel("Train Name:");
        JLabel classLabel = new JLabel("Class Type:");
        JLabel dateLabel = new JLabel("Date of Journey (dd-MM-yyyy):");
        JLabel fromLabel = new JLabel("From:");
        JLabel toLabel = new JLabel("To:");

        nameField = new JTextField();
        ageField = new JTextField();
        trainNoField = new JTextField();
        trainNameField = new JTextField();
        trainNameField.setEditable(false);

        classTypeBox = new JComboBox<>(new String[]{"Sleeper", "AC 3 Tier", "AC 2 Tier", "First Class"});

        dateField = new JTextField(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        fromField = new JTextField();
        toField = new JTextField();

        bookButton = new JButton("Book Ticket");
        clearButton = new JButton("Clear");

        // Event: Auto-fill Train Name
        trainNoField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                String trainNo = trainNoField.getText().trim();
                if (trainData.containsKey(trainNo)) {
                    trainNameField.setText(trainData.get(trainNo));
                } else {
                    trainNameField.setText("Unknown Train");
                }
            }
        });

        bookButton.addActionListener(this);
        clearButton.addActionListener(e -> {
            nameField.setText("");
            ageField.setText("");
            trainNoField.setText("");
            trainNameField.setText("");
            fromField.setText("");
            toField.setText("");
        });

        add(nameLabel); add(nameField);
        add(ageLabel); add(ageField);
        add(trainNoLabel); add(trainNoField);
        add(trainNameLabel); add(trainNameField);
        add(classLabel); add(classTypeBox);
        add(dateLabel); add(dateField);
        add(fromLabel); add(fromField);
        add(toLabel); add(toField);
        add(bookButton); add(clearButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText().trim();
        String age = ageField.getText().trim();
        String trainNo = trainNoField.getText().trim();
        String trainName = trainNameField.getText().trim();
        String classType = classTypeBox.getSelectedItem().toString();
        String date = dateField.getText().trim();
        String from = fromField.getText().trim();
        String to = toField.getText().trim();

        if (name.isEmpty() || age.isEmpty() || trainNo.isEmpty() || from.isEmpty() || to.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        String pnr = generatePNR();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reservations.txt", true))) {
            bw.write(pnr + "," + name + "," + age + "," + trainNo + "," + trainName + "," + classType + "," + date + "," + from + "," + to);
            bw.newLine();
            JOptionPane.showMessageDialog(this, "Ticket Booked! PNR: " + pnr);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving reservation!");
        }
    }

    private String generatePNR() {
        return "PNR" + System.currentTimeMillis();
    }
}