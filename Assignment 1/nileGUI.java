/* Name: Kaitlyn Stagg
Course: CNT 4714 – Spring 2025
Assignment title: Project 1 – An Event-driven Enterprise Simulation
Date: Sunday January 29, 2025
*/

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;


public class nileGUI extends JFrame{
    //class constants
    private static final int WINDOW_WIDTH = 700; //pixels
    private static final int WINDOW_HEIGHT = 650; //pixels
    private static final int FIELD_WIDTH = 20; //characters

    //instance variables
    private JLabel cartLabel, controlsLabel, idTag, quantTag, detailTag, subtotalTag;
    private JTextField idText, quantText, detailText, subtotalText, item1, item2, item3, item4, item5;

    //buttons
    private JButton runButton, addButton, deleteButton, checkoutButton, emptyButton, exitButton;

    //ref vars for button handlers
    private RunButtonHandler        runbHandler;
    private AddButtonHandler        addbHandler;
    private DeleteButtonHandler     deletebHandler;
    private CheckoutButtonHandler   checkoutbHandler;
    private EmptyButtonHandler      emptybHandler;
    private ExitButtonHandler       exitbHandler;

    ArrayList<String> cartItems = new ArrayList<>();//cart arraylist
    String[] cartOutput = new String[5];

    public int itemCount = 0;
    Double[] subt = {0.0, 0.0, 0.0, 0.0, 0.0};//arr of subtotals from item orders
    Double place = 0.0;//placeholder for curr item subtotal
    Double cartTotal = 0.0;//sum of all subtotals

    final DecimalFormat df = new DecimalFormat("0.00");//format currency
     
    
    //nileGUI(): constructor
    public nileGUI() {
        //configure GUI
        setTitle("Nile.com - Spring 2025");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);//center the frame

        for(int i = 0; i < 5; i++){//insert 5 blank strings
            cartItems.add("");
        }

        //labels
        idTag = new JLabel("Enter ID for Item #" + (itemCount+1) + ":"); //user entry area for id
        quantTag = new JLabel("Enter Quantity for Item #" + (itemCount+1) + ":"); //user entry area for quantity
        detailTag = new JLabel("Details for Item #" + (itemCount+1) + ":"); //entry area for details
        subtotalTag = new JLabel("Current Subtotal for " + (itemCount) + " Item(s):"); //entry area for subtotal
        cartLabel = new JLabel("Your Shopping Cart Currently Contains " + (itemCount) + " Item(s)");
        controlsLabel = new JLabel(" User Controls ", SwingConstants.RIGHT);

        //textfield objs
        idText = new JTextField(FIELD_WIDTH);
        quantText = new JTextField(FIELD_WIDTH);
        detailText = new JTextField(FIELD_WIDTH);
        subtotalText = new JTextField(FIELD_WIDTH);
        item1 = new JTextField(FIELD_WIDTH);
        item2 = new JTextField(FIELD_WIDTH);
        item3 = new JTextField(FIELD_WIDTH);
        item4 = new JTextField(FIELD_WIDTH);
        item5 = new JTextField(FIELD_WIDTH);
        
        //buttons & register handlers
        runButton = new JButton("Search For Item #" + (itemCount+1));
        runbHandler = new RunButtonHandler();
        runButton.addActionListener(runbHandler);

        addButton = new JButton("Add Item #" + (itemCount+1) + " to Cart");
        addbHandler = new AddButtonHandler();
        addButton.addActionListener(addbHandler);

        deleteButton = new JButton("Delete Last Item Added to Cart");
        deletebHandler = new DeleteButtonHandler();
        deleteButton.addActionListener(deletebHandler);

        checkoutButton = new JButton("Check Out");
        checkoutbHandler = new CheckoutButtonHandler();
        checkoutButton.addActionListener(checkoutbHandler);

        emptyButton = new JButton("Empty Cart - Start a New Order");
        emptybHandler = new EmptyButtonHandler();
        emptyButton.addActionListener(emptybHandler);

        exitButton = new JButton("Exit (Close App)");
        exitbHandler = new ExitButtonHandler();
        exitButton.addActionListener(exitbHandler);

        //initial btn states
        addButton.setEnabled(false);
        deleteButton.setEnabled(false);
        checkoutButton.setEnabled(false);
        emptyButton.setEnabled(true);

        Container pane = getContentPane();

        JPanel northPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel southPanel = new JPanel();

        northPanel.setLayout(new GridLayout(5,2, 0, 10));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        southPanel.setLayout(new GridLayout(5,2,35,5));

        centerPanel.setSize(new Dimension(500, 200));

        pane.add(northPanel, BorderLayout.NORTH);
        pane.add(centerPanel, BorderLayout.CENTER);
        pane.add(southPanel, BorderLayout.SOUTH);   

