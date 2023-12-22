package knightworld;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

/**
 * Draws a world consisting of knight-move holes.
 */
public class KnightWorld {

    private TETile[][] tiles;


    public KnightWorld(int width, int height, int holeSize) {
        tiles = new TETile[width][height];
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < height; j += 1) {
                if (isProperPlaceForHole(i, j, holeSize)) {
                    tiles[i][j] = Tileset.NOTHING;
                } else {
                    tiles[i][j] = Tileset.LOCKED_DOOR;
                }
            }
        }
        /*checkLastEdge(tiles, holeSize);*/
    }

    public boolean isProperPlaceForHole(int width, int height, int holeSize) {
        if (height / holeSize % 5 == 0) {
            if (width / holeSize % 5 == 3) {
                return true;
            }
        }
        if (height / holeSize % 5 == 1) {
            if (width / holeSize % 5 == 0) {
                return true;
            }
        }
        if (height / holeSize % 5 == 2) {
            if (width / holeSize % 5 == 2) {
                return true;
            }
        }
        if (height / holeSize % 5 == 3) {
            if (width / holeSize % 5 == 4) {
                return true;
            }
        }
        if (height / holeSize % 5 == 4) {
            if (width / holeSize % 5 == 1) {
                return true;
            }
        }
        return false;
    }

    /*public void checkLastEdge(TETile[][] tiles, int holeSize) {
        for (int i = 0; i < tiles.length; i += 1) {
            int j = tiles[0].length - 1;
            if (tiles[i][j].equals(Tileset.NOTHING)) {
                checkIfIsConcreteDeterminedByLastRow(i, tiles, holeSize);
            }
        }

        for (int q = 0; q < tiles[0].length; q +=1) {
            int p = tiles.length - 1;
            if (tiles[p][q].equals(Tileset.NOTHING)) {
                checkIfIsConcreteDeterminedByLastCol(q, tiles, holeSize);
            }
        }
    }

    public void checkIfIsConcreteDeterminedByLastCol(int rowIndex, TETile[][] t, int holeSize) {
        for (int i = t.length - 1; i > tiles.length - holeSize; i -= 1) {
            if (t[i][rowIndex].equals(Tileset.LOCKED_DOOR)) {
                for (int k = t.length - 1; k > tiles.length - holeSize; k -= 1) {
                    t[k][rowIndex] = Tileset.LOCKED_DOOR;
                }
            }
        }
    }
    public void checkIfIsConcreteDeterminedByLastRow(int colIndex, TETile[][] t, int holeSize) {
        for (int i = t[0].length - 1; i > t[0].length - holeSize; i -= 1) {
            if (t[colIndex][i].equals(Tileset.LOCKED_DOOR)) {
                for (int k = t[0].length - 1; k > t[0].length - holeSize; k -= 1) {
                    t[colIndex][k] = Tileset.LOCKED_DOOR;
                }
            }
        }
    }*/




    /** Returns the tiles associated with this KnightWorld. */
    public TETile[][] getTiles() {
        return tiles;
    }

    public static void main(String[] args) {
        // Change these parameters as necessary
        int width = 20;
        int height = 20;
        int holeSize = 2;

        KnightWorld knightWorld = new KnightWorld(width, height, holeSize);

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        ter.renderFrame(knightWorld.getTiles());

    }
}
