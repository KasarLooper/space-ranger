package com.kasarlooper.spaceranger.levels.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class BodyWrap {
    final Body body;
    final WorldWrap wWrap;
    boolean isDestroyed;

    BodyWrap(Body body, WorldWrap world) {
        this.body = body;
        this.wWrap = world;
        isDestroyed = false;
    }

    public void destroy() {
        wWrap.removeBody(this);
        wWrap.world.destroyBody(body);
        isDestroyed = true;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setLinearVelocity(float vX, float vY) {
        body.setLinearVelocity(vX, vY);
    }

    public void setLinearVelocity(Vector2 v) {
        body.setLinearVelocity(v);
    }

    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    public void applyLinearImpulse(Vector2 impulse, Vector2 point, boolean wake) {
        body.applyLinearImpulse(impulse, point, wake);
    }

    public Vector2 getWorldCenter() {
        return body.getWorldCenter();
    }

    public void setAwake(boolean flag) {
        body.setAwake(flag);
    }

    public void setBullet(boolean flag) {
        body.setBullet(flag);
    }

    public void setLinearDamping(float linearDamping) {
        body.setLinearDamping(linearDamping);
    }

    public void setAngularVelocity(float omega) {
        body.setAngularVelocity(omega);
    }
}
