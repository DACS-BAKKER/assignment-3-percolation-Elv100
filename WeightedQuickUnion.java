/*
 * WeightedQuickUnion
 * the "weighted quick union" algorithm
 * October 10, 2019
 * Elven Shum
 *
 * Algorithm Intuitively: same as QuickUnion method, but
 * instead of blindly connecting to root, this connects smaller size
 */


public class WeightedQuickUnion extends UFAlgorithm{
    private int[] arr;
    private int[] branches; //counts number of sites rooted
    private int size;

    public WeightedQuickUnion(int n){
        if (n < 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        arr = new int[n];
        branches = new int[n];
        for (int i = 0; i < n; i++){
            arr[i] = i;
            branches[i] = 1;
        }
        size = n;
    }

    public WeightedQuickUnion(int[] inarr) {
        size = inarr.length;
        arr = inarr;
        branches = new int[inarr.length];
        for (int i = 0; i < size; i++) {
            branches[i] = 1;
        }
    }

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

    public void union (int p, int q){
        int rootP = treeRoot(p);
        int rootQ = treeRoot (q);
        if (rootP == rootQ) return;

        if (branches[rootP] < branches[rootQ]) {
            //connects p-tree to Q's root
            arr[rootP] = rootQ;
            branches[rootQ] += branches[rootP];
        } else {
            arr[rootQ] = rootP;
            branches[rootP] += branches[rootQ];
        }
    }

    public boolean isConnected(int p, int q){
        return treeRoot(p)==treeRoot(q);
    }
}
