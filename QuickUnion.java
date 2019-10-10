/*
 * QuickUnion
 * the QuickUnion algorithm
 * October 10, 2019
 * Elven Shum
 *
 */


public class QuickUnion extends UFAlgorithm {
    private int[] arr;
    private int size;

    public QuickUnion(int n){
        if (n < 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        arr = new int[n];
        for (int i = 0; i < n; i++){
            arr[i] = i;
        }
        size = n;
    }

    public QuickUnion(int[] arr) {this.arr = arr; size = arr.length;}

    //validatesIndex
    public void validateIndex(int p) {
        if (p < 0 || p >= size) {
            throw new IllegalArgumentException("your index, " + p + " is outside 0 and " + (size - 1));
        }
    }

    //finds "the root of the tree" for some n
    public int treeRoot(int n){
        validateIndex(n);
        while (n != arr[n]){
            n = arr[n];
        }
        return n;
    }

    public void union(int p, int q){
         int rootP = treeRoot(p);
         int rootQ = treeRoot(q);
         //if they're the same, method simply end
         if (rootQ == rootP) return;
         // connects Q's root, to P's root
         arr[rootP] = rootQ;
    }

    public boolean isConnected(int p, int q){
        return treeRoot(p)==treeRoot(q);
    }


}
