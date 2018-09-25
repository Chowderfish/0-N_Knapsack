package algorithms;

public interface Knapsack {

    /**
     * The only method that must be defined by the instantiating class.
     * @return integer representing the total weight capacity of this algorithms.Knapsack
     */
    int totalAllowedWeight();

    /**
     * The only default method of this interface which should be called externally.
     * @param allItems array of Items with which the algorithms.Knapsack may be filled
     * @return algorithms.Item[] array of the items that achieve the best possible value within the weight limit of this algorithms.Knapsack
     */
    Item[] fillKnapsack(Item[] allItems);
}
