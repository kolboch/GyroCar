package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Karlo on 2017-06-04.
 */

public class Obstacle {

    public static final float OBSTACLE_WIDTH;
    public static final float OBSTACLE_HEIGHT;

    private static Random random;
    private static Texture obstacleTexture;
    private Vector2 position;

    static {
        random = new Random();
        obstacleTexture = new Texture("obstacle.png");
        OBSTACLE_WIDTH = obstacleTexture.getWidth();
        OBSTACLE_HEIGHT = obstacleTexture.getHeight();
    }

    public Obstacle(float y, float minX, float maxX) {
        position = new Vector2(generateRandomPoint(minX, maxX - obstacleTexture.getWidth()), y);
    }

    private float generateRandomPoint(float minX, float maxX) {
        return random.nextFloat() * (maxX - minX) + minX;
    }

    public Vector2 getPosition() {
        return position;
    }

    public static Texture getObstacleTexture() {
        return obstacleTexture;
    }

    public static void dispose() {
        obstacleTexture.dispose();
    }

    public void reposition(float y, float minX, float maxX) {
        position = new Vector2(generateRandomPoint(minX, maxX - obstacleTexture.getWidth()), y);
    }
}
