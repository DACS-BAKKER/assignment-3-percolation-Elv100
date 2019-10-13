import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.awt.*;

/*
 * Percolation Runner w/ Graphics
 * October 10, 2019
 * Elven Shum
 */

public class RunnerGraphics {
    private static int N;
    private static final int maxN = 40; //max number of N for Graphics
    private static int reps; //repetitions of algorithms -- how many times you want to run it
    private static algos[] chosenAlg;  //array for storing which algorithm user has chosen

    private static final int APPLICATION_SIZE = 500;
    private static double SQR_SIZE = 0.5 / N;

    //makes the 3 different states of sites
    private static final Color openSite = new Color(255, 255, 255);
    private static final Color closedSite = new Color(20, 20, 20);
    private static final Color filledSite = new Color(	16, 	150, 200);

    //enum to store algos and reference
    private enum algos {
        QuickFind, QuickUnion, WeightedQuickUnion, QuickUnionPathCompression
    }

    //draws for given r and c the correct block
    private static void draw(int r, int c) {
        StdDraw.setPenColor (openSite);
        StdDraw.filledSquare(2 * c * SQR_SIZE + SQR_SIZE, 2 * (N - r - 1) * SQR_SIZE + SQR_SIZE, SQR_SIZE);
        StdDraw.setPenColor(filledSite);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (perc.isFull(i, j) && perc.isOpen(i, j)) {
                    StdDraw.filledSquare(2 * j * SQR_SIZE + SQR_SIZE, 2 * (N - i - 1) * SQR_SIZE + SQR_SIZE, SQR_SIZE);
                }
            }
    }



    public static void main(String[] args) {
        //UI
        //Prompts Grid Length, Which Algorithm, and Repetition number
        StdOut.println("Elven's Percolation Runner w/ Graphics");
        StdOut.println("Due to graphics limitations, the max grid length is " + maxN);
        StdOut.println("Please Enter Grid Length:");
        if (N > maxN) {
            StdOut.println("max grid length exceeded, running at grid length = " + maxN);
        }
        N = Math.min(StdIn.readInt(), maxN);
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
        SQR_SIZE = 0.5 / N;
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
        StdOut.println("GraphicsRunner currently running " + reps+ "reps of algorithm #"+algSelection);

        //init's drawing field
        StdDraw.setCanvasSize(APPLICATION_SIZE, APPLICATION_SIZE);
        StdDraw.setPenColor(closedSite);
        StdDraw.filledSquare(APPLICATION_SIZE / 2, APPLICATION_SIZE / 2, APPLICATION_SIZE);

        long start, end;

        //runs through the chosenAlg[] runs
        for (algos currentAlg : chosenAlg) {
            start = System.currentTimeMillis();
            double total = 0;
            for (int j = 0; j < reps; j++) {
            total += calcPercolate(currentAlg);
            }
            end = System.currentTimeMillis();
            StdOut.println(currentAlg + "\nAverage Percolation Probability: " + total / reps / (N * N));
            StdOut.println("Time Taken (with graphics): " + (end - start) + " ms\n");
        }
    }
    private static Percolation perc;

    public static int calcPercolate(algos currentAlg) {
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
            // Picks random site to unblock
            do {
                rRow = (int) (Math.random() * (N));
                rCol = (int) (Math.random() * (N));
            } while (perc.isOpen(rRow, rCol));
            perc.open(rRow, rCol);
            draw(rRow,rCol);
            count++;
        }
        draw(rRow,rCol);
        // Pause time in milliseconds
        StdDraw.pause(1500);
        StdDraw.clear();
        StdDraw.setPenColor(closedSite);
        StdDraw.filledSquare(APPLICATION_SIZE / 2, APPLICATION_SIZE / 2, APPLICATION_SIZE);

        return count;
    }

}
