package byow.Core;

import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Random;

/* used for phase 2**/
public class MemoryGame {
    private int width;
    private int height;
    private Random random;

    public MemoryGame(int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void drawCenter(String s) {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.width / 2, this.height * 0.5, s);
        StdDraw.show();
    }

    public void drawCenterAbove(String s) {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.width / 2, this.height * 0.65, s);
        StdDraw.show();
    }

    public void drawCenterBelow(String s) {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.width / 2, this.height * 0.35, s);
        StdDraw.show();
    }

    public void drawCenterBelowMore(String s) {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.width / 2, this.height * 0.2, s);
        StdDraw.show();
    }

    public void drawHead(String s) {
        /* Take the input string S and display it at the top of the screen,
         * with the pen settings given below. */
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.width / 2, this.height * 0.85, s);
        StdDraw.show();
    }

    public void clearAndDrawCenter(String s) {
        StdDraw.clear(Color.BLACK);
        drawCenter(s);
    }

    public void displayDescription(TilesGenerator t) {
        clearPreviousDescription();
        StdDraw.setPenColor(Color.WHITE);
        Font fontSmall = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(fontSmall);
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();
        int tileLocX = getTileLocBasedOnMouseLocX(x);
        int tileLocY = getTileLocBasedOnMouseLocY(y);
        if (x < width - 2 && y < height - 2) {
            TETile[][] tiles = t.getTiles();
            TETile tileSet = tiles[tileLocX][tileLocY];
            StdDraw.text(width - 5, height - 1, tileSet.description());
            StdDraw.show();
        }
    }

    public void clearPreviousDescription() {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(width - 6, height - 1, 6, 1);
    }

    public int getTileLocBasedOnMouseLocX(double x) {
        int xLoc = 0;
        if ((x - 1) >= 0.75) {
            xLoc = (int) Math.nextUp(x);
        } else {
            xLoc = (int) Math.nextDown(x);
        }
        return xLoc;
    }

    public int getTileLocBasedOnMouseLocY(double x) {
        int xLoc = 0;
        if ((x - 1) >= 0.75) {
            xLoc = (int) Math.nextUp(x);
        } else {
            xLoc = (int) Math.nextDown(x);
        }
        return xLoc;
    }

    public void displayName(String name) {
        clearPreviousDescriptionName();
        StdDraw.setPenColor(Color.WHITE);
        Font fontSmall = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(fontSmall);
        StdDraw.text(7, height - 1, "Player Name: "+ name);
        StdDraw.show();
    }

    public void clearPreviousDescriptionName() {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(6, height - 1, 7, 1);
    }


}
