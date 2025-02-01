import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.JOptionPane;

public class filereader {

    public static String[] readInventoryFile(String id, Integer amt){//creates string array to return to GUI

        File inputFile = new File("inventory.csv");
        FileReader inputFileReader = null;
        BufferedReader inputBufReader = null;
        BufferedReader lineCountReader = null;
        String inventoryLine;
        Integer lines = 0;//total num of lines in file
        Integer tempL = 1;//current line
        Integer percent = 0;
        Double discount = 0.0;
        Double subtotal = 0.0;
        Double total = 0.0;
        String[] sep;//temp string to hold split inventory line
        String[] txtbox = new String[3];//final return string*******change this name pls lmao
        ArrayList<String> sepAL;//temp array list, holds items in a line
        String currID;//temp string to hold current line's item ID
        String stockStat;//stock status
        String tempStock;//num in stock
        final DecimalFormat dfZero = new DecimalFormat("0.00");

        try{
            inputFileReader = new FileReader(inputFile);
            inputBufReader = new BufferedReader(inputFileReader);
            lineCountReader = new BufferedReader(new FileReader("inventory.csv"));

            while (lineCountReader.readLine() != null) lines++;//find total num of lines in file
            lineCountReader.close();

            inventoryLine = inputBufReader.readLine();
            
            if(amt>=1 && amt<=4){
                discount = 0.0;
            }
            else if(amt>=5 && amt<=9){
                discount = 0.10;
            }
            else if(amt>=10 && amt<=14){
                discount = 0.15;
            }
            else if(amt>=15){
                discount = 0.20;
            }

            while(inventoryLine != null){//find ID in file
                sep = inventoryLine.split(",");//split inventory line by comma
                sepAL = new ArrayList<>(Arrays.asList(sep));//convert array into array list
                txtbox[0] = id;
                txtbox[1] = Integer.toString(amt);
                currID = sepAL.get(0);
                stockStat = sepAL.get(2);
                tempStock = sepAL.get(3);
                tempStock = tempStock.replaceAll("\s", "");//remove whitespace

                if(id.equals(currID) && stockStat.equals(" true") && (amt <= Integer.parseInt(tempStock))){//in file & in stock & enough stock
                    subtotal = (amt * Double.parseDouble(sepAL.get(4)));
                    total = (subtotal - (subtotal * discount));
                    percent = (int)(discount * 100);
                    txtbox[2] = id + "," + sepAL.get(1) + ", $" + (sepAL.get(4)).replaceAll("\s", "") + ", " + amt + ", " + percent + "%, $" + dfZero.format(total);
                    tempL = 1;//reset back to 1
                    break;//break out of loop
                }
                else if(id.equals(currID) && stockStat.equals(" true") && (amt >= Integer.parseInt(tempStock))){//insufficient stock
                    JOptionPane.showMessageDialog(null, "               Insufficient stock \nOnly " + tempStock + " on hand, please reduce the quantity", "Nile.com - ERROR", JOptionPane.ERROR_MESSAGE); 
                    txtbox[1] = "";//empty amt textbox
                    tempL = 1;
                    break;
                }
                else if(id.equals(currID) && stockStat.equals(" false")){//item not in stock
                    JOptionPane.showMessageDialog(null, "That item is not in stock, please try another item", "Nile.com - ERROR", JOptionPane.ERROR_MESSAGE);
                    txtbox[0] = "";
                    txtbox[1] = "";
                    tempL = 1;
                    break;
                }
                else if(tempL.equals(lines)){//not in file
                    JOptionPane.showMessageDialog(null, "Item ID " + id + " not in file", "Nile.com - ERROR", JOptionPane.ERROR_MESSAGE);
                    txtbox[0] = "";
                    txtbox[1] = "";
                    tempL = 1;
                    break;
                }
                else{
                    tempL++;
                    inventoryLine = inputBufReader.readLine();
                }
            }

            inputBufReader.close();
        }
        catch(FileNotFoundException fileNotFoundException){
            JOptionPane.showMessageDialog(null, "Error: File not found", "Nile.com - ERROR", JOptionPane.ERROR_MESSAGE); 
        }
        catch(IOException ioException){
            JOptionPane.showMessageDialog(null, "Error: Problem reading from file", "Nile.com - ERROR", JOptionPane.ERROR_MESSAGE); 
        }

        return txtbox;
    }

}
