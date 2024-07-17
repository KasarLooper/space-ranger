package com.mygdx.game.components;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;

public class View implements Disposable {
    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public View(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }
    public View(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public boolean isHit(float tx, float ty) {
        return (tx >= x && tx <= x + width && ty >= y && ty <= y + height);
    }
    public void TextPosition(){
        GlyphLayout glyphLayout = new GlyphLayout();
        width = glyphLayout.width;
        height = glyphLayout.height;

        this.x = GameSettings.SCREEN_WIDTH / 2f - glyphLayout.width / 2f;
    }


    public void draw(SpriteBatch batch) {
    }

    @Override
    public void dispose() {
    }
}
