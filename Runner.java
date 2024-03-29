import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.awt.*;

/*
 * Percolation Runner
 * October 10, 2019
 * Elven Shum
 */

public class Runner {
    private static int N = 20;
    private static int reps = 1; //repetitions of algorithms -- how many times you want to run it
    private static algos[] chosenAlg; //array for storing which algorithm user has chosen

    //enum to store algos and reference
    private enum algos {
        QuickFind, QuickUnion, WeightedQuickUnion, QuickUnionPathCompression
    }



    public static void main(String[] args) {
        //UI
        //Prompts Grid Length, Which Algorithm, and Repetition number
        StdOut.println("Elven's Percolation Runner");
        StdOut.println("Please Enter Grid Length:");
        N = StdIn.readInt();
        StdOut.println("Which algorithm would you like to test?");
        StdOut.println("1 : QuickFind");
        StdOut.println("2 : QuickUnion");
        StdOut.println("3 : WeightedQuickUnion");
        StdOut.println("4 : WeightedQuickUnion w/ Path Compression");
        StdOut.println("5 : Simulate all of the above");
        StdOut.println("Please input the number corresponding to your chosen algorithm.");
        int algSelection = StdIn.readInt();
        if (!(algSelection > 0 && algSelection < 6)) {
            StdOut.println("You have failed to enter a valid algorithm number, defaulting to simulating all algorithms");
            algSelection = 1;
        }
        StdOut.print("Input how many repetitions of algorithm #"+algSelection+" to simulate: ");
        reps = StdIn.readInt();

        //uses another array of algos for Confirmed Chosen  Algorithm
        chosenAlg = new algos[1];
        switch (algSelection) {
            case 1:
                chosenAlg[0] = algos.QuickFind;
                break;
            case 2:
                chosenAlg[0] = algos.QuickUnion;
                break;
            case 3:
                chosenAlg[0] = algos.WeightedQuickUnion;
                break;
            case 4:
                chosenAlg[0] = algos.QuickUnionPathCompression;
                break;
            case 5:
                chosenAlg = new algos[4];
                chosenAlg[0] = algos.QuickFind;
                chosenAlg[1] = algos.QuickUnion;
                chosenAlg[2] = algos.WeightedQuickUnion;
                chosenAlg[3] = algos.QuickUnionPathCompression;
                break;
        }
        StdOut.println("Runner currently running " + reps+ "reps of algorithm #"+algSelection);

        long start, end;

        //runs through the chosenAlg[] runs
        for (algos currentAlg : chosenAlg) {
            start = System.currentTimeMillis();
            double total = 0;
            for (int j = 0; j < reps; j++) {
                //Calculate Percolate for each currentAlg
                total += calcPercolate(currentAlg);
            }
            end = System.currentTimeMillis();
            StdOut.println(currentAlg + "\nAverage Percolation Probability: " + total / reps / (N * N));
            StdOut.println("Time Taken: " + (end - start) + " ms\n");
        }

    }
    private static Percolation perc;
    private static int calcPercolate(algos currentAlg) {
        perc = new Percolation(N);

        //reads the Current Algorithm
        switch (currentAlg) {
            case QuickFind:
                perc.setAlgorithm(new QuickFind(perc.connected));
                break;
            case QuickUnion:
                perc.setAlgorithm(new QuickUnion(perc.connected));
                break;
            case WeightedQuickUnion:
                perc.setAlgorithm(new WeightedQuickUnion(perc.connected));
                break;
            case QuickUnionPathCompression:
                perc.setAlgorithm(new QuickUnionPathCompression(perc.connected));
                break;
        }

        int rRow = 0, rCol = 0;
        int count = 0;
        while (!perc.percolates()) {
            // Picks random site to free
            do {
                rRow = (int) (Math.random() * (N));
                rCol = (int) (Math.random() * (N));
            } while (perc.isOpen(rRow, rCol));
            perc.open(rRow, rCol);
            count++;
        }
        return count;
    }

}
