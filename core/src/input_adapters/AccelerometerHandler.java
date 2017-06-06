package input_adapters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;

import sprites.Car;

/**
 * Created by Karlo on 2017-06-06.
 */

public class AccelerometerHandler {

    private static final String LOG_TAG = AccelerometerHandler.class.getSimpleName();
    private Car car;
    private float intensifier;

    public AccelerometerHandler(Car car, float intensifier) {
        this.car = car;
        this.intensifier = intensifier;
    }

    public void update() {
        logAccelerometerInputs();
    }

    private void logAccelerometerInputs() {
        float accX, accY;
        accX = Gdx.input.getAccelerometerX();
        accY = Gdx.input.getAccelerometerY();
        Vector3 reaction = new Vector3(-accX * intensifier, -accY * intensifier / 2, 0);
        car.turn(reaction);
    }

}
