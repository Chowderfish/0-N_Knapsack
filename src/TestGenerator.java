import java.util.Scanner;

public interface TestGenerator {

    static void runAllTests(Test[] tests, Knapsack knapsack) {
        int n = 1;
        for (Test test : tests){
            System.out.println("==============================");
            System.out.println("Test "+n+":");
            test.run(knapsack);
            n++;
        }
    }

    static Test[] generateTests(int numTests, int itemsPerTest, int knapsackWeight) {
        Test[] tests = new Test[numTests];
        for (int i = 0; i < numTests; i++) tests[i] = randomTest(itemsPerTest, knapsackWeight);
        return tests;
    }

    static Test randomTest(int numItems, int knapsackWeight) {
        Item[] items = new Item[numItems];
        for (int i = 0; i < numItems; i++) items[i] = randomItem(knapsackWeight);
        return new Test() {
            @Override
            public Item[] getItems() {
                return items;
            }
        };
    }

    static Item randomItem(int knapsackWeight) {
        double randomWeight = 1 + Math.random() * (knapsackWeight-1);
        double randomValue = Math.random() * 100;
        return new Item() {
            @Override
            public int getWeight() {
                return (int)Math.round(randomWeight);
            }

            @Override
            public int getValue() {
                return (int)Math.round(randomValue);
            }
        };
    }

    static void main(String[] args) {
        String nameForConsole = "01knapsack: ";
        int _numTests = -1, _numItemsPerTest = -1, _knapsackWeight = -1;
        Scanner scanner = new Scanner(System.in);
        while (_numTests == -1 || _numItemsPerTest == -1 || _knapsackWeight == -1) {
            if (_numTests == -1)
                System.out.println(nameForConsole + "Specify the number of tests you would like to run");
            else if (_numItemsPerTest == -1)
                System.out.println(nameForConsole + "Specify the number of items per test");
            else if (_knapsackWeight == -1)
                System.out.println(nameForConsole + "Specify the weight capacity of the knapsack");
            String input = scanner.nextLine();
            try {
                int arg = Integer.parseInt(input);
                if (arg < 0) throw new NumberFormatException();
                if (_numTests == -1) _numTests = arg;
                else if (_numItemsPerTest == -1) _numItemsPerTest = arg;
                else if (_knapsackWeight == -1) _knapsackWeight = arg;

                if (_numTests != -1 && _numItemsPerTest != -1 && _knapsackWeight != -1) break;
            } catch (NumberFormatException e) {
                System.out.println(nameForConsole+"Please input a valid number (a positive integer)");
            }
        }
        final int knapsackWeight = _knapsackWeight;
        Knapsack knapsack = new Knapsack() {
            @Override
            public int totalAllowedWeight() {
                return knapsackWeight;
            }
        };
        Test[] tests = generateTests(_numTests, _numItemsPerTest, _knapsackWeight);
        runAllTests(tests, knapsack);
    }
}
