package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Karlo on 2017-06-03.
 */

public class Car {

    private Texture carTexture;
    private Rectangle bounds;
    private Vector3 position;
    private Vector3 velocity;

    public Car(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        carTexture = new Texture("lambo.png");
    }

    public void update(float delta, float speed){

    }

    public Vector3 getPosition(){
        return position;
    }

    public Texture getCarTexture(){
        return carTexture;
    }

    public void dispose(){
        carTexture.dispose();
    }
}
