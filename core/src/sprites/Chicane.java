package sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Karlo on 2017-06-03.
 */

public class Chicane {

    public static final float CHICANE_WIDTH, CHICANE_HEIGHT;

    private static String chicaneTexturePath = "chicane_left.png";

    private Texture chicaneLeft, chicaneRight;
    private Vector2 positionLeft, positionRight;

    static {
        Texture textureDims = new Texture(chicaneTexturePath);
        CHICANE_WIDTH = textureDims.getWidth();
        CHICANE_HEIGHT = textureDims.getHeight();
    }

    public Chicane(float y, float leftX, float rightX) {
        chicaneLeft = new Texture(chicaneTexturePath);
        chicaneRight = new Texture("chicane_right.png");
        positionLeft = new Vector2(leftX, y);
        positionRight = new Vector2(rightX, y);
    }

    public Texture getChicaneLeftTexture() {
        return chicaneLeft;
    }

    public Texture getChicaneRightTexture() {
        return chicaneRight;
    }

    public Vector2 getPositionLeft() {
        return positionLeft;
    }

    public Vector2 getPositionRight() {
        return positionRight;
    }

    public void dispose() {
        chicaneLeft.dispose();
        chicaneRight.dispose();
    }

    public void reposition(float y, float leftX, float rightX) {
        positionLeft.x = leftX;
        positionLeft.y = y;
        positionRight.x = rightX;
        positionRight.y = y;
    }
}
