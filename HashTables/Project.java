/*
 * Author:  Brad Walsh - N00150149
 * Course:  COP 3538
 * Project #: 5
 * Title: Binary Trees
 * Due Date: 4/20/2014
 * 
 * This program demonstrates the storing, displaying, and deleting of data with hash
 * tables as well as transfering data to and from text files.
 */
package walsh_project5;

/**
 * The Project class is the entry point for the program. The main() method displays ID
 * info, instantiates an object of the Controller class, and then calls its execute()
 * method.
 */
public class Project {

    public static void main(String[] args) {
        System.out.println("Brad Walsh - N00150149");
        System.out.println("Project 5 - Hash Tables\n");

        Controller controller = new Controller();

        controller.execute();
    }
}
