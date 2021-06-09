import java.awt.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static ArrayList<String> cores = new ArrayList<String>();
    public static ArrayList<String> single = new ArrayList<String>();



    public static void main(String[] args) throws IOException {





        if (args.length < 1) {
            System.out.println("There is no Assembly file to compile!");
            System.exit(1);
        }
        File inputFile = null;
        Scanner input = null;

        try {
            inputFile = new File(args[0]);
            input = new Scanner(inputFile);

        } catch (FileNotFoundException e) {
            System.err.println("No such File!");
            System.exit(1);
        }
        // Initializing files for write operation
        String homeDir = System.getProperty("user.home");
        FileOutputStream single = new FileOutputStream(homeDir + "/Desktop/single-core");
        FileOutputStream core0 = new FileOutputStream(homeDir + "/Desktop/core-0");
        FileOutputStream core1 = new FileOutputStream(homeDir + "/Desktop/core-1");
        single.write("v2.0 raw\n".getBytes());
        core0.write("v2.0 raw\n".getBytes());
        core1.write("v2.0 raw\n".getBytes());


        /*
         * Useful Methods:
         * -To write a string to a FileOutputStream, you can use the following method.
         * core.write("Hello World!".getBytes())
         *
         * -To convert an Integer number to a hexadecimal string, you can use the following method.
         * Integer.toHexString(number)
         *
         * -To convert an Integer number to a binary object, you can use the following method.
         * Integer.parseInt(number, 2);
         */

        // Iterate through file

        // Iterate through file
        System.out.println(cores.toString());
        int counter = 0; // to count every step of cores
        while (input.hasNext()) {
            String inputText = input.nextLine();
            String controlWord = getCW(inputText) + " ";
            single.write(controlWord.getBytes());
            if(cores.get(counter).equals("0")){
                //System.out.println("Core 0");
                //System.out.println(controlWord);
                core0.write(controlWord.getBytes());
            }
            else if(cores.get(counter).equals("1")){
                //System.out.println("Core 1");
                //System.out.println(controlWord);
                core1.write(controlWord.getBytes());

            }
            else{ // X durumu
                //System.out.println("Core 0 " + controlWord);

                //System.out.println("Core 1 " + controlWord);
                core0.write(controlWord.getBytes());
                core1.write(controlWord.getBytes());
            }

            counter+=1;
            /**
             * @TODO Your code goes here. You can follow the steps below to complete the assembler.
             * - Check the @param inputText whether it is for single-core or multi-core
             * - Build a binary string using @see getBinaryString and finaly convert it to hexadecimal
             * - Write the hexadecimal string to the appropriate file.
             */
        } //while end
        // Opening directory of core files.
        Desktop desktop = Desktop.getDesktop();
        desktop.open(new File(homeDir + "/Desktop/single-core"));
        desktop.open(new File(homeDir + "/Desktop/core-0"));
        desktop.open(new File(homeDir + "/Desktop/core-1"));

        // Closing File Streams of files.
        single.close();
        core0.close();
        core1.close();
    }
    /**
     * This helper method gets a menomic and returns the corresponding binary string
     * @param menomic
     * @return binary string
     */
    private static String getOpCode(String menomic) {
        menomic = menomic.toUpperCase();
        switch (menomic) {
            case "BRZ":
                return "0000";
            case "BRN":
                return "0001";
            case "LDI":
                return "0010";
            case "LDM":
                return "0011";
            case "STR":
                return "0100";
            case "ADD":
                return "0101";
            case "SUB":
                return "0110";
            case "MUL":
                return "0111";
            case "DIV":
                return "1000";
            case "NEG":
                return "1001";
            case "LSL":
                return "1010";
            case "LSR":
                return "1011";
            case "XOR":
                return "1100";
            case "NOT":
                return "1101";
            case "AND":
                return "1110";
            case "ORR":
                return "1111";
            case "MOV":
                return "1";
        }
        return null;
    }
    /**
     * This helper method gets a number and returns translates it to a binary string
     * @param number address or an immediate value
     * @return binary version of the given number
     */
    private static String getBinaryString(String number) {
        int num = Integer.parseInt(number);

        String binaryString = Integer.toBinaryString(num);

        if (number.contains("-"))
            binaryString = binaryString.substring(27, 32);

        if (binaryString.length() < 12) {
            int length = binaryString.length();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 12 - length; i++)
                stringBuilder.append("0");
            stringBuilder.append(Integer.toBinaryString(num));
            return stringBuilder.toString();
        } else if (binaryString.length() > 12)
            System.out.println("Number can't be represented with 12 bits!");

        return binaryString;
    }

    /**
     * This helper method gets the input string and returns the control word in hexadecimal form
     * @param inputText a line from the assembly file
     * @return control word in hexadecimal form
     */
    private static String getCW(String inputText) {
        String[] array = inputText.split("\\s");
        String menomic = array[0];
        StringBuilder output = new StringBuilder();
        output.append(getOpCode(menomic));
        //cores list shows which core is
        cores.add(array[1]);

        if (array.length == 2) {
            String address = array[1];
            output.append(getBinaryString(address));
        } else {
            String address = array[2];
            output.append(getBinaryString(address));
            // for MOV method we have one more length(2).
            if (array.length == 4) {
                String address2 = array[3];
                output.append(getBinaryString(address2));
            }


        }
        int temp = Integer.parseInt(output.toString(), 2);
        return Integer.toHexString(temp);
    }

}