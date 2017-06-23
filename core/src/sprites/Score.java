package sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

import java.text.DecimalFormat;

import interfaces.MyDrawable;

/**
 * Created by Karlo on 2017-06-23.
 */

public class Score implements MyDrawable{


    private static final Color SCORE_BG = Color.WHITE;
    private static final int FONT_SIZE = 60;
    private static final String FONT_FILE_PATH = "fonts/font.ttf";
    private float score;
    private BitmapFont font;
    private Vector2 position;
    private DecimalFormat format;

    public Score(float score, float positionX, float positionY){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_FILE_PATH));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = FONT_SIZE;
        parameter.color = SCORE_BG;
        font = generator.generateFont(parameter);
        this.score = score;
        this.position = new Vector2(positionX, positionY);
        this.format = new DecimalFormat("000.0");
    }

    @Override
    public void draw(SpriteBatch batch) {
        String formatted =  format.format(score);
        GlyphLayout glyphLayout = new GlyphLayout(font, formatted);
        font.draw(batch, formatted, position.x - glyphLayout.width / 2, position.y + glyphLayout.height);
    }

    public void update(float x, float y, float score){
        position.x = x;
        position.y = y;
        this.score += score;
    }

    public void update(float scoreDelta){
        this.score += scoreDelta;
    }

    public void dispose(){
        font.dispose();
    }
}
