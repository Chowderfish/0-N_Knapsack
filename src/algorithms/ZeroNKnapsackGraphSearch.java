package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface ZeroNKnapsackGraphSearch extends Knapsack {

    /**
     * The only default method of this interface which should be called externally.
     * @param allItems array of Items with which the Knapsack may be filled
     * @return Item[] array of the items that achieve the best possible value within the weight limit of this Knapsack
     */
    default Item[] fillKnapsack(Item[] allItems) {
        Node startNode = Node.startNode(allItems);
        Set<Node> visited = new HashSet<>();
        Node best = findBest(startNode, visited);

        ArrayList<Item> items = new ArrayList<>();
        int[] itemCount = best.getNodeItemCount();
        for (int i = 0; i < allItems.length; i++) {
            for (int k = 0; k < itemCount[i]; k++) items.add(allItems[i]);
        }

        return items.toArray(new Item[items.size()]);
    }

    /**
     * Should not be called directly from outside of this interface.
     * @param node Base node denoting an empty Knapsack
     * @return Node returns the result best value node within the weight limit of this Knapsack
     */
    default Node findBest(Node node, Set<Node> visited) {
        for (Node n : visited) {
            if (Arrays.equals(n.getNodeItemCount(), node.getNodeItemCount()))return node;
        }

        visited.add(node);
        Node best = node;
        for (int i = 0; i < node.getNodeItems().length; i++) {
            Node next = Node.newNode(node, i);
            if (next.getNodeWeight() <= totalAllowedWeight()) {
                Node temp = findBest(next, visited);
                best = temp.getNodeValue() > best.getNodeValue() ? temp : best;
            }
        }
        return best;
    }

    /**
     * Node denotes the state of the Knapsack
     */
    interface Node {
        /**
         * Should not be called directly from outside of this interface.
         * @return integer array which denotes the Items in the Knapsack at this Node
         */
        int[] getNodeItemCount();

        /**
         * Should not be called directly from outside of this interface.
         * @return Item array which maps to each index of the array returned by getNodeItemCount()
         */
        Item[] getNodeItems();

        /**
         *
         * @param items with which the Knapsack can be filled
         * @return Node which represents the the origin, with fields initialised to match the given set of Items.
         */
        static Node startNode(Item[] items) {
            int[] startCount = new int[items.length];
            Arrays.fill(startCount, 0);
            return new Node() {
                @Override
                public int[] getNodeItemCount() {
                    return startCount;
                }
                @Override
                public Item[] getNodeItems() {
                    return items;
                }
            };
        }

        /**
         * Should not be called directly from outside of this interface.
         * @param origin The Node which this newNode should be based upon
         * @param item The Item which has been added to the origin node at this node.
         * @return the new node representing the Knapsack as shown in the given origin with the addition of the given item.
         */
        static Node newNode(Node origin, int item) {
            int[] nodeCount = origin.getNodeItemCount().clone();
            nodeCount[item]++;
            return new Node() {
                @Override
                public int[] getNodeItemCount() {
                    return nodeCount;
                }
                @Override
                public Item[] getNodeItems() {
                    return origin.getNodeItems();
                }
            };
        }

        /**
         * Should not be called directly from outside of this interface.
         * @return integer representing the total weight of all Item in the Knapsack at this Node.
         */
        default int getNodeWeight() {
            int totalWeight = 0;
            int[] itemCount = getNodeItemCount();
            Item[] items = getNodeItems();
            for (int i = 0; i < getNodeItems().length; i++) {
                totalWeight += items[i].getWeight() * itemCount[i];
            }
            return totalWeight;
        }

        /**
         * Should not be called directly from outside of this interface.
         * @return integer representing the total value of all Item in the Knapsack at this Node.
         */
        default int getNodeValue() {
            int totalValue = 0;
            int[] itemCount = getNodeItemCount();
            Item[] items = getNodeItems();
            for (int i = 0; i < getNodeItems().length; i++) {
                totalValue += items[i].getValue() * itemCount[i];
            }
            return totalValue;
        }
    }
}
