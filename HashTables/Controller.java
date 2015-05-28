package walsh_project5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Controls operational flow of the program.
 *
 * @author Brad Walsh - N00150149
 */
public class Controller {

    // Input files
    private final String INPUT_FILE = "States.Input.txt";
    private final String QUERY_FILE = "States.Query.txt";
    private final String OUTPUT_FILE = "State.Output.txt";

    private final boolean IS_QUERY_FILE = true;
    private final boolean ASCENDING = true;
    private HashTable hashTable;
    private String[] strArr;

    /**
     * Controls operational flow of the program. This method is called by the main()
     * method of the application.
     */
    public void execute() {
        // Init program
        hashTable = new HashTable(10);
        strArr = new String[10];

        // Load data from file to HashTable, proceed if successful
        if (loadDataFromFile(INPUT_FILE, !IS_QUERY_FILE)) {
            hashTable.display(ASCENDING);
            hashTable.display(!ASCENDING);

            // Load data from file to array, proceed if successful
            if (loadDataFromFile(QUERY_FILE, IS_QUERY_FILE)) {
                searchHashTableUsingStringArray();
                writeHashTableToFile(OUTPUT_FILE);
            }
        }
    }

    /**
     * Loads the input text file that contains State records and inserts them into the
     * HashTable. Accommodates both fixed width and comma delimited record formats.
     *
     * @param fileName Name of the file to be loaded
     * @return True if load successful, False if an exception occurred
     */
    private boolean loadDataFromFile(String fileName, boolean isQueryFile) {
        BufferedReader stateInputFileBR = null;
        boolean isCommaDelimited, openedSuccessfully = true;
        int arrCounter = 0;

        try {
            stateInputFileBR = new BufferedReader(new FileReader(fileName));

            // Load State records from file 
            while (stateInputFileBR.ready()) {
                String record = stateInputFileBR.readLine();
                isCommaDelimited = record.contains(",");

                if (isQueryFile) {
                    strArr[arrCounter++] = record;
                } else if (isCommaDelimited) {
                    hashTable.insert(new Node(new State(record.split(","))));
                } else {
                    hashTable.insert(new Node(new State(record)));
                }
            }
        } catch (IOException e) {
            System.out.println("***IO Exception - Check input files***");
            openedSuccessfully = false;
        } finally {                             // Clean up
            if (stateInputFileBR != null) {
                try {
                    stateInputFileBR.close();
                } catch (IOException ex) {
                    System.out.println("***Error closing Input file***");
                }
            }
        }

        return openedSuccessfully;  // false if there were any issues opening the file
    } // end loadDataFromFile()

    /**
     * Searches the HashTable for the state names in strArr and prints to display a
     * message based on whether they are found and if so where.
     */
    private void searchHashTableUsingStringArray() {
        System.out.println("State Locations:");

        for (int i = 0; i < strArr.length; ++i) {
            System.out.println(hashTable.findState(strArr[i]));
        }
    }

    /**
     * Writes the records in the HashTable to a file.
     *
     * @param filename String filename of file to be written to
     */
    private void writeHashTableToFile(String filename) {
        // portable newline character
        String newline = System.getProperty("line.separator");
        Node currentNode;

        BufferedWriter outputFileBW = null;

        try {
            outputFileBW = new BufferedWriter(new FileWriter(filename));
            while ((currentNode = hashTable.remove()) != null) {
                outputFileBW.write(currentNode.getState().toString());
                outputFileBW.write(newline);
            }
        } catch (IOException ex) {
            System.out.format("***Error writing to %s***", filename);
        } finally {                         // Clean up
            if (outputFileBW != null) {
                try {
                    outputFileBW.close();
                } catch (IOException ex) {
                    System.out.format("***Error closing %s***", filename);
                }
            }
        }
    } // end writeHashTableToFile()
} // end class Controller
