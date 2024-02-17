import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyRestaurant extends JFrame implements ActionListener {

    private JLabel restaurantNameLabel, customerLabel, nameLabel, contactLabel, foodLabel, drinkLabel, typeLabel, menuLabel;
    private JTextField tfName, tfContact;
    private JButton resetButton, printButton, receiptButton;
    private JButton viewCustomersButton;

    private JComboBox<String> foodComboBox, drinkComboBox, menuComboBox,quantityComboBox;
    private JRadioButton dietRadioButton, normalRadioButton;
    private JTextArea receiptTextArea;
    private int foodPrice, drinkPrice, totalPrice;
    private int customerNumber;

    public MyRestaurant() {
        setTitle("MK Restaurant Management System");
        setSize(700, 500);
        setLayout(null);

        // Restaurant Name
        restaurantNameLabel = new JLabel("MK Restaurant");
        restaurantNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        restaurantNameLabel.setBounds(250, 20, 200, 30);

        // Labels
        customerLabel = new JLabel("Customer No");
        customerLabel.setBounds(20, 70, 120, 20);

        nameLabel = new JLabel("Customer Name");
        nameLabel.setBounds(20, 110, 120, 20);

        contactLabel = new JLabel("Contact No");
        contactLabel.setBounds(20, 150, 120, 20);

        foodLabel = new JLabel("Food");
        foodLabel.setBounds(20, 190, 120, 20);

        drinkLabel = new JLabel("Drink");
        drinkLabel.setBounds(20, 230, 120, 20);

        typeLabel = new JLabel("Type");
        typeLabel.setBounds(20, 270, 120, 20);

        menuLabel = new JLabel("Menu");
        menuLabel.setBounds(20, 310, 120, 20);

        // TextFields
        tfName = new JTextField();
        tfName.setBounds(150, 110, 200, 20);

        tfContact = new JTextField();
        tfContact.setBounds(150, 150, 200, 20);

        // ComboBoxes
        String[] foodItems = {"Pizza", "Burgers", "Fries", "Salad"};
        foodComboBox = new JComboBox<>(foodItems);
        foodComboBox.setBounds(150, 190, 200, 20);

        String[] drinkItems = {"Pepsi", "Coca-Cola", "Dew", "Water"};
        drinkComboBox = new JComboBox<>(drinkItems);
        drinkComboBox.setBounds(150, 230, 200, 20);

        String[] menuItems = {"Pizza    Rs 100", "Burger    Rs 150", "Fries    Rs 60", "Salad    Rs 120",
                "Coca Cola    Rs 60", "Pepsi    Rs 60", "Dew    Rs 60", "Water    Rs 50"};
        menuComboBox = new JComboBox<>(menuItems);
        menuComboBox.setBounds(150, 310, 200, 20);

        String[] quantity={"1 ,2,3,5"};
       // quantityCombobox=new JComboBox<>(quantiy)
        //String[]quantity={}

        // Radio Buttons
        dietRadioButton = new JRadioButton("Diet");
        dietRadioButton.setBounds(150, 270, 100, 20);

        normalRadioButton = new JRadioButton("Normal");
        normalRadioButton.setBounds(250, 270, 80, 20);


        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(dietRadioButton);
        radioButtonGroup.add(normalRadioButton);

        // Buttons
        resetButton = new JButton("Reset");
        resetButton.setBounds(400, 290, 80, 40);
        resetButton.addActionListener(this);

        printButton = new JButton("Print");
        printButton.setBounds(480, 290, 80, 40);
        printButton.addActionListener(this);

        receiptButton = new JButton("Receipt");
        receiptButton.setBounds(560, 290, 80, 40);
        receiptButton.addActionListener(this);

        // TextArea
        receiptTextArea = new JTextArea();
        receiptTextArea.setBounds(400, 80, 240, 200);
        viewCustomersButton = new JButton("View Customers Details");
        viewCustomersButton.setBounds(400, 350, 240, 40);
        viewCustomersButton.addActionListener(this);
        add(viewCustomersButton);



        // Add components to the frame
        add(restaurantNameLabel);
        add(customerLabel);
        add(nameLabel);
        add(contactLabel);
        add(foodLabel);
        add(drinkLabel);
        add(typeLabel);
        add(menuLabel);
        add(tfName);
        add(tfContact);
        add(foodComboBox);
        add(drinkComboBox);
        add(dietRadioButton);
        add(normalRadioButton);
        add(menuComboBox);
        add(resetButton);
        add(receiptButton);
        add(printButton);
        add(receiptTextArea);
        add(viewCustomersButton);

        // Set default values
        dietRadioButton.setSelected(true);

        // Set frame properties
        getContentPane().setBackground(Color.lightGray);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            resetFields();
        } else if (e.getSource() == printButton) {
            printReceipt();
        } else if (e.getSource() == receiptButton) {
            generateReceipt();
        }
        else if (e.getSource() == viewCustomersButton) {
            viewCustomers();
        }
    }

    private void resetFields() {// reset fileds to reset the fileds when ever click
        tfName.setText("");
        tfContact.setText("");
        receiptTextArea.setText("");
    }

    private void printReceipt() {
        try {
            receiptTextArea.print();
        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }

    private void generateReceipt() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentDateTime.format(formatter);
        String customerName = tfName.getText();
        String contactNumber = tfContact.getText();
        String selectedFood = (String) foodComboBox.getSelectedItem();
        String selectedDrink = (String) drinkComboBox.getSelectedItem();
        String selectedMenu = (String) menuComboBox.getSelectedItem();

        receiptTextArea.setText("                              MK Restaurant\n\n");
        receiptTextArea.append("Date: " + currentDateTime.toLocalDate() + "\n");
        receiptTextArea.append("Time: " + formattedTime + "\n");

        // Generate and display customer number
        customerNumber++;
        receiptTextArea.append("Customer Number: " + customerNumber + "\n");

        receiptTextArea.append("Customer Name: " + customerName + "\n");
        receiptTextArea.append("Contact Number: " + contactNumber + "\n");
        receiptTextArea.append("Food: " + selectedFood + "\n");
        receiptTextArea.append("Drink: " + selectedDrink + "\n");

        calculateTotal();
        receiptTextArea.append("Total: Rs " + totalPrice + "\n");
        receiptTextArea.append("Including Tax +Total: Rs " + totalPrice*1.75 + "\n");

        // Save receipt to file
        saveReceiptToFile();
    }

    private void calculateTotal() {
        int foodIndex = foodComboBox.getSelectedIndex();
        int drinkIndex = drinkComboBox.getSelectedIndex();

        switch (foodIndex) {
            case 0:
                foodPrice = 100;
                break;
            case 1:
                foodPrice = 150;
                break;
            case 2:
                foodPrice = 60;
                break;
            case 3:
                foodPrice = 120;
                break;
        }

        switch (drinkIndex) {
            case 0:
            case 1:
            case 2:
                drinkPrice = 60;
                break;
            case 3:
                drinkPrice = 50;
                break;
        }

        totalPrice = foodPrice + drinkPrice;
    }

    private void saveReceiptToFile() {
        String fileName = "receipt.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(receiptTextArea.getText());
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void viewCustomers() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("receipt.txt"));
            String line;
            StringBuilder customers = new StringBuilder();
            StringBuilder customerDetails = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Customer Number: ")) {
                    if (customerDetails.length() > 0) {
                        customers.append(customerDetails.toString()).append("\n\n");
                        customerDetails = new StringBuilder();
                    }
                    customers.append(line.substring("Customer Number: ".length())).append("\n");
                } else if (line.startsWith("Food: ") || line.startsWith("Drink: ") || line.startsWith("Total: ")) {
                    customerDetails.append(line).append("\n");
                }
            }

            if (customerDetails.length() > 0) {
                customers.append(customerDetails.toString()).append("\n");
            }

            if (customers.length() > 0) {
                JOptionPane.showMessageDialog(this, "Saved Customers:\n" + customers.toString(), "Saved Customers", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No saved customers found.", "No Customers", JOptionPane.INFORMATION_MESSAGE);
            }

            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MyRestaurant::new);
    }
}
