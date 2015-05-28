package walsh_project5;

/**
 * Defines the methods required to implement a hash table of nodes by implementing the
 * Hashable interface.
 *
 * @author Brad Walsh - N00150149
 */
public class HashTable {

    // String.format() template used when displaying records
    private static final String STATE_RECORD_FORMAT
            = "%1$-15s %2$-15s %3$-5s %4$11s   %5$-15s  %6$s \n";
    private int size;           // Size of array
    private Node[] hashTable;

    /**
     * Constructor - Initializes the HashTable to a specific sized array.
     *
     * @param size Size of the array to build the HashTable on.
     */
    public HashTable(int size) {
        this.size = size;
        hashTable = new Node[size];
    }

    /**
     * Displays the items stored in the hash table. Moves forward through the array in
     * 'ascending' mode, backward otherwise. However, each list remains ordered as
     * originally inserted.
     *
     * @param ascending True if array traversed from 0, false if from size-1.
     */
    public void display(boolean ascending) {
        Node temp;
        System.out.println("Hash Table List:");
        System.out.format(STATE_RECORD_FORMAT, "State", "Capital", "Abbr", "Population",
                "Region", "Region #");
        int start, end, inc;

        if (ascending) {
            start = 0;
            end = size;
            inc = 1;
        } else {
            start = size - 1;
            end = -1;
            inc = -1;
        }

        for (int i = start; i != end; i += inc) {
            System.out.format("Index %d:\n", i);
            temp = hashTable[i];

            if (temp == null) {
                System.out.println("None");
            }

            while (temp != null) {
                System.out.println(temp.getState().toString());
                temp = temp.getNext();
            }
            System.out.println();
        }
    }

    /**
     * Determines the hash index based on the input value (a state name).
     *
     * The hash function MUST meet the following requirements: - Calculate the sum of all
     * the character values in the state name (i.e., A = 65, B = 66, etc.) - Calculate the
     * modulus of this sum using the size of the hash table array (i.e., sum % 10)
     *
     * @param value The value to calculate the hash index.
     * @return the hash index based on the input value.
     */
    public int getHash(String value) {
        int hashValue = 0;
        for (char letter : value.toCharArray()) {
            hashValue += letter;
        }
        return hashValue % size;
    }

    /**
     * Adds an item to the appropriate location of the hash table.
     *
     * @param item The item to add.
     */
    public void insert(Node item) {
        int hash = getHash(item.getState().getName());
        Node temp = hashTable[hash];

        if (isEmpty(hash)) {
            hashTable[hash] = item;
        } else if (item.getState().getName().compareToIgnoreCase(temp.getState().getName()) < 0) {
            item.setNext(temp);
            hashTable[hash] = item;
        } else {
            while (temp.getNext() != null
                    && temp.getNext().getState().getName().compareToIgnoreCase(item.getState().getName()) < 0) {
                temp = temp.getNext();
            }
            item.setNext(temp.getNext());
            temp.setNext(item);
        }
    }

    /**
     * Determines if the specified index of the hash table is empty.
     *
     * @param index The index of the hash table to test.
     * @return True if the specified index of the hash table is empty; otherwise, false.
     */
    public boolean isEmpty(int index) {
        return hashTable[index] == null;
    }

    /**
     * Locates the specified state in the hash table.
     *
     * @param name The state name to find in the hash table.
     * @return A message indicating whether the state was found in the hash table, and the
     * hash and position of the state, if found.
     */
    public String findState(String name) {
        int pos = 0;
        int hash = getHash(name);
        Node temp = hashTable[hash];

        while (temp != null
                && !temp.getState().getName().equalsIgnoreCase(name)) {
            temp = temp.getNext();
            pos++;
        }
        return (temp == null)
                ? String.format("%s was not found in the hash table", name)
                : String.format("%s location is Hash: %d Position %d", name, hash, pos);
    }

    /**
     * Removes the next item from the hash table and returns it.
     *
     * @return The item that was removed.
     */
    public Node remove() {
        Node removedNode = null;
        int currentHashMarker = 0;

        while (currentHashMarker < size && isEmpty(currentHashMarker)) {
            currentHashMarker++;
        }

        if (currentHashMarker < size) {
            removedNode = hashTable[currentHashMarker];
            hashTable[currentHashMarker] = hashTable[currentHashMarker].getNext();
        }

        return removedNode;
    }
}
