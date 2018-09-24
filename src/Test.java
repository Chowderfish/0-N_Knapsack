import algorithms.Item;
import algorithms.Knapsack;

public interface Test {
    /**
     *
     * @return Item[] array containing the items for this test
     */
    Item[] getItems();

    default void run(Knapsack knapsack) {
        Item[] bestFill = knapsack.fillKnapsack(getItems());
        int totalWeight = 0, totalValue = 0;
        System.out.println("------------------------------");
        System.out.println("Test contains: ");
        for (Item item : getItems()) item.print();
        System.out.println("------------------------------");
        System.out.println("Knapsack capacity = "+knapsack.totalAllowedWeight());
        for (Item item : bestFill) {
            totalWeight += item.getWeight();
            totalValue += item.getValue();
            item.print();
        }
        System.out.println("Total Weight: "+totalWeight+" | Total Value: "+totalValue);
        System.out.println("------------------------------");
    }
}
