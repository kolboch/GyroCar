package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.MenuScreen;

public class MyGdxGame extends Game {

    public static final int GAME_WIDTH = 480;
    public static final int GAME_HEIGHT = 800;
    public static final String GAME_TITLE = "GyroCar";

    private static final String LOG_TAG = MyGdxGame.class.getSimpleName();

    private SpriteBatch batch;
    private MenuScreen menu;

    @Override
    public void create() {
        batch = new SpriteBatch();
        menu = new MenuScreen(this);
        this.setScreen(menu);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    @Override
    public Screen getScreen() {
        return super.getScreen();
    }
}
