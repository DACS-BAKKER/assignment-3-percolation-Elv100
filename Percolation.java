/*
 * Percolation Class
 * October 10, 2019
 * Elven Shum
 */

public class Percolation {
    public int[] connected;
    public boolean[] blocking;
    private int top;
    private int bottom;
    public UFAlgorithm algo;
    private int size;


    public Percolation(int N){
        //false ByDefault
        blocking = new boolean[N*N+2];
        connected = new int[N*N+2];
        for (int i = 0; i < N*N+2; i++){
            connected[i] = i;
        }
        //associate top and bottom node
        top = N*N;
        bottom = N*N+1;
        size=N;
    }

    public void setAlgorithm(UFAlgorithm currentAlgo){
        algo = currentAlgo;
        //connects top row with top, bottom row with bottom
        for (int i = 0; i < size; i++){
            algo.union(i, top);
            algo.union(size*(size-1)+i, bottom);
        }
    }

    public void open(int i, int j){
        blocking[calcPos(i,j)] = true;
        unionAdjacent(calcPos(i,j));
    }
//
    public void unionAdjacent(int p){
        //deal with horizontal unions
        if(p % size ==0){  //left column of grid
            fillableAdj(p, p+1);
        } else if (p % size ==size-1){ //right column of grid
            fillableAdj(p, p-1);
        } else { //middle columns
            fillableAdj(p,p-1);
            fillableAdj(p, p+1);
        }
        //deal with vertical unions
        if (p < size){ //top row
            fillableAdj(p, p+size);
        } else if (p > size*(size-1)){ //bottom row
            fillableAdj(p, p-size);
        } else {  //middle rows
            fillableAdj(p, p+size);
            fillableAdj(p, p-size);
        }

    }
    //makes p and q "fillable,
    //aka unions known-open site to Test iff test is open already
    public void fillableAdj(int known, int test){
        if (blocking[test]){
            algo.union(known, test);
        }
    }

    //checks if site is full aka, connected to top
    public boolean isOpen(int i, int j) {
        return (blocking[calcPos(i,j)]);
    }

    //1x1 is upper left
    public int calcPos(int i, int j){
        return (i)*size+(j);
    }

    public boolean isFull(int i, int j){
        return algo.isConnected(calcPos(i,j), top);
    }
    // is site (row i, column j) full?/does it connect to top?


    public boolean percolates(){
        return algo.isConnected(top, bottom);
    }         // does the system percolate?


    public static void main(String[] args){

    }  // test client



}

