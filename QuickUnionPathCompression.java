/*
 * QuickUnionPathCompression
 * the "weighted quick union w/ path compression" algorithm
 * October 10, 2019
 * Elven Shum
 *
 * Algorithm Intuitively: same as other QuickUnion methods, but now
 * every site is connected Directly to its root.
 */

import edu.princeton.cs.algs4.*;

public class QuickUnionPathCompression extends UFAlgorithm{
    private int[] arr;
    private int size;

    public QuickUnionPathCompression(int n){
        if (n < 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        arr = new int[n];
        for (int i = 0; i < n; i++){
            arr[i] = i;
        }
        size = n;
    }

    public QuickUnionPathCompression(int[] inarr) {
        size = inarr.length;
        arr = inarr;
    }
    public void validateIndex(int p) {
        if (p < 0 || p >= size) {
            throw new IllegalArgumentException("your index, " + p + " is outside 0 and " + (size - 1));
        }
    }

    //connects all checked sites to their root
    public int treeRoot(int n){
        validateIndex(n);
        int root = n;
        //finds root of temp "root"
        while (root != arr[root]){
            root = arr[root];
        }
        //connects all traversed sites to root
        while (n != root){
            int newn = arr[n];
            arr[n] = root;
            n = newn;
        }
        return root;
    }

    public void union (int p, int q){
        int rootP = treeRoot(p);
        int rootQ = treeRoot(q);
        if (rootP == rootQ) return;
        //note, no need to check for size, because they should theoretically be equal.
        arr[rootP] = rootQ;
    }

    public boolean isConnected(int p, int q){
        return treeRoot(p)==treeRoot(q);
    }
    public static void main (String[] args) {
        int n = StdIn.readInt();
        QuickUnionPathCompression uf = new QuickUnionPathCompression(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.isConnected(p, q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
    }
}
