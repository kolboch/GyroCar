package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import sprites.Car;
import sprites.Chicane;

/**
 * Created by Karlo on 2017-06-03.
 */

public class GameScreen implements Screen {

    public static final int GROUND_SPEED = 120;

    private static final String LOG_TAG = GameScreen.class.getSimpleName();
    private static final float BG_GREY = 41 / 255f;
    private static final int ROAD_WIDTH_CAR_WIDTH_SCALE = 4;
    private static final int CHICANE_OFFSET = (int) -Chicane.CHICANE_HEIGHT / 2;

    private final int INIT_CAR_X;
    private final int INIT_CAR_Y;

    private Car car;
    private Chicane chicane1, chicane2;
    private Game game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport viewport;

    public GameScreen(Game game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        viewport = new FitViewport(MyGdxGame.GAME_WIDTH, MyGdxGame.GAME_HEIGHT, camera);
        INIT_CAR_X = (int) (camera.position.x - Car.CAR_WIDTH / 2);
        INIT_CAR_Y = (int) (camera.position.y / 2);
    }

    @Override
    public void show() {
        Gdx.app.log(LOG_TAG, "Show called");
        batch = new SpriteBatch();
        car = new Car(INIT_CAR_X, INIT_CAR_Y);
        float leftChicaneX = (camera.viewportWidth - ROAD_WIDTH_CAR_WIDTH_SCALE * Car.CAR_WIDTH) / 2 - Chicane.CHICANE_WIDTH;
        float rightChicaneX = leftChicaneX + Chicane.CHICANE_WIDTH + ROAD_WIDTH_CAR_WIDTH_SCALE * Car.CAR_WIDTH;
        chicane1 = new Chicane(CHICANE_OFFSET, leftChicaneX, rightChicaneX);
        chicane2 = new Chicane(CHICANE_OFFSET + Chicane.CHICANE_HEIGHT, leftChicaneX, rightChicaneX);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(BG_GREY, BG_GREY, BG_GREY, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
        updateAll(delta);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(car.getCarTexture(), car.getPosition().x, car.getPosition().y);
        drawChicanes(batch);
        batch.end();
    }

    private void updateAll(float delta) {
        car.update(delta, GROUND_SPEED);
        updateCamera();
        updateChicanes();
    }

    private void updateCamera() {
        camera.position.y = car.getPosition().y + camera.viewportHeight / 3;  // setting position + offset
        camera.update();
    }

    private void updateChicanes() {
        if (chicane1.getPositionLeft().y + Chicane.CHICANE_HEIGHT < camera.position.y - camera.viewportHeight / 2) {
            Chicane temp = chicane2;
            chicane1.reposition(chicane1.getPositionLeft().y + 2 * Chicane.CHICANE_HEIGHT, chicane1.getPositionLeft().x, chicane1.getPositionRight().x);
            chicane2 = chicane1;
            chicane1 = temp;
        }
    }

    private void drawChicanes(SpriteBatch batch) {
        drawChicane(chicane1, batch);
        drawChicane(chicane2, batch);
    }

    private void drawChicane(Chicane chicane, SpriteBatch batch) {
        batch.draw(chicane.getChicaneLeftTexture(), chicane.getPositionLeft().x, chicane.getPositionLeft().y);
        batch.draw(chicane.getChicaneRightTexture(), chicane.getPositionRight().x, chicane.getPositionRight().y);
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        chicane1.dispose();
        chicane2.dispose();
    }
}
