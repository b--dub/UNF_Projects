package walsh_project5;

/**
 * Provides the methods required to implement a hash table of nodes.
 * 
 * This interface MUST be implemented by a class.
 * i.e., public class HashTable implements Hashable {
 * 
 * @author Jim Littleton
 * @since April 5, 2014
 */
public interface Hashable {
    /**
     * Displays the items stored in the hash table.
     * @param ascending True if items ordered from smallest to largest; otherwise, from 
     * largest to smallest.
     */
    public void display(boolean ascending);
    
	/**
     * Determines the hash index based on the input value (a state name).
	 *
	 * The hash function MUST meet the following requirements:
	 *   - Calculate the sum of all the character values in the state name 
         * (i.e., A = 65, B = 66, etc.)
	 *   - Calculate the modulus of this sum using the size of the hash table array 
         * (i.e., sum % 10)
	 *
     * @param value The value to calculate the hash index.
     * @return the hash index based on the input value.
     */
    public int getHash(String value);
	
    /**
     * Adds an item to the appropriate location of the hash table.
     * @param item The item to add.
     */
    public void insert(Node item);
    
	/**
     * Determines if the specified index of the hash table is empty.
     * @param index The index of the hash table to test.
     * @return True if the specified index of the hash table is empty; otherwise, false.
     */
    public boolean isEmpty(int index);
	
	/**
     * Locates the specified state in the hash table.
     * @param name The state name to find in the hash table.
     * @return A message indicating whether the state was found in the hash table, 
     * and the hash and position of the state, if found.
     */
	public String findState(String name);
	
    /**
     * Removes the next item from the hash table.
     * @return The item that was removed.
     */
    public Node remove();
}
