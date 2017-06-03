package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

/**
 * Created by Karlo on 2017-06-03.
 */

public class MenuScreen implements Screen {

    private static final String LOG_TAG = MenuScreen.class.getSimpleName();
    private Texture playButton;
    private Game game;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    public MenuScreen(Game game){
        Gdx.app.log(LOG_TAG, "Constructor called.");
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, MyGdxGame.GAME_WIDTH, MyGdxGame.GAME_HEIGHT);
    }

    @Override
    public void show() {
        Gdx.app.log(LOG_TAG, "Show called.");
        playButton = new Texture("playbtn.png");
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        handleInput();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(playButton, camera.position.x - playButton.getWidth() / 2, camera.position.y - playButton.getHeight() / 2);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.app.log(LOG_TAG, "Hide called.");
        dispose();
    }

    @Override
    public void dispose() {
        Gdx.app.log(LOG_TAG, "Dispose called.");
        playButton.dispose();
        batch.dispose();
    }

    private void handleInput(){
        if(Gdx.input.justTouched()){
            this.game.setScreen(new GameScreen(game));
        }
    }
}
