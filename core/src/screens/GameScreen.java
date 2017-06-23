package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import java.util.LinkedList;
import java.util.List;

import input_adapters.AccelerometerHandler;
import input_adapters.KeyBoardHandler;
import music_utils.MusicUtils;
import sprites.Car;
import sprites.Chicane;
import sprites.Obstacle;
import sprites.Score;

/**
 * Created by Karlo on 2017-06-03.
 */

public class GameScreen implements Screen {

    public static final int GROUND_SPEED = 150;

    private static final String LOG_TAG = GameScreen.class.getSimpleName();
    private static final String MUSIC_BG = "background_race.mp3";
    private static final float BG_GREY = 41 / 255f;
    private static final int ROAD_WIDTH_CAR_WIDTH_SCALE = 5;
    private static final int CHICANE_OFFSET = (int) -Chicane.CHICANE_HEIGHT / 2;
    private static final int NUMBER_OF_OBSTACLES = 7;
    private static final int OBSTACLE_SPACING = (int) (Car.CAR_HEIGHT * 3f);
    private static final int FIRST_OBSTACLE_OFFSET_CAR_HEIGHT_SCALE = 4;
    private static final float TURN_REACTION = 100;
    private static final float ACCELEROMETER_INTENSIFIER = 30;
    private static final float MUSIC_VOLUME = 0.7f;
    private static final float OBSTACLE_AVOIDED_PRIZE = 5;

    private final int INIT_CAR_X;
    private final int INIT_CAR_Y;

    private Car car;
    private Chicane chicane1, chicane2;
    private List<Obstacle> obstacles;
    private Score score;
    private Game game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport viewport;
    private KeyBoardHandler inputAdapter;
    private AccelerometerHandler accelerometerHandler;
    private float obstacleMinX, obstacleMaxX;
    private float groundSpeed;
    private Music backgroundMusic;
    private boolean isStatePaused;

    public GameScreen(Game game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        viewport = new FitViewport(MyGdxGame.GAME_WIDTH, MyGdxGame.GAME_HEIGHT, camera);
        INIT_CAR_X = (int) (camera.position.x - Car.CAR_WIDTH / 2);
        INIT_CAR_Y = (int) (camera.position.y / 2);
    }

    private void initObstacles(float y, float minX, float maxX) {
        obstacles = new LinkedList<Obstacle>();
        for (int i = 0; i < NUMBER_OF_OBSTACLES; i++) {
            obstacles.add(new Obstacle(y + i * (Obstacle.OBSTACLE_HEIGHT + OBSTACLE_SPACING), minX, maxX));
        }
    }

