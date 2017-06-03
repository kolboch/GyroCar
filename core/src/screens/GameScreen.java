package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

import sprites.Car;

/**
 * Created by Karlo on 2017-06-03.
 */

public class GameScreen implements Screen {

    public static final int GROUND_SPEED = 15;

    private static final String LOG_TAG = GameScreen.class.getSimpleName();
    private static final int INIT_CAR_X;
    private static final int INIT_CAR_Y;

    private Car car;
    private Game game;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    static{
        INIT_CAR_X = 0;
        INIT_CAR_Y = 0;
    }

    public GameScreen(Game game){
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, MyGdxGame.GAME_WIDTH, MyGdxGame.GAME_HEIGHT);
    }

    @Override
    public void show() {
        Gdx.app.log(LOG_TAG, "Show called");
        batch = new SpriteBatch();
        car = new Car(INIT_CAR_X, INIT_CAR_Y);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(41/255f, 41/255f, 41/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(car.getCarTexture(), car.getPosition().x, car.getPosition().y);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.app.log(LOG_TAG, "Hide called");
        dispose();
    }

    @Override
    public void dispose() {
        Gdx.app.log(LOG_TAG, "Dispose called.");
        car.dispose();
        batch.dispose();
    }
}
