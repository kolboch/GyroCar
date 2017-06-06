package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Karlo on 2017-06-03.
 */

public class Car {

    public static final int CAR_WIDTH;
    public static final int CAR_HEIGHT;

    private Texture carTexture;
    private Rectangle bounds;
    private Vector3 position;
    private Vector3 velocity;
    private Vector3 turn;
    private static String texturePath = "lambo.png";

    static {
        Texture textureForDims = new Texture(texturePath);
        CAR_WIDTH = textureForDims.getWidth();
        CAR_HEIGHT = textureForDims.getHeight();
        textureForDims.dispose();
    }

    public Car(int x, int y) {
        position = new Vector3(x, y, 0);
        bounds = new Rectangle(position.x, position.y, CAR_WIDTH, CAR_HEIGHT);
        velocity = new Vector3(0, 0, 0);
        turn = new Vector3(0, 0, 0);
        carTexture = new Texture(texturePath);
    }

    public void update(float delta, float speed) {
        if (position.y >= 0) {
            velocity.add(0, speed, 0);
            velocity.add(turn);
        }
        velocity.scl(delta);
        position.add(velocity.x, velocity.y, velocity.z);
        updateBounds();
    }

    public void turn(Vector3 turnVector) {
        if (turnVector.y != 0) {
            if (this.turn.y + turnVector.y > 0) {
                this.turn.add(turnVector);
            }
        }
        this.turn.x = turnVector.x;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getCarTexture() {
        return carTexture;
    }

    public void dispose() {
        carTexture.dispose();
    }

    private void updateBounds(){
        bounds.x = position.x;
        bounds.y = position.y;
    }
}
