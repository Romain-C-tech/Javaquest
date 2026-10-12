package fr.javaquest.entity.dummy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.javaquest.entity.Entity;

public abstract class Dummy extends Entity {

    protected final DummyData data;

    public Dummy( DummyData data, float x, float y){
        super(200, x, y);
        this.data = data;
    }
    @Override public void update(float delta) {}

    public abstract void render(SpriteBatch batch);

    public abstract void dispose();
}
