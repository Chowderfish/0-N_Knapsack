import algorithms.*;

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
        Scanner scanner = new Scanner(System.in);
        String input;
        int mode = -1;
        String nameForConsole;
        System.out.println("Specify which algorithm you would like to run:");
        System.out.println("1.  0-1 algorithms.Knapsack Dynamic Programming Algorithm");
        System.out.println("2.  0-N algorithms.Knapsack Brute Force Algorithm");
        System.out.println("3.  0-N algorithms.Knapsack Dynamic Programming Algorithm");
        System.out.println("4.  0-N algorithms.Knapsack Graph Search Algorithm");
        System.out.println("0.  Benchmark mode (runs all algorithms)");
        while (mode == -1) {
            input = scanner.nextLine();
            try {
                int arg = Integer.parseInt(input);
                if (arg < 0) throw new NumberFormatException();
                mode = arg;
            } catch (NumberFormatException e) {
                System.out.println("Please input a valid option (0-4)");
            }
        }
        switch (mode) {
            case 1: //01knapsack
                nameForConsole = "01knapsack: ";
                break;
            case 2: //0N brute force knapsack
                nameForConsole = "0Nknapsack-bf: ";
                break;
            case 3: //0N dynamic knapsack
                nameForConsole = "0Nknapsack-dp: ";
                break;
            case 4: //0N graph search knapsack
                nameForConsole = "01knapsack-gs: ";
                break;
            default: //Benchmark mode
                nameForConsole = "benchmark: ";
                break;
        }
        int _numTests = -1, _numItemsPerTest = -1, _knapsackWeight = -1;
        while (_numTests == -1 || _numItemsPerTest == -1 || _knapsackWeight == -1) {
            if (_numTests == -1)
                System.out.println(nameForConsole + "Specify the number of tests you would like to run");
            else if (_numItemsPerTest == -1)
                System.out.println(nameForConsole + "Specify the number of items per test");
            else if (_knapsackWeight == -1)
                System.out.println(nameForConsole + "Specify the weight capacity of the knapsack");
            input = scanner.nextLine();
            try {
                int arg = Integer.parseInt(input);
                if (arg <= 0) throw new NumberFormatException();
                if (_numTests == -1) _numTests = arg;
                else if (_numItemsPerTest == -1) _numItemsPerTest = arg;
                else if (_knapsackWeight == -1) _knapsackWeight = arg;

                if (_numTests != -1 && _numItemsPerTest != -1 && _knapsackWeight != -1) break;
            } catch (NumberFormatException e) {
                System.out.println(nameForConsole+"Please input a valid number (a positive integer)");
            }
        }
        final int knapsackWeight = _knapsackWeight;
        Knapsack knapsack;
        switch(mode) {
            case 1: //01knapsack
                knapsack = zeroOneDynamic(knapsackWeight);
                break;
            case 2: //0N brute force knapsack
                knapsack = zeroNBF(knapsackWeight);
                break;
            case 3: //0N dynamic knapsack
                knapsack = zeroNDynamic(knapsackWeight);
                break;
            case 4: //0N graph search knapsack
                knapsack = zeroNGS(knapsackWeight);
                break;
            default: //Benchmark mode
                knapsack = null;
                break;
        }
        long start = System.currentTimeMillis();
        Test[] tests = generateTests(_numTests, _numItemsPerTest, _knapsackWeight);
        String testGenerationOutput = nameForConsole+(_numTests*_numItemsPerTest)+" test items generated across "+_numTests+" tests in "+(System.currentTimeMillis()-start)+"ms";
        if (knapsack != null){
            long algorithmTime = System.currentTimeMillis();
            runAllTests(tests, knapsack);
            algorithmTime = System.currentTimeMillis() - algorithmTime;
            System.out.println(nameForConsole+"Algorithm completed "+_numTests+" tests ("+_numItemsPerTest+" items per test) in "+algorithmTime+"ms");
        }
        else startBenchmark(tests, knapsackWeight, _numTests, _numItemsPerTest);
        System.out.println(testGenerationOutput);
    }

    static void startBenchmark(Test[] tests, int knapsackWeight, int numTests, int itemsPerTest) {
        long zeroOneDynamic = System.currentTimeMillis();
        Knapsack knapsack = zeroOneDynamic(knapsackWeight);
        runAllTests(tests, knapsack);
        zeroOneDynamic = System.currentTimeMillis() - zeroOneDynamic;

        long zeroNBruteForce = System.currentTimeMillis();
        knapsack = zeroNBF(knapsackWeight);
        runAllTests(tests, knapsack);
        zeroNBruteForce = System.currentTimeMillis() - zeroNBruteForce;

        long zeroNDynamic = System.currentTimeMillis();
        knapsack = zeroNDynamic(knapsackWeight);
        runAllTests(tests, knapsack);
        zeroNDynamic = System.currentTimeMillis() - zeroNDynamic;

        long zeroNGS = System.currentTimeMillis();
        knapsack = zeroNGS(knapsackWeight);
        runAllTests(tests, knapsack);
        zeroNGS = System.currentTimeMillis() - zeroNGS;

        String nameForConsole = "benchmark: ";
        System.out.println(
                nameForConsole+"0-1 algorithms.Knapsack Dynamic Programming Algorithm completed in: "+zeroOneDynamic+"ms"+"    (approx. "+(zeroOneDynamic/numTests)+"ms per test)"+
                "\n"+nameForConsole+"0-N algorithms.Knapsack Brute Force Algorithm completed in: "+zeroNBruteForce+"ms"+"    (approx. "+(zeroNBruteForce/numTests)+"ms per test)"+
                "\n"+nameForConsole+"0-N algorithms.Knapsack Dynamic Programming Algorithm completed in: "+zeroNDynamic+"ms"+"    (approx. "+(zeroNDynamic/numTests)+"ms per test)"+
                "\n"+nameForConsole+"0-N algorithms.Knapsack Graph Search Algorithm completed in: "+zeroNGS+"ms"+"    (approx. "+(zeroNGS/numTests)+"ms per test)");
    }

    static Knapsack zeroOneDynamic(int knapsackWeight) {
        return new ZeroOneKnapsackDynamic() {
            @Override
            public int totalAllowedWeight() {
                return knapsackWeight;
            }
        };
    }
    static Knapsack zeroNBF(int knapsackWeight) {
        return new ZeroNKnapsackBruteForce() {
            @Override
            public int totalAllowedWeight() {
                return knapsackWeight;
            }
        };
    }
    static Knapsack zeroNDynamic(int knapsackWeight) {
        return new ZeroNKnapsackDynamic() {
            @Override
            public int totalAllowedWeight() {
                return knapsackWeight;
            }
        };
    }
    static Knapsack zeroNGS(int knapsackWeight) {
        return new ZeroNKnapsackGraphSearch() {
            @Override
            public int totalAllowedWeight() {
                return knapsackWeight;
            }
        };
    }
}
