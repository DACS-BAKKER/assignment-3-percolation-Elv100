/*
 * QuickFind
 * the QuickFind algorithm
 * October 10, 2019
 * Elven Shum
 *
 */

public class QuickFind extends UFAlgorithm{
    private int[] arr;
    private int size;

    public QuickFind(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        size = n;
    }

    public QuickFind(int[] inarr) {
        arr = inarr;
        size = inarr.length;
    }

    //this method is sorely unnecessary, but it used for the sake of organization and the "extends"
    public int treeRoot(int i){
        return arr[i];
    }
    //validatesIndex
    public void validateIndex(int p) {
        if (p < 0 || p >= size) {
            throw new IllegalArgumentException("your index, " + p + " is outside 0 and " + (size - 1));
        }
    }

    public boolean isConnected(int p, int q){
        validateIndex(p);
        validateIndex(q);
        return (arr[p]==arr[q]);
    }

    public void union(int p, int q){
        validateIndex(p);
        validateIndex(q);
        //reduces need to constantly check array
        int arrP = arr[p];
        int arrQ = arr[q];
        if (arrQ == arrP) return;

        for(int i = 0; i < size; i++){
            if (arr[i]==arrP){
                arr[i] = arrQ;
            }
        }
    }

    public static void main (String[] args){
    }
}
