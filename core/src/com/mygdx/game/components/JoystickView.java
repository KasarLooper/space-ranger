package com.mygdx.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameResources;

public class JoystickView extends View {
    private Texture circle;
    private Texture zone;
    private static float centreX;
    private static float centreY;
    private static float circleX;
    private static float circleY;
    private static float radius;


    public JoystickView(float x, float y) {
        super(x, y);
        circle = new Texture(GameResources.JOYSTICK_CIRCLE_IMG_PATH);
        zone = new Texture(GameResources.JOYSTICK_BACK_IMG_PATH);

        centreX = x + zone.getWidth() / 2f;
        centreY = y + zone.getHeight() / 2f;
        circleX = centreX;
        circleY = centreY;
        radius = Math.max(zone.getWidth(), zone.getHeight()) / 2f;
    }

    public void onTouch(int touchX, int touchY) {
        if (getDistanceFromCenter(touchX, touchY) <= radius) {
            circleX = touchX;
            circleY = touchY;
        } else {
            float ratio = (float) Math.sqrt(((touchX - centreX) * (touchX - centreX) + (touchY - centreY) * (touchY - centreY)));
            circleX = centreX + radius * (touchX - centreX) / ratio;
            circleY = centreY + radius * (touchY - centreY) / ratio;
        }
    }

    public void toDefault() {
        circleX = centreX;
        circleY = centreY;
    }

    private float getDistanceFromCenter(int x, int y) {
        float dx = Math.abs(x - centreX);
        float dy = Math.abs(y - centreY);
        return (int) Math.round(Math.sqrt(dx * dx + dy * dy));
    }

    public static float getX() {
        return (circleX - centreX) / radius;
    }

    public static float getY() {
        return (circleY - centreY) / radius;
    }

    public static float getDegrees() {
        double radians = Math.atan2(getY(), getX());
        double degrees = Math.toDegrees(radians);
        if (degrees < 0) degrees += 360;
        return (float) degrees + 270f;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(circle, circleX - (float) circle.getWidth() / 2f, circleY - (float) circle.getHeight() / 2f);
        batch.draw(zone, x, y);
    }

    @Override
    public void dispose() {
        circle.dispose();
        zone.dispose();
    }

    @Override
    public void onCameraUpdate(float dx, float dy) {
        super.onCameraUpdate(dx, dy);
        centreX += dx;
        circleX += dx;
        centreY += dy;
        circleY += dy;
    }
}
