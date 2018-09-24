package algorithms;

public interface ZeroNKnapsackGraphSearch extends Knapsack {

    /**
     * The only default method of this interface which should be called externally.
     * @param allItems array of Items with which the Knapsack may be filled
     * @return Item[] array of the items that achieve the best possible value within the weight limit of this Knapsack
     */
    default Item[] fillKnapsack(Item[] allItems) {
        return new Item[0];
    }
}
