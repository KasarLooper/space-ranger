package com.mygdx.game.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;


public class TextView extends View{
    protected BitmapFont font;
    protected String text;

    public TextView(BitmapFont font, float x, float y) {
        super(x, y);
        this.font = font;
    }

    public TextView(BitmapFont font, float x, float y, String text) {
        this(font, x, y);
        this.text = text;

        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        width = glyphLayout.width;
        height = glyphLayout.height;
    }
    public void setText(String text) {
        this.text = text;
        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        width = glyphLayout.width;
        height = glyphLayout.height;
    }

    public void TextPosition(){
        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        width = glyphLayout.width;
        height = glyphLayout.height;

        this.x = GameSettings.SCREEN_WIDTH / 2f - glyphLayout.width / 2f;
    }

    @Override
    public void draw(SpriteBatch batch) {
        font.draw(batch, text, x, y + height);
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}

