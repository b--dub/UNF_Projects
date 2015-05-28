package walsh_project5;

/**
 * Provides the methods required to implement a linked list of nodes.
 * 
 * This interface MUST be implemented by a class.
 * i.e., public class Node implements Linkable {
 * 
 * @author Jim Littleton
 * @since February 14, 2014
 */
public interface Linkable {
    /**
     * Returns the node to the right of the current node.
     * @return the node to the right of the current node.
     */
    public Node getNext();
    
    /**
     * Returns the state object stored in the current node.
     * @return the state object stored in the current node.
     */
	public State getState();
	
    /**
     * Sets the next pointer to the node to the right of the current node.
     * @param node the node to assign to the next pointer.
     */
    public void setNext(Node node);
}