import java.util.ArrayList;
import java.util.List;

public interface ZNKnapsackBruteForce extends Knapsack {

    /**
     * The only default method of this interface which should be called externally.
     * @param allItems array of Items with which the Knapsack may be filled
     * @return Item[] array of the items that achieve the best possible value within the weight limit of this Knapsack
     */
    default Item[] fillKnapsack(Item[] allItems) {
        List<List<Item>> allSolutions = new ArrayList<>();
        allSolutions = bruteForceFill(allSolutions, null, allItems, allItems.length-1);

        int best = Integer.MIN_VALUE;
        List<Item> bestSolution = new ArrayList<>();
        for (List<Item> solution : allSolutions) {
            int value = calculateValue(solution);
            if (value > best) {
                best = value;
                bestSolution = solution;
            }

//            System.out.println("Solution = "+calculateValue(solution)+"(v), "+calculateWeight(solution)+"(w)");
//            for (Item item : solution) {
//                item.print();
//            }
        }
        return bestSolution.toArray(new Item[bestSolution.size()]);
    }

    default List<List<Item>> bruteForceFill(List<List<Item>> allSolutions, List<Item> solution, Item[] items, int n) {
        if (solution == null) {
            solution = new ArrayList<>();
            allSolutions.add(solution);
        }
        if (n == 0) return allSolutions;

        if (calculateWeight(solution) + items[n].getWeight() > totalAllowedWeight()) return bruteForceFill(allSolutions, solution, items, n-1);

        for (int i = 0; i < n; i++) {
            if (calculateWeight(solution) + items[i].getWeight() <= totalAllowedWeight()) {
                List<Item> newSolution = new ArrayList<>(solution);
                allSolutions.add(newSolution);
                newSolution.add(items[i]);
                return bruteForceFill(allSolutions, newSolution, items, n);
            }
        }
        solution.add(items[n]);
        return bruteForceFill(allSolutions, solution, items, n);
    }

    default int calculateValue(List<Item> items) {
        int total = 0;
        for (Item item : items) total += item.getValue();
        return total;
    }

    default int calculateWeight(List<Item> items) {
        int total = 0;
        for (Item item : items) total += item.getWeight();
        return total;
    }
}