        cartLabel.setBorder(new EmptyBorder(new Insets(0,185,15,0)));
        controlsLabel.setBorder(new EmptyBorder(new Insets(15,280,15,150)));
        
        
        //pad top & left side of tags
        idTag.setBorder(new EmptyBorder(new Insets(15,15,0,0)));
        quantTag.setBorder(new EmptyBorder(new Insets(0,15,0,0)));
        detailTag.setBorder(new EmptyBorder(new Insets(0,15,0,0)));
        subtotalTag.setBorder(new EmptyBorder(new Insets(0,15,0,0)));
        
        //pad left side of txt box
        item1.setMargin(new Insets(0,10,0,0));
        item2.setMargin(new Insets(0,10,0,0));
        item3.setMargin(new Insets(0,10,0,0));
        item4.setMargin(new Insets(0,10,0,0));
        item5.setMargin(new Insets(0,10,0,0));

        detailText.setEditable(false);
        subtotalText.setEditable(false);
        item1.setEditable(false);
        item2.setEditable(false);
        item3.setEditable(false);
        item4.setEditable(false);
        item5.setEditable(false);

        //change btn backgrounds
        runButton.setBackground(Color.WHITE);
        runButton.setOpaque(true);

        addButton.setBackground(Color.WHITE);
        addButton.setOpaque(true);

        deleteButton.setBackground(Color.WHITE);
        deleteButton.setOpaque(true);

        checkoutButton.setBackground(Color.WHITE);
        checkoutButton.setOpaque(true);

        emptyButton.setBackground(Color.WHITE);
        emptyButton.setOpaque(true);

        exitButton.setBackground(Color.WHITE);
        exitButton.setOpaque(true);


        //details panel
        northPanel.setBorder(new EmptyBorder(new Insets(15,15,0,15)));
        northPanel.add(idTag);
        northPanel.add(idText);
        northPanel.add(quantTag);
        northPanel.add(quantText);
        northPanel.add(detailTag);
        northPanel.add(detailText);
        northPanel.add(subtotalTag);
        northPanel.add(subtotalText);

        //cart panel
        centerPanel.setBorder(new EmptyBorder(new Insets(0,15,0,15)));
        centerPanel.add(cartLabel);
        centerPanel.add(item1);
        centerPanel.add(item2);
        centerPanel.add(item3);
        centerPanel.add(item4);
        centerPanel.add(item5);
        centerPanel.add(controlsLabel);

