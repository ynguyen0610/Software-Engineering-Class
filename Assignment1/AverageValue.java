  /* 
   * Desc:
   *    The main driver program for Assignment 1.
   *    This program takes two command line arguments: an integer N, and the name of a file. 
   *    It then calculates and display the average of the numerical values in the (0-based) Nth field of each line of the file.
   * @author ynguyen
   * Modified: Feb 2, 2022
   */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AverageValue {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Missing argument");
            System.exit(0);
        }
        int nArgument = 0;
        try {
            nArgument = Integer.parseInt(args[0]); 
        }
        catch (NumberFormatException nfe) {
            System.err.println("Invalid data");
            System.exit(0);
        }

        if (nArgument < 0) {
            System.out.println("Invalid n input"); // if the user inputs a negative number as n 
            System.exit(0);
        }

        try {
            File file = new File(args[1]); 
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            List<String> rows = new ArrayList<>();
            double result = 0;
            double total = 0;

            while ((line = br.readLine()) != null) {
                rows.add(line);
            }
            
            if (rows.size() == 0) { 
                System.out.println("Invalid data"); // if the list is empty because the file didn't read successfully 
                System.exit(0);
            }

            if (nArgument >= rows.size()) { 
                System.out.println("Invalid n input"); // if the user inputs an n that is larger than the file size
                System.exit(0);
            }

            int validRows = 0;

            for (String row : rows) {
                if (row == null) {
                    System.exit(0);
                }

                String[] numbers = row.split(",");

                if (numbers.length <= nArgument) {
                    continue;
                }

                if (numbers[nArgument] != null) { 
                    try {
                        double number = Double.parseDouble(numbers[nArgument]);
                        total += number;
                        validRows++;
                    }
                    catch (NumberFormatException e) {
                        System.exit(0);
                    }
                }
            }
            result = total / validRows;
            // result = total / rows.size();
            System.out.println(result);
        }
        catch (IOException ioe) {
            System.err.println("Invalid data");
        }
    }
}
