package walsh_project5;

/**
 * Provides the methods required to implement a linked list of nodes.
 *
 * @author Brad Walsh - N00150149
 */
public class Node implements Linkable {

    private Node next;
    private State state;

    /**
     * Constructor - creates a single Node object with a State object as its data.
     *
     * @param state
     */
    public Node(State state) {
        this.state = state;
        next = null;
    }

    /**
     * Returns the node to the right of the current node.
     *
     * @return the node to the right of the current node.
     */
    @Override
    public Node getNext() {
        return next;
    }

    /**
     * Returns the state object stored in the current node.
     *
     * @return the state object stored in the current node.
     */
    @Override
    public State getState() {
        return state;
    }

    /**
     * Sets the next pointer to the node to the right of the current node.
     *
     * @param node the node to assign to the next pointer.
     */
    @Override
    public void setNext(Node node) {
        next = node;
    }
}
