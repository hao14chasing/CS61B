package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Random;

public class NewTileWorld {
    private int width;
    private int height;
    private TETile[][] world;
    private int seed;
    private TETile[][] tilesCopy;
    private int coinNum = 0;
    private Random random;
    private int playerFirstWidth;
    private int playerFirstHeight;
    private int x_start;
    private int x_end;
    private int y_start;
    private int y_end;
    private static boolean methodCalled = false;
    public NewTileWorld(int width, int height, int seed, TETile[][] tilesCopy) {
        this.width = width;
        this.height = height;
        this.world = new TETile[width][height];
        this.seed = seed;
        this.tilesCopy = tilesCopy;
        random = new Random(seed);
        x_start = (int) width / 4;
        x_end = (int) width / 2;
        y_start = (int) height / 4;
        y_end = (int) height / 2;
        draw();
        generateNothingInTheTiles();
        generateRoom();
        generateWall();
        generateWater();
        generateAvater();
    }

    public void draw() {
        if (!methodCalled) {
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(Color.WHITE);
            Font fontBig = new Font("Monaco", Font.BOLD, 30);
            StdDraw.setFont(fontBig);
            StdDraw.text(width / 2, height / 3 * 2, "Please Collect All Water not to be thirsty!");
            StdDraw.show();
            StdDraw.pause(1000);
            StdDraw.text(width / 2, height / 3, "Good Luck!");
            StdDraw.show();
            StdDraw.pause(500);
            StdDraw.clear(Color.BLACK);
            methodCalled = true;
        }
    }

    //making the world
    public void generateNothingInTheTiles() {
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < height; j += 1) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }
        //generate a room within the given width and height
    public void generateRoom() {
        for (int i = x_start; i <= x_end; i++) {
            for (int j = y_start; j <= y_end; j++) {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }

    //generate wall around the room
    public void generateWall() {
        for (int i = x_start; i <= x_end; i++) {
            world[i][y_end + 1] = Tileset.WALL;
            world[i][y_start - 1] = Tileset.WALL;
        }
        for (int j = y_start; j <= y_end; j++) {
            world[x_end + 1][j] = Tileset.WALL;
            world[x_start - 1][j] = Tileset.WALL;
        }
        world[x_start - 1][y_end + 1] = Tileset.WALL;
        world[x_start - 1][y_start - 1] = Tileset.WALL;
        world[x_end + 1][y_end + 1] = Tileset.WALL;
        world[x_end + 1][y_start - 1] = Tileset.WALL;
    }

    //randomly generate some water(coins) within in the room
    public void generateWater () {
        int count = 0;
        while (count < 11) {
            int x = random.nextInt((x_end - x_start) + 1) + x_start;
            int y = random.nextInt((y_end - y_start) + 1) + y_start;
            if (world[x][y] == Tileset.FLOOR) {
                world[x][y] = Tileset.WATER;
                count++;
                coinNum++;
            }
        }
    }

    //randomly generate avater's position within in the room
    public void generateAvater () {
        int x = random.nextInt((x_end - x_start) + 1) + x_start;
        int y = random.nextInt((y_end - y_start) + 1) + y_start;
        if (world[x][y] == Tileset.FLOOR) {
            world[x][y] = Tileset.TREE;
        }
        playerFirstWidth = x;
        playerFirstHeight = y;
    }

    public TETile[][] getTiles() {
        return world;
    }

    public int getCoinNum() {
        return coinNum;
    }

    public TETile[][] returnOriginalWorld() {
        if (coinNum == 0) {
            world = tilesCopy;
        }
        return world;
    }

    public int getPlayerFirstWidth() {
        return playerFirstWidth;
    }

    public int getPlayerFirstHeight() {
        return playerFirstHeight;
    }
}

