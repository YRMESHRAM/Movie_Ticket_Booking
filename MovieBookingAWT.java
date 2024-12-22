import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MovieBookingAWT extends Frame implements ActionListener {

    Label cityLabel, theaterLabel, movieLabel, ticketLabel, timingLabel;
    Choice cityChoice, theaterChoice, movieChoice, timingChoice;
    TextField ticketsField;
    Button bookButton, resetButton;

    // Maximum tickets available per show
    private static final int MAX_TICKETS = 10;
    private int availableTickets = MAX_TICKETS;

    public MovieBookingAWT() {
        // Frame properties
        setTitle("Movie Booking System");
        setSize(450, 400);
        setLayout(new GridLayout(7, 2, 10, 10)); // Added padding

        // City selection
        cityLabel = new Label("Select City:");
        cityChoice = new Choice();
        cityChoice.add("Nagpur");
        cityChoice.add("Pune");
        cityChoice.add("Chandrapur");

        // Theater selection
        theaterLabel = new Label("Select Theater:");
        theaterChoice = new Choice();
        theaterChoice.add("Inox");
        theaterChoice.add("Icon");
        theaterChoice.add("PVP");

        // Movie selection
        movieLabel = new Label("Select Movie:");
        movieChoice = new Choice();
        movieChoice.add("RRR");
        movieChoice.add("Pathan");
        movieChoice.add("KGF2");

        // Tickets input
        ticketLabel = new Label("Number of Tickets:");
        ticketsField = new TextField();

        // Timing selection
        timingLabel = new Label("Select Show Timing:");
        timingChoice = new Choice();
        timingChoice.add("10.00-1.00");
        timingChoice.add("1.10-4.10");
        timingChoice.add("4.20-7.20");
        timingChoice.add("7.30-10.30");

        // Buttons
        bookButton = new Button("Book Tickets");
        resetButton = new Button("Reset");

        bookButton.addActionListener(this);
        resetButton.addActionListener(this);

        // Add components to the frame
        add(cityLabel); add(cityChoice);
        add(theaterLabel); add(theaterChoice);
        add(movieLabel); add(movieChoice);
        add(ticketLabel); add(ticketsField);
        add(timingLabel); add(timingChoice);
        add(bookButton); add(resetButton);

        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookButton) {
            String city = cityChoice.getSelectedItem();
            String theater = theaterChoice.getSelectedItem();
            String movie = movieChoice.getSelectedItem();
            String timing = timingChoice.getSelectedItem();
            String numTicketsStr = ticketsField.getText().trim();

            // Validate inputs
            if (numTicketsStr.isEmpty()) {
                showErrorDialog("Please enter the number of tickets!");
                return;
            }

            try {
                int numTickets = Integer.parseInt(numTicketsStr);
                if (numTickets <= 0 || numTickets > availableTickets) {
                    showErrorDialog("Invalid number of tickets! Maximum available: " + availableTickets);
                    return;
                }

                // Random screen allocation
                String[] screens = {"SCREEN 1", "SCREEN 2", "SCREEN 3"};
                String allocatedScreen = screens[new Random().nextInt(screens.length)];

                // Dynamic ticket pricing
                double basePrice = 150.0;
                double timingMultiplier = timing.equals("7.30-10.30") ? 1.2 : 1.0; // Evening shows cost more
                double ticketPrice = basePrice * timingMultiplier;
                double totalCost = numTickets * ticketPrice;

                // Update available tickets
                availableTickets -= numTickets;

                // Write booking details to a file
                try (FileWriter writer = new FileWriter("movie_bookings.txt", true)) {
                    writer.write("City: " + city + "\n");
                    writer.write("Theater: " + theater + "\n");
                    writer.write("Movie: " + movie + "\n");
                    writer.write("Timing: " + timing + "\n");
                    writer.write("Allocated Screen: " + allocatedScreen + "\n");
                    writer.write("Number of Tickets: " + numTickets + "\n");
                    writer.write("Total Cost: ₹" + totalCost + "\n\n");
                } catch (IOException ex) {
                    showErrorDialog("Failed to save booking details. Please try again.");
                    return;
                }

                // Show confirmation dialog
                Dialog dialog = new Dialog(this, "Booking Confirmed", true);
                dialog.setLayout(new FlowLayout());
                dialog.setSize(300, 200);
                dialog.add(new Label("Booking confirmed!"));
                dialog.add(new Label("Allocated Screen: " + allocatedScreen));
                dialog.add(new Label("Total Cost: ₹" + totalCost));
                dialog.add(new Label("Remaining Tickets: " + availableTickets));
                Button okButton = new Button("OK");
                okButton.addActionListener(ae -> dialog.setVisible(false));
                dialog.add(okButton);
                dialog.setVisible(true);

                // Reset fields after booking
                resetFields();

            } catch (NumberFormatException ex) {
                showErrorDialog("Please enter a valid number of tickets!");
            }
        } else if (e.getSource() == resetButton) {
            resetFields();
        }
    }

    private void showErrorDialog(String message) {
        Dialog errorDialog = new Dialog(this, "Error", true);
        errorDialog.setLayout(new FlowLayout());
        errorDialog.setSize(300, 100);
        errorDialog.add(new Label(message));
        Button okButton = new Button("OK");
        okButton.addActionListener(ae -> errorDialog.setVisible(false));
        errorDialog.add(okButton);
        errorDialog.setVisible(true);
    }

    private void resetFields() {
        cityChoice.select(0);
        theaterChoice.select(0);
        movieChoice.select(0);
        ticketsField.setText("");
        timingChoice.select(0);
    }

    public static void main(String[] args) {
        new MovieBookingAWT();
    }
}
