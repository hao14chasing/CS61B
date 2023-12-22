package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 50;
    public static final int HEIGHT = 40;
    private TilesGenerator helloWorld;
    private TilesGenerator changingWorld;
    private String worldBuilderSeed;
    private String lSeed;
    private char prevChar = 'c';
    private boolean isWorldDisplayed = false;
    private boolean islExecuted = false;
    private boolean loadL = false;
    private String name;
    public static final int DIGIT = 10;
    public static final int TIME = 1000;



    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        MemoryGame m = new MemoryGame(WIDTH + 2, HEIGHT + 2);
        m.drawHead("CS 61B: THE GAME");
        m.drawCenter("Load Game (L)");
        m.drawCenterAbove("New Game (N)");
        m.drawCenterBelow("Quit (Q)");
        m.drawCenterBelowMore("Give Name(G)");
        String str = "";
        Boolean hasPressed = false;
        while (!hasPressed) {
            if (StdDraw.hasNextKeyTyped()) {
                char nextKey = StdDraw.nextKeyTyped();
                /////////// click N in the main menu, start a new game ////////////////////////////////
                if (nextKey == 'N' || nextKey == 'n') {
                    m.clearAndDrawCenter("Your Seed: ");
                    m.drawHead("End your seed pressing S");
                    String seed = "N";
                    while (seed.charAt(seed.length() - 1) != 'S' && seed.charAt(seed.length() - 1) != 's') {
                        if (StdDraw.hasNextKeyTyped()) {
                            seed += StdDraw.nextKeyTyped();
                            m.clearAndDrawCenter(seed);
                        }
                    }
                    worldBuilderSeed = seed;
                    interactWithInputString(seed);
                    movementPartInteractWithKeyBoard(seed, m);
                }
                /////////// click L in the main menu, reload the old game ////////////////////////////////
                if (nextKey == 'L' || nextKey == 'l') {
                    interactWithInputString(Character.toString(nextKey));
                    movementPartInteractWithKeyBoard(lSeed, m);
                }
                /////////// click Q in the main menu, quit the game ////////////////////////////////
                if (nextKey == 'Q' || nextKey == 'q') {
                    System.exit(0);
                }
                /////////// click G in the main menu, give your avatar a name//////////////////
                if (nextKey == 'G' || nextKey == 'g') {
                    name = giveName("G", m);
                    hasPressed = true;
                    interactWithKeyboard();
                }
            }
        }
    }

    public void movementPartInteractWithKeyBoard(String seed, MemoryGame m) {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char nextKey = StdDraw.nextKeyTyped();
                seed += nextKey;
                interactWithInputString(seed);
                checkExit(prevChar, nextKey);
                prevChar = nextKey;
            }
            m.displayDescription(helloWorld);
            m.displayName(name);
        }
    }

    public void checkExit(char c1, char c2) {
        if (c1 == ':' && (c2 == 'q' || c2 == 'Q')) {
            System.exit(0);
        }
    }

    public String giveName(String s, MemoryGame m) {
        m.clearAndDrawCenter("Type your name and end by pressing 0");
        while (s.charAt(s.length() - 1) != '0') {
            if (StdDraw.hasNextKeyTyped()) {
                s += StdDraw.nextKeyTyped();
                m.clearAndDrawCenter("end by pressing 0: " + s);
            }
        }
        m.clearAndDrawCenter("Your name has been updated");
        StdDraw.pause(TIME);
        s = removeFirstAndLast(s);
        return s;
    }

    public String removeFirstAndLast(String s) {
        String s2 = "";
        for (int i = 1; i < s.length() - 1; i++) {
            s2 += s.charAt(i);
        }
        return s2;
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, running both of these:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        TETile[][] finalWorldFrame = null;
        int seed = 0;
        int numOfS = countNumOfS(input);
        if ((input.charAt(0) == 'L' || input.charAt(0) == 'l')) { // && !isWorldDisplayed) {
            try {
                FileReader reader = new FileReader("./byow/Core/SeedString.txt");
                int character;
                StringBuilder builder = new StringBuilder();
                while ((character = reader.read()) != -1) {
                    builder.append((char) character);
                }
                reader.close();
                String content = builder.toString();
                lSeed = content;
                content += input;
                loadL = true;
                finalWorldFrame = interactWithInputString(content);
                isWorldDisplayed = true;
                //executeQOperation(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (numOfS == 1 && (input.charAt(input.length() - 1) == 'S'
                || input.charAt(input.length() - 1) == 's')) {
            seed = transformStringToInt(input, input);
            TilesGenerator tilesGenerator = new TilesGenerator(WIDTH, HEIGHT, seed);
            ter.initialize(WIDTH + 2, HEIGHT + 2);
            finalWorldFrame = tilesGenerator.getTiles();
            helloWorld = tilesGenerator;
            changingWorld = helloWorld.copy();
            ter.renderFrame(finalWorldFrame);
            isWorldDisplayed = true;
        } else if (loadL) {
            String oldWorld = rebuildTheWorld(input);
            seed = transformStringToInt(input, oldWorld);
            TilesGenerator tilesGenerator = new TilesGenerator(WIDTH, HEIGHT, seed);
            helloWorld = tilesGenerator;
            changingWorld = helloWorld.copy();
            String oldMovement = findMovementOperation(input);
            ter.initialize(WIDTH + 2, HEIGHT + 2);
            finalWorldFrame = moveFollowedByMovementOperation(changingWorld, oldMovement);
            ter.renderFrame(finalWorldFrame);
            changingWorld = helloWorld.copy();
            isWorldDisplayed = true;
            islExecuted = true;
            loadL = false;
            executeQOperation(input);
        } else {
            buildWorldAtTheBeginningToPassGradeScope(input);
            String movement = findMovementOperation(input);
            finalWorldFrame = moveFollowedByMovementOperation(changingWorld, movement);
            changingWorld = helloWorld.copy();
            executeQOperation(input);
        }
        return finalWorldFrame;
    }

    public int transformStringToInt(String s, String worldBuilderSee) {
        int seed = 0;
        for (int k = 1; k < worldBuilderSee.length() - 1; k += 1) {
            char c = s.charAt(k);
            seed = seed * DIGIT + Integer.parseInt(String.valueOf(c));
        }
        return seed;
    }

    public TETile[][] moveFollowedByMovementOperation(TilesGenerator changingWorld1, String movement) {
        for (int j = 0; j < movement.length(); j++) {
            if (movement.charAt(j) == 'w' || movement.charAt(j) == 'W') {
                changingWorld1.pressW();
            }
            if (movement.charAt(j) == 's' || movement.charAt(j) == 'S') {
                changingWorld1.pressS();
            }
            if (movement.charAt(j) == 'a' || movement.charAt(j) == 'A') {
                changingWorld1.pressA();
            }
            if (movement.charAt(j) == 'd' || movement.charAt(j) == 'D') {
                changingWorld1.pressD();
            }
        }
        TETile[][] finalWorldFrame = changingWorld1.getTiles();
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

    /* get the string wwwwwddddd without N****S **/
    public String findMovementOperation(String str) {
        boolean hasSAppeared = false;
        String s = "";
        for (int i = 0; i < str.length(); i++) {
            if (hasSAppeared) {
                s += str.charAt(i);
            }
            if (str.charAt(i) == 'S') {
                hasSAppeared = true;
            }
        }
        return s;
    }

    public String saveStringWithoutQ(String str) {
        String s = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ':' || str.charAt(i) != 'q' || str.charAt(i) != 'Q') {
                s += str.charAt(i);
            }
        }
        return s;
    }

    /* get the string N***S **/
    public String rebuildTheWorld(String str) {
        boolean hasSAppeared = false;
        String s = "";
        for (int i = 0; i < str.length(); i++) {
            if (!hasSAppeared) {
                s += str.charAt(i);
            }
            if (str.charAt(i) == 'S' || str.charAt(i) == 's') {
                hasSAppeared = true;
            }
        }
        return s;
    }

    public void buildWorldAtTheBeginningToPassGradeScope(String input) {
        if (!isWorldDisplayed) {
            String worldBuilder = rebuildTheWorld(input);
            interactWithInputString(worldBuilder);
            isWorldDisplayed = true;
        }
    }

    public void executeQOperation(String input) {
        if (input.length() >= 2 && input.charAt(input.length() - 2) == ':'
                && (input.charAt(input.length() - 1) == 'q' || input.charAt(input.length() - 1) == 'Q')) {
            saveSeedAndQuit(input);
        }
    }

    public void saveSeedAndQuit(String input) {
        String savedSeed = saveStringWithoutQ(input);
        try {
            FileWriter fileWriter = new FileWriter("./byow/Core/SeedString.txt");
            fileWriter.write(savedSeed);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int countNumOfS(String input) {
        int n = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 's' || input.charAt(i) == 'S') {
                n += 1;
            }
        }
        return n;
    }
}
