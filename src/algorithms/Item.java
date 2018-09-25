package algorithms;

public interface Item {
    /**
     *
     * @return integer representing the weight of this item
     */
    int getWeight();

    /**
     *
     * @return integer representing the value of this item
     */
    int getValue();

    /**
     * Prints the given algorithms.Item onto the System console.
     */
    default void print() {
        System.out.println("[ weight: "+getWeight()+" value: "+getValue()+" ]");
    }
}
