package algorithms;

import java.util.ArrayList;
import java.util.List;

public interface ZeroNKnapsackBruteForce extends Knapsack {

    /**
     * The only default method of this interface which should be called externally.
     * @param allItems array of Items with which the Knapsack may be filled
     * @return Item[] array of the items that achieve the best possible value within the weight limit of this Knapsack
     */
    default Item[] fillKnapsack(Item[] allItems) {
        List<List<Item>> allSolutions = new ArrayList<>();
        allSolutions = iterativeBF(allItems);

        int best = Integer.MIN_VALUE;
        List<Item> bestSolution = new ArrayList<>();
        for (List<Item> solution : allSolutions) {
            int value = calculateValue(solution);
            if (value > best) {
                best = value;
                bestSolution = solution;
            }
        }
        return bestSolution.toArray(new Item[bestSolution.size()]);
    }

    /**
     * Should not be called directly from outside of this interface.
     * @param items array of items with which the Knapsack may be filled
     * @return List<List<Item>> A list of all solutions - each solution is a list of Item
     */
    default List<List<Item>> iterativeBF(Item[] items) {
        List<List<Item>> allSolutions = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            List<Item> solution = new ArrayList<>();
            if (items[i].getWeight() <= totalAllowedWeight()) solution.add(items[i]);
            for (Item item : items) {
                if (calculateWeight(solution) + item.getWeight() <= totalAllowedWeight()) solution.add(item);
            }
            allSolutions.add(solution);
        }
        return allSolutions;
    }

    /**
     * Should not be called directly from outside of this interface.
     * @param items Takes a List<Item> and calculates the total value of all Item in the List
     * @return int total value of all Item in the list
     */
    default int calculateValue(List<Item> items) {
        int total = 0;
        for (Item item : items) total += item.getValue();
        return total;
    }

    /**
     * Should not be called directly from outside of this interface.
     * @param items Takes a List<Item> and calculates the total weight of all Item in the List
     * @return int total weight of all Item in the list
     */
    default int calculateWeight(List<Item> items) {
        int total = 0;
        for (Item item : items) total += item.getWeight();
        return total;
    }
}
