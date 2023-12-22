package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TilesGenerator {
    private int width;
    private int height;
    private int startWidth;
    private int startHeight;
    private int playerFirstWidth;
    private int playerFirstHeight;
    private int randomSeed;
    private TETile[][] tiles;
    private TETile[][] tilesCopy;
    private Random random;
    private int totalNumOfTiles;
    private int totalNumOfTilesShouldBeGenerate;
    private NewTileWorld newWorld;
    private boolean isEncounterGenerated = false;
    private boolean isEncounterTouched = false;
    private boolean isCoinAllCollected = false;
    private static boolean isGoodJobShown = false;
    private int numTouchedCoin = 0;
    private int playerOldWorldWidthLoc;
    private int playerOldWorldHeightLoc;

    public TilesGenerator(int width, int height, int seed) {
        this.width = width;
        this.height = height;
        tiles = new TETile[width][height];
        generateNothingInTheTiles();
        randomSeed = seed;
        random = new Random(randomSeed);
        totalNumOfTiles = 0;
        /* We expect to generate total number of floors within
        the interval of (0.333 of totalNumOfTiles, 0.583 of totalNumOfTiles) **/
        totalNumOfTilesShouldBeGenerate = getRemainder(random, width * height / 4) + width * height / 3;
        firstTile(random);
        randomlyGenerateFromTheLastTile();
        wallGenerator();
        tiles[playerFirstWidth][playerFirstHeight] = Tileset.TREE;
    }

    public TilesGenerator(TilesGenerator g) {
        this.tiles = g.tiles;
        this.width = g.width;
        this.height = g.height;
        this.startWidth = g.startWidth;
        this.startHeight = g.startHeight;
        this.playerFirstWidth = g.playerFirstWidth;
        this.playerFirstHeight = g.playerFirstHeight;
        this.totalNumOfTiles = g.totalNumOfTiles;
        this.randomSeed = g.randomSeed;
        this.random = g.random;
        this.totalNumOfTilesShouldBeGenerate = g.totalNumOfTilesShouldBeGenerate;
        this.isEncounterGenerated = g.isEncounterGenerated;
        this.isEncounterTouched = g.isEncounterTouched;
        this.isCoinAllCollected = g.isCoinAllCollected;
        this.playerOldWorldWidthLoc = g.playerOldWorldWidthLoc;
        this.playerOldWorldHeightLoc = g.playerOldWorldHeightLoc;
    }

    public void generateNothingInTheTiles() {
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < height; j += 1) {
                tiles[i][j] = Tileset.NOTHING;
            }
        }
    }

    public void firstTile(Random r) {
        startWidth = getRemainder(r, width - 2) + 1;
        startHeight = getRemainder(r, height - 2) + 1;
        playerFirstWidth = startWidth;
        playerFirstHeight = startHeight;
        tiles[startWidth][startHeight] = Tileset.FLOOR;
        totalNumOfTiles += 1;
    }

    public void randomlyGenerateFromTheLastTile() {
        if (totalNumOfTiles >= totalNumOfTilesShouldBeGenerate) {
            return;
        }
        if (isRoomOrHallway(random)) {
            getRandomStartPointLoc();
            generateRoomInTheNextStep(random);
            randomlyGenerateFromTheLastTile();
        } else {
            getRandomStartPointLoc();
            generateHallwayInTheNextStep(startWidth, startHeight, random);
            randomlyGenerateFromTheLastTile();
        }
    }

    public boolean isRoomOrHallway(Random r) {
        if (getRemainder(r, 3) == 0) {
            return true;
        } else {
            return false;
        }
    }

    //this method generates a hallway
    public void generateHallwayInTheNextStep(int width, int height, Random r) {
        //generate a hallway with width of 1 to 2, and length of 4 to 8.
        //int hallwayDecider = getRemainder(r, 6);
        int hallwayWidth = 1;
        int hallwayLength = getRemainder(r, 8) + 3;
        int hallwayDirection = getRemainder(r, 4);
        // 0: up, 1: right, 2: down, 3: left
        //The hallway is from the tile[width][height]
        // If length that generate from the point in the direction is out of bounds
        // stop generating the hallway.
        if (hallwayDirection == 0) {
            if (height + hallwayLength > tiles[0].length - 1) {
                return;
            }
            for (int i = 0; i < hallwayLength; i++) {
                if (hallwayWidth == 1) {
                    if (notCoverEncounter(width, height + 1)) {
                        tiles[width][height + i] = Tileset.FLOOR;
                    }
                }
            }
        } else if (hallwayDirection == 1) {
            if (width + hallwayLength > tiles.length - 1) {
                return;
            }
            for (int i = 0; i < hallwayLength; i++) {
                if (hallwayWidth == 1) {
                    if (notCoverEncounter(width + i, height)) {
                        tiles[width + i][height] = Tileset.FLOOR;
                    }
                }
            }
        } else if (hallwayDirection == 2) {
            if (height - hallwayLength < 1) {
                return;
            }
            for (int i = 0; i < hallwayLength; i++) {
                if (hallwayWidth == 1) {
                    if (notCoverEncounter(width, height - i)) {
                        tiles[width][height - i] = Tileset.FLOOR;
                    }
                }
            }
        } else if (hallwayDirection == 3) {
            if (width - hallwayLength < 1) {
                return;
            }
            for (int i = 0; i < hallwayLength; i++) {
                if (hallwayWidth == 1) {
                    if (notCoverEncounter(width - i, height)) {
                        tiles[width - i][height] = Tileset.FLOOR;
                    }
                }
            }
        }
        totalNumOfTiles += hallwayWidth * hallwayLength;
    }

    public boolean notCoverEncounter(int widthLoc, int heightLoc) {
        if (!tiles[widthLoc][heightLoc].equals(Tileset.FLOWER)) {
            return true;
        }
        return false;
    }


    // HallwayRoom Line///////////////////////////////////////////////////////////////
    public void generateRoomInTheNextStep(Random r) {
        int widthUpperBound = width / 5;
        int widthLowerBound = 3;
        int widthToGenerate = getRemainder(r, widthUpperBound - widthLowerBound) + widthLowerBound;
        int heightUpperBound = height / 5;
        int heightLowerBound = 3;
        int heightToGenerate = getRemainder(r, heightUpperBound - heightLowerBound) + heightLowerBound;
        randomlyGenerateRoomInOneDirection(startWidth, startHeight, widthToGenerate, heightToGenerate, r);

    }

    public void randomlyGenerateRoomInOneDirection(int lastWidthLoc, int lastHeightLoc,
                                                   int widthToGenerate, int heightToGenerate, Random r) {
        int remainder = getRemainder(r, 4);
        if (remainder == 0) {
            generateRoomUp(lastWidthLoc, lastHeightLoc, widthToGenerate, heightToGenerate, r);
        }
        if (remainder == 1) {
            generateRoomRight(lastWidthLoc, lastHeightLoc, widthToGenerate, heightToGenerate, r);
        }
        if (remainder == 2) {
            generateRoomDown(lastWidthLoc, lastHeightLoc, widthToGenerate, heightToGenerate, r);
        }
        if (remainder == 3) {
            generateRoomLeft(lastWidthLoc, lastHeightLoc, widthToGenerate, heightToGenerate, r);
        }
    }

    public void generateRoomUp(int lastWidthLoc, int lastHeightLoc,
                               int widthToGenerate, int heightToGenerate, Random r) {
        int remainder = getRemainder(r, widthToGenerate);
        int startWidth = getLeftBottomTileLocOfRoomUp(remainder, lastWidthLoc, lastHeightLoc)[0];
        int startHeight = getLeftBottomTileLocOfRoomUp(remainder, lastWidthLoc, lastHeightLoc)[1];
        generateRoomInGeneral(startWidth, startHeight, widthToGenerate, heightToGenerate);
    }

    public void generateRoomRight(int lastWidthLoc, int lastHeightLoc,
                                  int widthToGenerate, int heightToGenerate, Random r) {
        int remainder = getRemainder(r, heightToGenerate);
        int startWidth = getLeftBottomTileLocOfRoomRight(remainder, lastWidthLoc, lastHeightLoc)[0];
        int startHeight = getLeftBottomTileLocOfRoomRight(remainder, lastWidthLoc, lastHeightLoc)[1];
        generateRoomInGeneral(startWidth, startHeight, widthToGenerate, heightToGenerate);
    }

    public void generateRoomDown(int lastWidthLoc, int lastHeightLoc,
                                 int widthToGenerate, int heightToGenerate, Random r) {
        int remainder = getRemainder(r, widthToGenerate);
        int startWidth = getLeftBottomTileLocOfRoomDown(remainder, lastWidthLoc, lastHeightLoc, heightToGenerate)[0];
        int startHeight = getLeftBottomTileLocOfRoomDown(remainder, lastWidthLoc, lastHeightLoc, heightToGenerate)[1];
        generateRoomInGeneral(startWidth, startHeight, widthToGenerate, heightToGenerate);

    }

    public void generateRoomLeft(int lastWidthLoc, int lastHeightLoc,
                                 int widthToGenerate, int heightToGenerate, Random r) {
        int remainder = getRemainder(r, heightToGenerate);
        int startWidth = getLeftBottomTileLocOfRoomLeft(remainder, lastWidthLoc, lastHeightLoc, widthToGenerate)[0];
        int startHeight = getLeftBottomTileLocOfRoomLeft(remainder, lastWidthLoc, lastHeightLoc, widthToGenerate)[1];
        generateRoomInGeneral(startWidth, startHeight, widthToGenerate, heightToGenerate);

    }

    public int[] getLeftBottomTileLocOfRoomUp(int remainder, int lastWidthLoc, int lastHeightLoc) {
        return new int[]{lastWidthLoc - remainder, lastHeightLoc + 1};
    }

    public int[] getLeftBottomTileLocOfRoomRight(int remainder, int lastWidthLoc, int lastHeightLoc) {
        return new int[]{lastWidthLoc + 1, lastHeightLoc - remainder};
    }

    public int[] getLeftBottomTileLocOfRoomDown(int remainder, int lastWidthLoc,
                                                int lastHeightLoc, int HeightToGenerate) {
        return new int[]{lastWidthLoc - remainder, lastHeightLoc - HeightToGenerate};
    }

    public int[] getLeftBottomTileLocOfRoomLeft(int remainder, int lastWidthLoc,
                                                int lastHeightLoc, int widthToGenerate) {
        return new int[]{lastWidthLoc - widthToGenerate, lastHeightLoc - remainder};
    }

    public void generateRoomInGeneral(int startWidth, int startHeight, int widthToGenerate, int heightToGenerate) {
        if (canRoomBeGenerated(startWidth, startHeight, widthToGenerate, heightToGenerate)) {
            for (int i = startWidth; i < startWidth + widthToGenerate; i += 1) {
                for (int j = startHeight; j < startHeight + heightToGenerate; j += 1) {
                    tiles[i][j] = Tileset.FLOOR;
                    totalNumOfTiles += 1;
                }
            }
            encounterGenerator(startWidth, startHeight, widthToGenerate, heightToGenerate);
        }
    }

    public void encounterGenerator(int startWidth, int startHeight, int widthToGenerate, int heightToGenerate) {
        for (int i = startWidth + 1; i < startWidth + widthToGenerate - 1; i += 1) {
            for (int j = startHeight + 1; j < startHeight + heightToGenerate - 1; j += 1) {
                if (!isEncounterGenerated) {
                    if (getRemainder(random, 2) == 0) {
                        tiles[i][j] = Tileset.FLOWER;
                        isEncounterGenerated = true;
                    }
                }
            }
        }
    }

    public boolean canRoomBeGenerated(int startWidth, int startHeight, int widthToGenerate, int heightToGenerate) {
        for (int i = startWidth; i < startWidth + widthToGenerate; i += 1) {
            for (int j = startHeight; j < startHeight + heightToGenerate; j += 1) {
                if (!validate(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validate(int widthLoc, int heightLoc) {
        if (widthLoc < 1 || widthLoc >= width - 1 || heightLoc < 1 || heightLoc >= height - 1) {
            return false;
        } else if (tiles[widthLoc][heightLoc] == Tileset.FLOOR) {
            return false;
        } else {
            return true;
        }
    }

    public int getRemainder(Random r, int divider) {
        return Math.floorMod(r.nextInt(), divider);
    }


    public TETile[][] getTiles() {
        return tiles;
    }

    public void getRandomStartPointLoc() {
        List lst = getArbitraryWidthAndHeight(random);
        startWidth = (int) lst.get(0);
        startHeight = (int) lst.get(1);
    }


    //this method is used to get the arbitrary width and height that's in the tile marked as floor
    //only used the bound of the floor tile
    public List<Integer> getArbitraryWidthAndHeight(Random r) {
        ArrayList<List<Integer>> truePoints = new ArrayList<>();
        truePoints.add(Arrays.asList(startWidth, startHeight));
        for (int i = 1; i < tiles.length - 1; i++) {
            for (int j = 1; j < tiles[0].length - 1; j++) {
                if (tiles[i][j] == Tileset.FLOOR && checkIfSuitableToBeStartPoint(i, j) == 2) {
                    truePoints.add(Arrays.asList(i, j));
                }
                if (tiles[i][j] == Tileset.FLOOR && checkIfSuitableToBeStartPoint(i, j) >= 3) {
                    for (int k = 0; k < 2000; k++) {
                        truePoints.add(Arrays.asList(i, j));
                    }
                }
            }
        }
        List<Integer> randomPoint = truePoints.get(getRemainder(r, truePoints.size()));
        return randomPoint;
    }

    public int checkIfSuitableToBeStartPoint(int i, int j) {
        int nothingNum = 0;
        if (tiles[i][j + 1] == Tileset.NOTHING) {
            nothingNum += 1;
        }
        if (tiles[i][j - 1] == Tileset.NOTHING) {
            nothingNum += 1;
        }
        if (tiles[i + 1][j] == Tileset.NOTHING) {
            nothingNum += 1;
        }
        if (tiles[i - 1][j] == Tileset.NOTHING) {
            nothingNum += 1;
        }
        return nothingNum;
    }


    ///////////FloorsWallLine/////////////////////////////////////////////////
    public void wallGenerator() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (tiles[i][j] == Tileset.NOTHING && isProperPlaceToBuildWall(i, j)) {
                    tiles[i][j] = Tileset.WALL;
                }
            }
        }

    }

    public boolean isProperPlaceToBuildWall(int i, int j) {
        int floorNum = 0;
        if (locChecker(i, j) == 0) {
            floorNum = WallCheckerRight(i, j) + WallCheckerUp(i, j) + DiagonalWallCheckerRightTop(i, j);
        }
        if (locChecker(i, j) == 1) {
            floorNum = WallCheckerRight(i, j) + WallCheckerDown(i, j) + DiagonalWallCheckerRightBottom(i, j);
        }
        if (locChecker(i, j) == 2) {
            floorNum = WallCheckerLeft(i, j) + WallCheckerDown(i, j) + DiagonalWallCheckerLeftBottom(i, j);
        }
        if (locChecker(i, j) == 3) {
            floorNum = WallCheckerLeft(i, j) + WallCheckerUp(i, j) + DiagonalWallCheckerLeftTop(i, j);
        }
        if (locChecker(i, j) == 4) {
            floorNum = WallCheckerDown(i, j) + WallCheckerRight(i, j) + WallCheckerUp(i, j)
                    + DiagonalWallCheckerRightTop(i, j) + DiagonalWallCheckerRightBottom(i, j);
        }
        if (locChecker(i, j) == 5) {
            floorNum = WallCheckerDown(i, j) + WallCheckerRight(i, j) + WallCheckerLeft(i, j)
                    + DiagonalWallCheckerLeftBottom(i, j) + DiagonalWallCheckerRightBottom(i, j);
        }
        if (locChecker(i, j) == 6) {
            floorNum = WallCheckerDown(i, j) + WallCheckerUp(i, j) + WallCheckerLeft(i, j)
                    + DiagonalWallCheckerLeftBottom(i, j) + DiagonalWallCheckerLeftTop(i, j);
        }
        if (locChecker(i, j) == 7) {
            floorNum = WallCheckerLeft(i, j) + WallCheckerUp(i, j) + WallCheckerRight(i, j)
                    + DiagonalWallCheckerLeftTop(i, j) + DiagonalWallCheckerRightTop(i, j);
        }
        if (locChecker(i, j) == 8) {
            floorNum = WallCheckerLeft(i, j) + WallCheckerUp(i, j) + WallCheckerDown(i, j) + WallCheckerRight(i, j)
                    + DiagonalWallCheckerRightTop(i, j) + DiagonalWallCheckerLeftTop(i, j)
                    + DiagonalWallCheckerLeftBottom(i, j) + DiagonalWallCheckerRightBottom(i, j);
        }
        if (floorNum >= 1) {
            return true;
        }
        return false;
    }

    public int locChecker(int i, int j) {
        /* 0 = leftBottom corner, 1 = leftTop corner, 2 = RightTop corner, 3 = RightBottom Corner
           4 = leftEdge, 5 = topEdge, 6 = rightEdge, 7 = bottomEdge **/
        if (i == 0 && j == 0) {
            return 0;
        } else if (i == 0 && j == height - 1) {
            return 1;
        } else if (i == width - 1 && j == height - 1) {
            return 2;
        } else if (i == width - 1 && j == 0) {
            return 3;
        } else if (i == 0) {
            return 4;
        } else if (j == height - 1) {
            return 5;
        } else if (i == width - 1) {
            return 6;
        } else if (j == 0) {
            return 7;
        } else {
            return 8;
        }
    }

    public int WallCheckerUp(int i, int j) {
        if (tiles[i][j + 1] == Tileset.FLOOR) {
            return 1;
        }
        return 0;
    }

    public int WallCheckerRight(int i, int j) {
        if (tiles[i + 1][j] == Tileset.FLOOR) {
            return 1;
        }
        return 0;
    }

    public int WallCheckerDown(int i, int j) {
        if (tiles[i][j - 1] == Tileset.FLOOR) {
            return 1;
        }
        return 0;
    }

    public int WallCheckerLeft(int i, int j) {
        if (tiles[i - 1][j] == Tileset.FLOOR) {
            return 1;
        }
        return 0;
    }

    public int DiagonalWallCheckerLeftBottom(int i, int j) {
        if (tiles[i - 1][j - 1] == Tileset.FLOOR) {
            return 1;
        }
        return 0;
    }

    public int DiagonalWallCheckerLeftTop(int i, int j) {
        if (tiles[i - 1][j + 1] == Tileset.FLOOR) {
            return 1;
        }
        return 0;
    }

    public int DiagonalWallCheckerRightTop(int i, int j) {
        if (tiles[i + 1][j + 1] == Tileset.FLOOR) {
            return 1;
        }
        return 0;
    }

    public int DiagonalWallCheckerRightBottom(int i, int j) {
        if (tiles[i + 1][j - 1] == Tileset.FLOOR) {
            return 1;
        }
        return 0;
    }

    /////////Player Control and Wall line /////////////////////////////////////////////////
    public void pressW() {
        if (validateNotWall(playerFirstWidth, playerFirstHeight + 1)) {
            tiles[playerFirstWidth][playerFirstHeight] = Tileset.FLOOR;
            ifTouchEncounterNext(playerFirstWidth, playerFirstHeight + 1);
            playerFirstHeight += 1;
            ifTouchEncounter(playerFirstWidth, playerFirstHeight);
            if (newWorld != null) {
                ifTouchCoin(playerFirstWidth, playerFirstHeight);
                ifTouchAllCoins();
                tiles[playerFirstWidth][playerFirstHeight] = Tileset.TREE;
            }
            if (!isEncounterTouched || isCoinAllCollected) {
                tiles[playerFirstWidth][playerFirstHeight] = Tileset.TREE;
            }
        }
    }

    public void pressS() {
        if (validateNotWall(playerFirstWidth, playerFirstHeight - 1)) {
            tiles[playerFirstWidth][playerFirstHeight] = Tileset.FLOOR;
            ifTouchEncounterNext(playerFirstWidth, playerFirstHeight - 1);
            playerFirstHeight -= 1;
            ifTouchEncounter(playerFirstWidth, playerFirstHeight);
            if (newWorld != null) {
                ifTouchCoin(playerFirstWidth, playerFirstHeight);
                ifTouchAllCoins();
                tiles[playerFirstWidth][playerFirstHeight] = Tileset.TREE;
            }
            if (!isEncounterTouched || isCoinAllCollected) {
                tiles[playerFirstWidth][playerFirstHeight] = Tileset.TREE;
            }
        }
    }

    public void pressA() {
        if (validateNotWall(playerFirstWidth - 1, playerFirstHeight)) {
            tiles[playerFirstWidth][playerFirstHeight] = Tileset.FLOOR;
            ifTouchEncounterNext(playerFirstWidth - 1, playerFirstHeight);
            playerFirstWidth -= 1;
            ifTouchEncounter(playerFirstWidth, playerFirstHeight);
            if (newWorld != null) {
                ifTouchCoin(playerFirstWidth, playerFirstHeight);
                ifTouchAllCoins();
                tiles[playerFirstWidth][playerFirstHeight] = Tileset.TREE;
            }
            if (!isEncounterTouched || isCoinAllCollected) {
                tiles[playerFirstWidth][playerFirstHeight] = Tileset.TREE;
            }
        }
    }

    public void pressD() {
        if (validateNotWall(playerFirstWidth + 1, playerFirstHeight)) {
            tiles[playerFirstWidth][playerFirstHeight] = Tileset.FLOOR;
            ifTouchEncounterNext(playerFirstWidth + 1, playerFirstHeight);
            playerFirstWidth += 1;
            ifTouchEncounter(playerFirstWidth, playerFirstHeight);
            if (newWorld != null) {
                ifTouchCoin(playerFirstWidth, playerFirstHeight);
                ifTouchAllCoins();
                tiles[playerFirstWidth][playerFirstHeight] = Tileset.TREE;
            }
            if (!isEncounterTouched || isCoinAllCollected) {
                tiles[playerFirstWidth][playerFirstHeight] = Tileset.TREE;
            }
        }
    }

    public boolean validateNotWall(int widthLoc, int heightLoc) {
        if (tiles[widthLoc][heightLoc].equals(Tileset.WALL)) {
            return false;
        }
        return true;
    }

    public void ifTouchEncounter(int widthLoc, int heightLoc) {
        if (tiles[widthLoc][heightLoc] == Tileset.FLOWER) {
            tilesCopy = tiles;
            newWorld = new NewTileWorld(width, height, randomSeed, tilesCopy);
            tiles = newWorld.getTiles();
            playerFirstWidth = newWorld.getPlayerFirstWidth();
            playerFirstHeight = newWorld.getPlayerFirstHeight();
        }
    }

    public void ifTouchEncounterNext(int widthLoc, int heightLoc) {
        if (tiles[widthLoc][heightLoc] == Tileset.FLOWER) {
            playerOldWorldWidthLoc = playerFirstWidth;
            playerOldWorldHeightLoc = playerFirstHeight;
            tiles[playerFirstWidth][playerFirstHeight] = Tileset.TREE;
        }
    }

    public void ifTouchCoin(int widthLoc, int heightLoc) {
        if (tiles[widthLoc][heightLoc] == Tileset.WATER) {
            numTouchedCoin++;
        }
    }

    public void ifTouchAllCoins() {
        if (newWorld.getCoinNum() - numTouchedCoin <= 0) {
            if (!isGoodJobShown) {
                StdDraw.clear(Color.BLACK);
                StdDraw.setPenColor(Color.WHITE);
                Font fontBig = new Font("Monaco", Font.BOLD, 30);
                StdDraw.setFont(fontBig);
                StdDraw.text(width / 2 + 2, height / 2 + 2, "You Collected All Water! Good Job!");
                StdDraw.show();
                StdDraw.pause(1000);
                isGoodJobShown = true;
            }
            tiles = tilesCopy;
            playerFirstWidth = playerOldWorldWidthLoc;
            playerFirstHeight = playerOldWorldHeightLoc;
            newWorld = null;
        }
    }

    public TilesGenerator copy() {
        TilesGenerator newTile = new TilesGenerator(this);
        return newTile;
    }
}