    @Override
    public void show() {
        Gdx.app.log(LOG_TAG, "Show called");
        batch = new SpriteBatch();
        car = new Car(INIT_CAR_X, INIT_CAR_Y);
        initChicanes();
        obstacleMinX = chicane1.getPositionLeft().x + Chicane.CHICANE_WIDTH;
        obstacleMaxX = chicane1.getPositionRight().x;
        initObstacles(INIT_CAR_Y + Car.CAR_HEIGHT * FIRST_OBSTACLE_OFFSET_CAR_HEIGHT_SCALE, obstacleMinX, obstacleMaxX);
        score = new Score(0, 0, MyGdxGame.GAME_HEIGHT);
        setInputAdapter(car);
        initMusic(MUSIC_VOLUME);
        isStatePaused = false;
        startGameEffect();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(BG_GREY, BG_GREY, BG_GREY, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
        updateAll(delta);
        checkCollisions();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        car.draw(batch);
        drawChicanes(batch);
        drawObstacles(batch);
        score.draw(batch);
        batch.end();
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

    private void initChicanes() {
        float leftChicaneX = (camera.viewportWidth - ROAD_WIDTH_CAR_WIDTH_SCALE * Car.CAR_WIDTH) / 2 - Chicane.CHICANE_WIDTH;
        float rightChicaneX = leftChicaneX + Chicane.CHICANE_WIDTH + ROAD_WIDTH_CAR_WIDTH_SCALE * Car.CAR_WIDTH;
        chicane1 = new Chicane(CHICANE_OFFSET, leftChicaneX, rightChicaneX);
        chicane2 = new Chicane(CHICANE_OFFSET + Chicane.CHICANE_HEIGHT, leftChicaneX, rightChicaneX);
    }

    private void updateAll(float delta) {
        car.update(delta, groundSpeed);
        updateCamera();
        updateChicanes();
        updateObstacles();
        if (!isStatePaused) {
            score.update(camera.position.x, camera.position.y - camera.viewportHeight / 2, 0.01f);
        }
        if (accelerometerHandler != null) {
            accelerometerHandler.update();
        }
    }

    private void checkCollisions() {
        boolean collision = false;
        Rectangle carBounds = car.getBounds();
        if (chicane1.collides(carBounds) || chicane2.collides(carBounds)) {
            collision = true;
        } else {
            for (int i = 0; i < obstacles.size() && !collision; i++) {
                collision = obstacles.get(i).collides(carBounds);
            }
        }
        if (collision) {
            game.setScreen(new MenuScreen(game));
        }
    }

    private void updateCamera() {
        camera.position.y = car.getPosition().y + camera.viewportHeight / 3;  // setting position + offset
        camera.update();
    }

    private void drawObstacles(SpriteBatch batch) {
        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).draw(batch);
        }
    }

    private void updateObstacles() {
        if (obstacles.get(0).getPosition().y + Obstacle.OBSTACLE_HEIGHT < camera.position.y - camera.viewportHeight / 2) {
            obstacleAvoided();
            Obstacle toReposition = obstacles.remove(0);
            Obstacle last = obstacles.get(obstacles.size() - 1);
            toReposition.reposition(last.getPosition().y + Obstacle.OBSTACLE_HEIGHT + OBSTACLE_SPACING, obstacleMinX, obstacleMaxX);
            obstacles.add(toReposition);
        }
    }

    private void obstacleAvoided() {
        score.update(OBSTACLE_AVOIDED_PRIZE);
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
        chicane1.draw(batch);
        chicane2.draw(batch);
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            game.setScreen(new MenuScreen(game));
        }
    }

    private void setInputAdapter(Car car) {
        if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {
            Gdx.app.log(LOG_TAG, "Acc available");
            accelerometerHandler = new AccelerometerHandler(car, ACCELEROMETER_INTENSIFIER);
        } else {
            inputAdapter = new KeyBoardHandler(car, TURN_REACTION);
            Gdx.input.setInputProcessor(inputAdapter);
        }
    }

    private void startGameEffect() {
        stopMovement();
        raceStartMusic(MUSIC_VOLUME);
    }

    private void stopMovement() {
        groundSpeed = 0;
        isStatePaused = true;
        backgroundMusic.setVolume(backgroundMusic.getVolume() / 5);
        disableInputs();
    }

    public void resumeMovement() {
        groundSpeed = GROUND_SPEED;
        isStatePaused = false;
        backgroundMusic.setVolume(MUSIC_VOLUME);
        setInputAdapter(car);
    }

    private void disableInputs() {
        accelerometerHandler = null;
        Gdx.input.setInputProcessor(null);
    }

    private void initMusic(float volume) {
        backgroundMusic = MusicUtils.initAndPlay(MUSIC_BG, volume, true);
    }

    private void raceStartMusic(float volume) {
        car.playCarStartSounds(volume, this);
    }

    @Override
    public void dispose() {
        Gdx.app.log(LOG_TAG, "Dispose called.");
        car.dispose();
        batch.dispose();
        chicane1.dispose();
        chicane2.dispose();
        score.dispose();
        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).dispose();
        }
        backgroundMusic.dispose();
    }
}
