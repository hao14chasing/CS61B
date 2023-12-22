package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.google.common.truth.Truth.assertThat;

public class testRandom {

    @Test
    public void testRandomNum(){
        Random r = new Random(1000);
        System.out.println(r.nextInt());
        System.out.println(r.nextInt());
        System.out.println(r.nextInt());
    }

    @Test
    public void testSameRandomNum(){
        Random r = new Random(1000);
        System.out.println(r.nextInt());
        System.out.println(r.nextInt());
        System.out.println(r.nextInt());
    }

    @Test
    public void testRenderInteractWithInputString() throws InterruptedException {
        Engine engine = new Engine();
        TETile[][] f1 = engine.interactWithInputString("N1999SWWWAAWWAA:q");
        //assertThat(f1).isEqualTo(f2);
        //System.out.println(engine.toString());
        System.out.println(f1);
        //System.out.println(f2);
        TERenderer ter = new TERenderer();
        ter.initialize(50, 30);
        ter.renderFrame(f1);
        Thread.sleep(10000);
    }

    @Test
    public void testSeparateMovement() throws InterruptedException {
        Engine engine2 = new Engine();
        TETile[][] f2 = engine2.interactWithInputString("N1999SWWW:q");
        f2 = engine2.interactWithInputString("LAAWW:q");
        f2 = engine2.interactWithInputString("LAA:q");
        System.out.println(f2);
        TERenderer ter = new TERenderer();
        ter.initialize(50, 30);
        ter.renderFrame(f2);
        Thread.sleep(10000);

    }
}
