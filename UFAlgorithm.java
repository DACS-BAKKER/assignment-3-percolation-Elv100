/*
 * UFAlgorithm
 * An abstract class for the 4 algorithms
 * Allows specific algorithm to be passed as an input in Percolation
 * October 8, 2019
 * Elven Shum
 *
 * Idea for Abstract Class from Alex Yuk and Kevin Gu
 * Borrowed with permission
 */

public abstract class UFAlgorithm {

    //determines site i's root
    protected abstract int treeRoot(int i);

    //validates inputted index
    protected abstract void validateIndex(int i);

    //determined if p and q are connected
    protected abstract boolean isConnected(int p, int q);

    //unites (connects) p and q
    protected abstract void union(int p, int q);

}
