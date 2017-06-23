package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import interfaces.MyDrawable;

/**
 * Created by Karlo on 2017-06-04.
 */

public class Obstacle implements MyDrawable{

    public static final float OBSTACLE_WIDTH;
    public static final float OBSTACLE_HEIGHT;

    private static Random random;
    private Texture obstacleTexture;
    private Vector2 position;
    private Rectangle bounds;

    static {
        random = new Random();
        Texture textureDims = new Texture("obstacle.png");
        OBSTACLE_WIDTH = textureDims.getWidth();
        OBSTACLE_HEIGHT = textureDims.getHeight();
        textureDims.dispose();
    }

    public Obstacle(float y, float minX, float maxX) {
        position = new Vector2(generateRandomPoint(minX, maxX - OBSTACLE_WIDTH), y);
        bounds = new Rectangle(position.x, position.y, OBSTACLE_WIDTH, OBSTACLE_HEIGHT);
        obstacleTexture = new Texture("obstacle.png");
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getObstacleTexture() {
        return obstacleTexture;
    }

    public void dispose() {
        obstacleTexture.dispose();
    }

    public void reposition(float y, float minX, float maxX) {
        position = new Vector2(generateRandomPoint(minX, maxX - OBSTACLE_WIDTH), y);
        updateBounds();
    }

    public boolean collides(Rectangle otherBounds){
        return bounds.overlaps(otherBounds);
    }

    private float generateRandomPoint(float minX, float maxX) {
        return random.nextFloat() * (maxX - minX) + minX;
    }

    private void updateBounds(){
        bounds.x = position.x;
        bounds.y = position.y;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(obstacleTexture, position.x, position.y);
    }
}
