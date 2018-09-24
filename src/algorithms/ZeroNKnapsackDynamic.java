package algorithms;

import java.util.Arrays;

public interface ZeroNKnapsackDynamic extends Knapsack {

    /**
     * The only default method of this interface which should be called externally.
     * @param allItems array of Items with which the Knapsack may be filled
     * @return Item[] array of the items that achieve the best possible value within the weight limit of this Knapsack
     */
    default Item[] fillKnapsack(Item[] allItems) {
        Item[][] itemTable = calculateTable(allItems);

        return itemTable[totalAllowedWeight()];
    }

    /**
     * Should not be called directly from outside of this interface.
     * @param items array of Items with which the Knapsack may be filled
     * @return Item[][] table representing the best value fill of the Knapsack at all weight levels
     */
    default Item[][] calculateTable(Item[] items) {
        Item[][] table = new Item[totalAllowedWeight()+1][];

        for (int w = 0; w <= totalAllowedWeight(); w++) {
            for (int i = 0; i < items.length; i++) {
                if (items[i].getWeight() <= w) {
                    Item[] a = table[w];
                    if (a == null) a = new Item[1];
                    int aTotalValue = 0;
                    for (Item item : a) if (item != null) aTotalValue += item.getValue();
                    Item[] b = table[w - items[i].getWeight()];
                    if (b == null) {
                        b = new Item[1];
                    } else {
                        b = Arrays.copyOf(b, b.length + 1);
                    }
                    b[b.length-1] = items[i];
                    int bTotalValue = 0;
                    for (Item item : b) if (item != null) bTotalValue += item.getValue();
                    table[w] = aTotalValue > bTotalValue ? a : b;
                }
            }
        }
        return table;
    }
}
