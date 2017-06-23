package input_adapters;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;

import sprites.Car;

/**
 * Created by Karlo on 2017-06-06.
 */

public class KeyBoardHandler extends InputAdapter {

    private Car car;
    private final float CHANGE_VELOCITY;

    public KeyBoardHandler(Car car, float changeInVelocity) {
        super();
        this.car = car;
        this.CHANGE_VELOCITY = changeInVelocity;
    }

    @Override
    public boolean keyDown(int keycode) {
        Vector3 reaction = null;
        boolean handled = false;
        switch (keycode){
            case Input.Keys.DPAD_LEFT:
                reaction = new Vector3(-CHANGE_VELOCITY, 0, 0);
                handled = true;
                break;
            case Input.Keys.DPAD_RIGHT:
                reaction = new Vector3(CHANGE_VELOCITY, 0, 0);
                handled = true;
                break;
            case Input.Keys.DPAD_UP:
                reaction = new Vector3(0, CHANGE_VELOCITY, 0);
                handled = true;
                break;
            case Input.Keys.DPAD_DOWN:
                reaction = new Vector3(0, -CHANGE_VELOCITY, 0);
                handled = true;
                break;

            default:

        }
        if(handled){
            car.turn(reaction);
        }
        return handled;
    }
}
