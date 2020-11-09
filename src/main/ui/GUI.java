package ui;

import model.Car;
import model.ParkingList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class GUI extends JFrame implements ActionListener {
    private static final Color BACKGROUND_COLOR = new Color(94, 134, 132);

    private JFrame frame;
    private JPanel panel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel label;
    JTextField fieldMaxCapacity;
    JTextField fieldRate;
    JTextField fieldMinHour;
    JTextField fieldDiscount;
    JTextField fieldCarLicense;
    JTextField fieldCarStartT;
    JTextField fieldCarStartD;
    JButton buttonLeft;
    JButton buttonRight;
    JButton buttonEntry = new JButton("Enter Car");
    JButton buttonExit = new JButton("Remove Car");
    JButton buttonViewHour = new JButton("View Hour(s) Parked");
    JButton buttonViewList = new JButton("View List");
    JButton buttonChangeRate = new JButton("Change Rate ($/hour)");
    JButton buttonChangeMinHour = new JButton("Change Minimum Hour(s)");
    JButton buttonChangeDiscount = new JButton("Change Discount Percentage");
    JButton buttonMainMenu = new JButton("Go to Main menu");
    JButton buttonContinue = new JButton("Continue");

    private static final String JSON_STORE = "./data/parkinglist.json";
    Font font = new Font("Verdana", Font.PLAIN, 30);
    private ParkingList parkingList;
    private Double rate;
    private Integer minDiscountHours;
    private Double discountPercentage;
    private final JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private final JsonReader jsonReader = new JsonReader(JSON_STORE);

    public GUI() {
        mainMenu();
    }

    private void mainMenu() {
        makePanel();
        topPanel = new JPanel();
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        buttonLeft = new JButton("Load previous parking list");
        buttonLeft.setPreferredSize(new Dimension(200,50));
        buttonLeft.setActionCommand("loadList");
        buttonLeft.addActionListener(this);
        buttonRight = new JButton("Open new parking list");
        buttonRight.setPreferredSize(new Dimension(200,50));
        buttonRight.setActionCommand("newList");
        buttonRight.addActionListener(this);

        ImageIcon homeImage = new ImageIcon(getClass().getResource("HomeImage.png"));
        label = new JLabel(homeImage);

        topPanel.add(label);
        bottomPanel.add(buttonLeft);
        bottomPanel.add(buttonRight);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    public static void main(String[] args) {
        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose();
        String command = e.getActionCommand();


        if (command.equals("newList")) {
            doNewParkingList();
        } else {
            if (command.equals("loadList")) {
                doLoadParkingList();
            } else if (command.equals("afterNew")) {
                actionAfterNew();
            } else {
                actionMenu(command);
            }
            doSaveParkingList();
        }
    }

    private void actionAfterNew() {
        Integer maxCapacity = Integer.parseInt(fieldMaxCapacity.getText());
        rate = Double.parseDouble(fieldRate.getText());
        minDiscountHours = Integer.parseInt(fieldMinHour.getText());
        discountPercentage = Double.parseDouble(fieldDiscount.getText());
        parkingList = new ParkingList(maxCapacity, rate, minDiscountHours, discountPercentage);
        doMenu();
    }
    
    private void actionMenu(String command) {
        if ((command.equals("menu"))) {
            doMenu();
        } else if ((command.equals("entry"))) {
            doEntry();
        } else if ((command.equals("exit"))) {
            doExit();
        } else if ((command.equals("viewHour"))) {
            doViewHour();
        } else if ((command.equals("parkedHour"))) {
            actionParkedHour();
        } else if ((command.equals("viewList"))) {
            doViewList();
        } else if ((command.equals("addCar"))) {
            actionAddCar();
        } else if ((command.equals("removeCar"))) {
            actionRemoveCar();
        } else if ((command.equals("mainMenu"))) {
            mainMenu();
        } else {
            actionChange(command);
        }
    }
    
    private void actionChange(String command) {
        if ((command.equals("changeRate"))) {
            doChangeRate();
        } else if ((command.equals("changeMinHour"))) {
            doChangeMinHour();
        } else if ((command.equals("changeDiscount"))) {
            doChangeDiscount();
        } else if ((command.equals("setRate"))) {
            actionSetRate();
        } else if ((command.equals("setMinHour"))) {
            actionSetMinHour();
        } else if ((command.equals("setDiscount"))) {
            actionSetDiscount();
        }
    }

    private void actionAddCar() {
        rate = parkingList.getRate();
        Car car = new Car(fieldCarLicense.getText(), fieldCarStartT.getText(), fieldCarStartD.getText(), rate);
        parkingList.addCar(car);
        makePanel();
        topPanel = new JPanel(new GridLayout(2, 1));
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        label = new JLabel(fieldCarLicense.getText() + " has been added to a parking spot");
        label.setFont(font);
        buttonContinue.setActionCommand("menu");
        buttonContinue.addActionListener(this);

        topPanel.add(label);
        bottomPanel.add(buttonContinue);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    private void actionRemoveCar() {
        String licenseNum = fieldCarLicense.getText();
        if (parkingList.getCar(licenseNum) == null) {
            label = new JLabel("Sorry, there is no car with this license plate! Please try again.");
            label.setFont(font);
            panel.add(label);
            makeFrame();
        } else {
            Car car = parkingList.getCar(licenseNum);
            parkingList.removeCar(car);
            doDisplayPrice(car, fieldCarStartT.getText(), fieldCarStartD.getText());
        }
    }

    private void actionParkedHour() {
        String licenseNum = fieldCarLicense.getText();
        if (parkingList.getCar(licenseNum) == null) {
            label = new JLabel("Sorry, there is no car with this license plate! Please try again.");
            label.setFont(font);
            panel.add(label);
        } else {
            makePanel();
            panel.setLayout(new GridLayout(2,1));
            Car car = parkingList.getCar(licenseNum);
            Integer totalHours = car.getHoursParked(fieldCarStartT.getText(), fieldCarStartD.getText());
            label = new JLabel("This car has been parked for " + totalHours + " hour(s).");
            label.setFont(font);
            panel.add(label);
            panel.add(buttonContinue);
        }
        makeFrame();
    }
    
    private void actionSetRate() {
        rate = Double.parseDouble(fieldRate.getText());
        parkingList.setRate(rate);
        doMenu();
    }
    
    private void actionSetMinHour() {
        minDiscountHours = Integer.parseInt(fieldRate.getText());
        parkingList.setMinDiscountHours(minDiscountHours);
        doMenu();
    }

    private void actionSetDiscount() {
        discountPercentage = Double.parseDouble(fieldRate.getText());
        parkingList.setDiscountPercentage(discountPercentage);
        doMenu();
    }

    private void doNewParkingList() {
        makePanel();
        topPanel = new JPanel(new GridLayout(5, 1));
        bottomPanel = new JPanel();


        topPanelNewList();

        buttonContinue.addActionListener(this);
        buttonContinue.setActionCommand("afterNew");
        bottomPanel.add(buttonContinue);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();

    }

    private void topPanelNewList() {
        

        JLabel label1 = new JLabel("1. maximum capacity of the parking: ");
        label1.setFont(font);
        JLabel label2 = new JLabel("2. rate of charge ($/hour): ");
        label2.setFont(font);
        JLabel label3 = new JLabel("3. minimum hour(s) for discount: ");
        label3.setFont(font);
        JLabel label4 = new JLabel("4. discount percentage: ");
        label4.setFont(font);
        fieldMaxCapacity = new JTextField(5);
        fieldRate = new JTextField(5);
        fieldMinHour = new JTextField(5);
        fieldDiscount = new JTextField(5);

        topPanel.add(label1);
        topPanel.add(fieldMaxCapacity);
        topPanel.add(label2);
        topPanel.add(fieldRate);
        topPanel.add(label3);
        topPanel.add(fieldMinHour);
        topPanel.add(label4);
        topPanel.add(fieldDiscount);
    }

    private void doLoadParkingList() {
        try {
            parkingList = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        makePanel();

        topPanel = new JPanel();
        bottomPanel = new JPanel();


        buttonContinue.setActionCommand("menu");
        buttonContinue.addActionListener(this);

        topPanelLoadList();
        bottomPanel.add(buttonContinue);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    private void topPanelLoadList() {
        String labelString = "<html>Loaded previous parking list...<br><br>";
        labelString += "Number of empty spots left  = " + parkingList.getNumberEmptySlots() + "<br>";
        labelString += "Rate of charge = " + parkingList.getRate() + "<br>";
        labelString += "Minimum hours for discount = " + parkingList.getMinDiscountHours() + "<br>";
        labelString += "Discount percentage = " + parkingList.getDiscountPercentage() + "<br><br></html>";

        
        label = new JLabel(labelString);
        label.setFont(font);
        topPanel.add(label);
    }

    private void doMenu() {
        makePanel();
        panel.setLayout(new GridLayout(8, 2));

        JLabel label1 = new JLabel("Car Entry");
        label1.setFont(font);
        
        JLabel label2 = new JLabel("Car Exit");
        label2.setFont(font);
        
        JLabel label3 = new JLabel("View Hour(s) Parked");
        label3.setFont(font);
        
        JLabel label4 = new JLabel("View List");
        label4.setFont(font);
        
        JLabel label5 = new JLabel("Change Rate");
        label5.setFont(font);
        
        JLabel label6 = new JLabel("Change Minimum Hour(s) for Discount");
        label6.setFont(font);
        
        JLabel label7 = new JLabel("Change Discount Percentage");
        label7.setFont(font);
        
        JLabel label8 = new JLabel("Go to Main Menu");
        label8.setFont(font);

        buttonsMenu();
        
        panelAddMenu(label1, label2, label3, label4, label5, label6, label7, label8);
        
        makeFrame();

    }
    
    private void buttonsMenu() {

        buttonEntry.setActionCommand("entry");
        buttonEntry.addActionListener(this);


        buttonExit.setActionCommand("exit");
        buttonExit.addActionListener(this);


        buttonViewHour.setActionCommand("viewHour");
        buttonViewHour.addActionListener(this);


        buttonViewList.setActionCommand("viewList");
        buttonViewList.addActionListener(this);


        buttonChangeRate.setActionCommand("changeRate");
        buttonChangeRate.addActionListener(this);


        buttonChangeMinHour.setActionCommand("changeMinHour");
        buttonChangeMinHour.addActionListener(this);


        buttonChangeDiscount.setActionCommand("changeDiscount");
        buttonChangeDiscount.addActionListener(this);


        buttonMainMenu.setActionCommand("mainMenu");
        buttonMainMenu.addActionListener(this);
    }
    
    private void panelAddMenu(JLabel l1, JLabel l2,JLabel l3,JLabel l4,JLabel l5,JLabel l6,JLabel l7,JLabel l8) {
        panel.add(l1);
        panel.add(buttonEntry);
        panel.add(l2);
        panel.add(buttonExit);
        panel.add(l3);
        panel.add(buttonViewHour);
        panel.add(l4);
        panel.add(buttonViewList);
        panel.add(l5);
        panel.add(buttonChangeRate);
        panel.add(l6);
        panel.add(buttonChangeMinHour);
        panel.add(l7);
        panel.add(buttonChangeDiscount);
        panel.add(l8);
        panel.add(buttonMainMenu);
    }
    
    private void doEntry() {
        makePanel();
        if (parkingList.getNumberEmptySlots() == 0) {
            label = new JLabel("<html>Sorry, parking is full!<br>Please ask the car to park somewhere else</html>");
            label.setFont(new Font("Verdana", Font.BOLD, 30));
            panel.add(label);
        } else {
            topPanel = new JPanel(new GridLayout(3, 1));
            bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            buttonLeft = new JButton("Go Back");
            buttonLeft.setActionCommand("menu");
            buttonLeft.addActionListener(this);
            buttonRight = new JButton("Add Car");
            buttonRight.setActionCommand("addCar");
            buttonRight.addActionListener(this);

            topPanelEntryOrExitOrView();
            bottomPanel.add(buttonLeft);
            bottomPanel.add(buttonRight);

            panel.add(topPanel, BorderLayout.NORTH);
            panel.add(bottomPanel, BorderLayout.SOUTH);

        }
        makeFrame();
    }


    private void doExit() {
        makePanel();

        topPanel = new JPanel(new GridLayout(3, 1));
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        buttonLeft = new JButton("Go Back");
        buttonLeft.setActionCommand("menu");
        buttonLeft.addActionListener(this);
        buttonRight = new JButton("Remove Car");
        buttonRight.setActionCommand("removeCar");
        buttonRight.addActionListener(this);

        topPanelEntryOrExitOrView();
        bottomPanel.add(buttonLeft);
        bottomPanel.add(buttonRight);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }


    private void doDisplayPrice(Car car, String endTime, String endDate) {
        Double totalCost;
        Double discP = 0.00;
        if (car.getHoursParked(endTime, endDate) == 0) {
            totalCost = car.getRate();
        } else {
            totalCost = car.getHoursParked(endTime, endDate) * car.getRate();
        }

        if (car.getHoursParked(endTime, endDate) >= parkingList.getMinDiscountHours()) {
            totalCost = totalCost * ((100 - parkingList.getDiscountPercentage()) / 100);
            discP = parkingList.getDiscountPercentage();
        }

        makePanel();

        topPanel = new JPanel(new GridLayout(2, 1));
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        buttonContinue.setActionCommand("menu");
        buttonContinue.addActionListener(this);

        topPanelPrice(discP, totalCost);
        bottomPanel.add(buttonContinue);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    private void topPanelPrice(Double discP, Double totalCost) {
        
        JLabel label1 = new JLabel("Discount has been applied of " + discP + "%.");
        JLabel label2 = new JLabel("The total cost for this car is $" + totalCost);
        label1.setFont(font);
        label2.setFont(font);

        topPanel.add(label1);
        topPanel.add(label2);
    }

    private void doViewHour() {
        makePanel();

        topPanel = new JPanel(new GridLayout(3, 1));
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        buttonLeft = new JButton("Go Back");
        buttonLeft.setActionCommand("menu");
        buttonLeft.addActionListener(this);
        buttonRight = new JButton("View Hour(s)");
        buttonRight.setActionCommand("parkedHour");
        buttonRight.addActionListener(this);

        topPanelEntryOrExitOrView();
        bottomPanel.add(buttonLeft);
        bottomPanel.add(buttonRight);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    private void topPanelEntryOrExitOrView() {
        
        JLabel label1 = new JLabel("Enter the license plate number of the car ");
        label1.setFont(font);
        JLabel label2 = new JLabel("Enter the current time as HH:MM (24-hour clock):");
        label2.setFont(font);
        JLabel label3 = new JLabel("Enter today's date as MM-DD-YYYY:");
        label3.setFont(font);

        fieldCarLicense = new JTextField(5);
        fieldCarStartT = new JTextField(5);
        fieldCarStartD = new JTextField(5);

        topPanel.add(label1);
        topPanel.add(fieldCarLicense);
        topPanel.add(label2);
        topPanel.add(fieldCarStartT);
        topPanel.add(label3);
        topPanel.add(fieldCarStartD);
    }

    private void doViewList() {
        makePanel();
        topPanel = new JPanel();
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        List<Car> carList = parkingList.getCars();
        String labelString = "<html>";
        if (carList.isEmpty()) {
            labelString = "No cars parked right now";
        }
        for (Car c : carList) {
            labelString += "Car " + (carList.indexOf(c) + 1) + ": " + c.getLicenseNum() + "<br>";
        }
        labelString += "</html>";
        
        label = new JLabel(labelString);
        label.setFont(font);


        buttonContinue.setActionCommand("menu");
        buttonContinue.addActionListener(this);

        topPanel.add(label);
        bottomPanel.add(buttonContinue);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    private void doChangeRate() {
        makePanel();

        topPanel = new JPanel(new GridLayout(1,1));
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        
        label = new JLabel("Enter the new rate of charge ($/hour): ");
        label.setFont(font);
        fieldRate = new JTextField(5);
        buttonLeft = new JButton("Go Back");
        buttonLeft.setActionCommand("menu");
        buttonLeft.addActionListener(this);
        buttonRight = new JButton("Change");
        buttonRight.setActionCommand("setRate");
        buttonRight.addActionListener(this);

        topPanel.add(label);
        topPanel.add(fieldRate);
        bottomPanel.add(buttonLeft);
        bottomPanel.add(buttonRight);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    private void doChangeMinHour() {
        makePanel();

        topPanel = new JPanel(new GridLayout(1,1));
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        
        label = new JLabel("<html>Enter the new minimum number of hour(s)<br>needed for discount: </html>");
        label.setFont(font);
        fieldRate = new JTextField(5);
        buttonLeft = new JButton("Go Back");
        buttonLeft.setActionCommand("menu");
        buttonLeft.addActionListener(this);
        buttonRight = new JButton("Change");
        buttonRight.setActionCommand("setMinHour");
        buttonRight.addActionListener(this);

        topPanel.add(label);
        topPanel.add(fieldRate);
        bottomPanel.add(buttonLeft);
        bottomPanel.add(buttonRight);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    private void doChangeDiscount() {
        makePanel();

        topPanel = new JPanel(new GridLayout(1,1));
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        
        label = new JLabel("Enter the new discount percentage :");
        label.setFont(font);
        fieldRate = new JTextField(5);
        buttonLeft = new JButton("Go Back");
        buttonLeft.setActionCommand("menu");
        buttonLeft.addActionListener(this);
        buttonRight = new JButton("Change");
        buttonRight.setActionCommand("setDiscount");
        buttonRight.addActionListener(this);

        topPanel.add(label);
        topPanel.add(fieldRate);
        bottomPanel.add(buttonLeft);
        bottomPanel.add(buttonRight);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    private void doSaveParkingList() {
        try {
            jsonWriter.open();
            jsonWriter.write(parkingList);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private void makeFrame() {
        frame = new JFrame();
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("PerfectPark");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void makePanel() {
        panel = new JPanel(new BorderLayout());

        topPanel = new JPanel();
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        panel.setBackground(BACKGROUND_COLOR);
    }

}
