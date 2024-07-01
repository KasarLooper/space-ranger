package com.mygdx.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class JoystickView extends View {
    private Texture circle;
    private Texture zone;
    private int centreX, centreY;
    private int circleX, circleY;
    private int radius;

    public JoystickView(int x, int y) {
        super(x, y);
        circle = new Texture("joystick-circle.png");
        zone = new Texture("joystick-zone.png");
        centreX = x + zone.getWidth() / 2;
        centreY = y + zone.getHeight() / 2;
        circleX = x + centreX;
        circleY = y + centreY;
        radius = Math.max(zone.getWidth(), zone.getHeight()) / 2;
    }

    public void onTouch(int touchX, int touchY) {
        if (getDistanceFromCenter(touchX, touchY) <= radius) {
            circleX = touchX - circle.getWidth() / 2;
            circleY = touchY - circle.getHeight() / 2;
        } else {
            float ratio = (float) touchX / (float) touchY;
            int newX = (int) Math.round((double) radius / Math.sqrt(ratio * ratio + 1));
            int newY = Math.round((float) newX / ratio);
            if (touchX <= centreX) newX = -newX;
            if (touchY <= centreY) newY = -newY;
            circleX = newX - circle.getWidth() / 2;
            circleY = newY - circle.getHeight() / 2;
        }
    }

    private int getDistanceFromCenter(int x, int y) {
        int dx = Math.abs(x - centreX);
        int dy = Math.abs(y - centreY);
        return (int) Math.round(Math.sqrt(dx * dx + dy * dy));
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(circle, circleX, circleY);
        batch.draw(zone, x, y);
    }

    @Override
    public void dispose() {
        circle.dispose();
        zone.dispose();
    }
}
