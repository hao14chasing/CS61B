import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private int sizeNumber;
    private int vitualBottom;
    private int virtualTop;
    private WeightedQuickUnionUF table;
    private WeightedQuickUnionUF anotherTable;
    private int openSpot;
    private boolean[] spot;

    public static final int TEST_DATA = -10;


    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        sizeNumber = N;
        vitualBottom = N * N + 1;
        virtualTop = N * N;
        openSpot = 0;
        table = new WeightedQuickUnionUF((N * N + 2));
        anotherTable = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 0; i < sizeNumber; i++) {
            table.union(virtualTop, i);
        }
        for (int i = sizeNumber * (sizeNumber - 1); i < sizeNumber * sizeNumber; i++) {
            anotherTable.union(vitualBottom, i);
        }
        spot = new boolean[N * N + 2];

    }

    public void open(int row, int col) {
        attention(row, col);
        int number = xyTo1D(row, col);
        if (!spot[number]) {
            spot[number] = true;
            connect(row, col);
            openSpot++;
        }
    }

    public boolean isOpen(int row, int col) {
        attention(row, col);
        int number = xyTo1D(row, col);
        return spot[number];
    }

    public boolean isFull(int row, int col) {

        attention(row, col);
        return (isOpen(row, col) && table.connected(virtualTop, xyTo1D(row, col)));
    }

    public int numberOfOpenSites() {

        return openSpot;
    }

    public boolean percolates() {

        return anotherTable.connected(virtualTop, vitualBottom);
    }


    private int xyTo1D(int i, int j) {
        return sizeNumber * i + j;
    }

    private void attention(int i, int j) {
        if (i < 0 || j < 0 || i > sizeNumber - 1 || j > sizeNumber) {
            throw new IndexOutOfBoundsException();
        }
    }

    //top,bottom,left,right
    private int[] checkAround(int i, int j) {
        int[] track = {TEST_DATA, TEST_DATA, TEST_DATA, TEST_DATA};
        if (i == 0 && j == 0) {
            track[0] = xyTo1D(i, j);
        }
        if (i > 0) {
            track[0] = xyTo1D(i - 1, j);
        }
        if (i < sizeNumber - 1) {
            track[1] = xyTo1D(i + 1, j);
        }
        if (j > 0) {
            track[2] = xyTo1D(i, j - 1);
        }
        if (j < sizeNumber - 1) {
            track[3] = xyTo1D(i, j + 1);
        }
        return track;
    }

    private void connect(int i, int j) {
        for (int x: checkAround(i, j)) {
            if (x != TEST_DATA && spot[x]) {
                table.union(xyTo1D(i, j), x);
                anotherTable.union(xyTo1D(i, j), x);
                if (table.connected(virtualTop, x)) {
                    table.union(virtualTop, xyTo1D(i, j));
                    anotherTable.union(virtualTop, xyTo1D(i, j));
                }
            }
        }
    }
}