        //button panel
        southPanel.setBorder(new EmptyBorder(new Insets(0,25,0,25)));
        southPanel.add(runButton);
        southPanel.add(addButton);
        southPanel.add(deleteButton);
        southPanel.add(checkoutButton);
        southPanel.add(emptyButton);
        southPanel.add(exitButton);

    }
    private class RunButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //set class vars
            String input = idText.getText();//get user input
            String quantInput = quantText.getText();// ^
            Integer quant = Integer.parseInt(quantInput);//convert requested amt to int

            String[] txtboxs = filereader.readInventoryFile(input, quant);
            //update txt boxes based on search
            idText.setText(txtboxs[0]);
            quantText.setText(txtboxs[1]);
            detailText.setText(txtboxs[2]);

            if(txtboxs[2].equals("") == false){//if search successful: enable add btn, disable run btn
                runButton.setEnabled(false);
                addButton.setEnabled(true);
            }
        }
    }
    private class AddButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String tempDetail = detailText.getText();
            cartOutput[itemCount] = tempDetail;
            String[] details = (detailText.getText()).split(",");//split by comma
            ArrayList<String> detailsAL = new ArrayList<>(Arrays.asList(details));
            place = Double.parseDouble((detailsAL.get(5)).replaceAll(" \\$", ""));//hold curr item subtotal
            subt[itemCount] = place;


            cartTotal += subt[itemCount];//add curr item subtotal to cart total
            
            //modify details str for cartItems
            cartItems.set(itemCount, "Item " + (itemCount+1) + " - SKU: " + detailsAL.get(0) + ", Desc:" + detailsAL.get(1) + ", Price Ea." + detailsAL.get(2) + ", Qty:" + detailsAL.get(3) + ", Total:" + detailsAL.get(5));

            //update cart
            item1.setText(cartItems.get(0));
            item2.setText(cartItems.get(1));
            item3.setText(cartItems.get(2));
            item4.setText(cartItems.get(3));
            item5.setText(cartItems.get(4));

            itemCount++;

            //update tags + cart label
            if(itemCount <= 4){
                idTag.setText("Enter ID for Item #" + (itemCount+1) + ":");
                quantTag.setText("Enter Quantity for Item #" + (itemCount+1) + ":");
                detailTag.setText("Details for Item #" + (itemCount) + ":");
                subtotalTag.setText("Current Subtotal for " + (itemCount) + " Item(s):");
                runButton.setText("Search For Item #" + (itemCount+1));
                addButton.setText("Add Item #" + (itemCount+1) + " to Cart");
                cartLabel.setText("Your Shopping Cart Currently Contains " + (itemCount) + " Item(s)");
            }

            //update txtboxes
            idText.setText("");
            quantText.setText("");
            subtotalText.setText("$" + df.format(cartTotal));
            detailText.setText(tempDetail);

            //change btn states
            if(itemCount <= 4){
                runButton.setEnabled(true);
                addButton.setEnabled(false);
            }
            else{
                runButton.setEnabled(false);
                addButton.setEnabled(false);
            }

            if(cartItems.get(0) != ""){
                deleteButton.setEnabled(true);
                checkoutButton.setEnabled(true);
            }
        }
    }
    private class DeleteButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            itemCount--;
            cartTotal -= subt[itemCount];//subtract prev item's subtotal from total

            //reset curr index's value
            subt[itemCount] = 0.0;
            cartItems.set(itemCount, "");
            cartOutput[itemCount] = "";

            //update cart
            item1.setText(cartItems.get(0));
            item2.setText(cartItems.get(1));
            item3.setText(cartItems.get(2));
            item4.setText(cartItems.get(3));
            item5.setText(cartItems.get(4));

            //update tags & btns
            idTag.setText("Enter ID for Item #" + (itemCount+1) + ":");
            quantTag.setText("Enter Quantity for Item #" + (itemCount+1) + ":");
            detailTag.setText("Details for Item #" + (itemCount+1) + ":");
            subtotalTag.setText("Current Subtotal for " + (itemCount) + " Item(s):");
            runButton.setText("Search For Item #" + (itemCount+1));
            addButton.setText("Add Item #" + (itemCount+1) + " to Cart");
            subtotalText.setText("");
            cartLabel.setText("Your Shopping Cart Currently Contains " + (itemCount) + " Item(s)");

            //update text
            idText.setText("");
            quantText.setText("");
            detailText.setText("");

            if(itemCount >= 1){
                subtotalText.setText("$" + df.format(cartTotal));
            }
            else{
                subtotalText.setText("");
            }

            //update btn states
            if(itemCount == 0){
                checkoutButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
            if(itemCount <= 4){
                runButton.setEnabled(true);
            }
        }
    }
    private class EmptyButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //empty subtotal arr & cart arr list
            for(int i = 0; i < itemCount; i++){
                subt[i] = 0.0;
                cartItems.set(i, "");
                cartOutput[i] = "";
            }

            itemCount = 0;//reset item count
            cartTotal = 0.0;//reset cart total

            //update cart 
            item1.setText("");
            item2.setText("");
            item3.setText("");
            item4.setText("");
            item5.setText("");

            //update tags, btns, & subtotal text
            idTag.setText("Enter ID for Item #" + (itemCount+1) + ":");
            quantTag.setText("Enter Quantity for Item #" + (itemCount+1) + ":");
            detailTag.setText("Details for Item #" + (itemCount+1) + ":");
            subtotalTag.setText("Current Subtotal for " + (itemCount) + " Item(s):");
            runButton.setText("Search For Item #" + (itemCount+1));
            addButton.setText("Add Item #" + (itemCount+1) + " to Cart");
            detailText.setText("");
            subtotalText.setText("");
            cartLabel.setText("Your Shopping Cart Currently Contains " + (itemCount) + " Item(s)");

            //reset btn states
            runButton.setEnabled(true);
            checkoutButton.setEnabled(false);
        }
    }
    private class CheckoutButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
      
           try(FileWriter fw = new FileWriter("transactions.csv", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);){
                    
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");  
                    Date dateID = new Date();
                    Date date = new Date();

                    idText.setEditable(false);
                    quantText.setEditable(false);

                    for(int i = 0; i < 5; i++){
                        if(cartOutput[i] == null){
                            cartOutput[i] = "empty";
                        }
                        else if(cartOutput[i] != "empty"){
                            cartOutput[i] = (i+1) + ". " + cartOutput[i];
                        }
                    }
                    String output = String.join("\n", cartOutput);
                    output = output.replaceAll("empty", "");

                    JOptionPane.showMessageDialog(rootPane, "Date: " + date + "\n\nNumber of Line Items: " + (itemCount) + "\n\nItem# / ID / Title / Price / Qty / Discount % / Subtotal: \n\n" + output + "Order Subtotal: $" + df.format(cartTotal) + "\n\nTax Rate: 6% \n\nTax Amount: $" + df.format(cartTotal * 0.06) + "\n\nORDER TOTAL: $" + df.format(cartTotal + (cartTotal*0.06)) + "\n\nThanks for shopping at Nile.com!", "Nile.com - Final Invoice", JOptionPane.INFORMATION_MESSAGE);

                    for(int i = 0; i< itemCount; i++){
                        out.println(formatter.format(dateID) + cartOutput[i] +", " + date);
                    }
                    out.println("\n");

           }
           catch(IOException ioException){
            JOptionPane.showMessageDialog(null, "Error: Problem writing to file", "Nile.com - ERROR", JOptionPane.ERROR_MESSAGE); 
        }
        }
    }
    private class ExitButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }

    //main(): application entry point
    public static void main(String[] args) {
        JFrame aNewStore = new nileGUI(); //create frame object
        aNewStore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aNewStore.setVisible(true); //display gui


    }//end main
}//end class nileGUI

