package com.kasarlooper.spaceranger.components;

import static com.kasarlooper.spaceranger.GameSettings.SCREEN_HEIGHT;
import static com.kasarlooper.spaceranger.GameSettings.SCREEN_WIDTH;
import static com.kasarlooper.spaceranger.GraphicsSettings.TEXT_PADDING;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kasarlooper.spaceranger.GraphicsSettings;

public class MiddleTextView extends View {
    protected BitmapFont font;
    protected String text;
    GlyphLayout gl;
    public MiddleTextView(BitmapFont font, String text) {
        super(0,0);
        this.font = font;
        setText(text);
    }

    public void setText(String text) {
        this.text = text;

        gl = new GlyphLayout();
        gl.setText(font, text, font.getColor(), SCREEN_WIDTH - TEXT_PADDING * 2, 1, true);
        width = gl.width;
        height = gl.height;

        x = GraphicsSettings.TEXT_PADDING;
        y = (SCREEN_HEIGHT + height) / 2f;
    }
    @Override
    public void draw(SpriteBatch batch) {
        font.draw(batch, gl, x, y);
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
