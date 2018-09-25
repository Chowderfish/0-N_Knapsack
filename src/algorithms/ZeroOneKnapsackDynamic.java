package algorithms;

import java.util.ArrayList;

public interface ZeroOneKnapsackDynamic extends Knapsack {

    /**
     * The only default method of this interface which should be called externally.
     * @param allItems array of Items with which the algorithms.Knapsack may be filled
     * @return algorithms.Item[] array of the items that achieve the best possible value within the weight limit of this algorithms.Knapsack
     */
    default Item[] fillKnapsack(Item[] allItems) {
        int[][] table = calculateValueTable(allItems);
        return itemsFromValueTable(table, allItems);
    }

    /**
     * Should not be called directly from outside of this interface.
     * @param items array of Items with which the algorithms.Knapsack may be filled
     * @return int[][] table representing the best value fill of the algorithms.Knapsack's weight
     */
    default int[][] calculateValueTable(Item[] items) {
        int[][] table = new int[items.length+1][totalAllowedWeight()+1];

        for (int w = 0; w <= totalAllowedWeight(); w++) table[0][w] = 0; //0 weight means 0 items

        for (int i = 1; i <= items.length; i++) {
            table[i][0] = 0; //0 items has 0 weight
            for (int w = 1; w <= totalAllowedWeight(); w++) {
                if (items[i-1].getWeight() < w) {
                    if (items[i-1].getValue() + table[i-1][w-items[i-1].getWeight()] > table[i-1][w]) {
                        table[i][w] = items[i-1].getValue() + table[i-1][w-items[i-1].getWeight()];
                    } else table[i][w] = table[i-1][w];
                } else table[i][w] = table[i-1][w];
            }
        }
        return table;
    }

    /**
     * Should not be called directly from outside of this interface.
     * @param table int[][] array representing the best value fill of the algorithms.Knapsack's weight
     * @param items algorithms.Item array of the items which achieve the optimal fill of the algorithms.Knapsack
     * @return
     */
    default Item[] itemsFromValueTable(int[][] table, Item[] items) {
        ArrayList<Item> temp = new ArrayList<>();
        int i = items.length;
        int w = totalAllowedWeight();
        while (i > 0 && w > 0) {
            if (table[i][w] != table[i-1][w]) {
                temp.add(items[i-1]);
                w -= items[i-1].getWeight();

            }
            i -= 1;
        }
        return temp.toArray(new Item[temp.size()]);
    }
}
