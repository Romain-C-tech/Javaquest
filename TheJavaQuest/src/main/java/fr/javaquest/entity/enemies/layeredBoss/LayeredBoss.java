package fr.javaquest.entity.enemies.layeredBoss;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import fr.javaquest.entity.enemies.EnemyData;
import fr.javaquest.entity.enemies.EnemyRenderer;

public class LayeredBoss implements EnemyRenderer {

    private final Animation<TextureRegion> idle;
    private final Animation<TextureRegion> run;
    private final Animation<TextureRegion> attack;
    private final Animation<TextureRegion> death;

    private Animation<TextureRegion> currentAnimation;

    private final Array<Texture> textures = new Array<>();

    private float stateTime = 0f;

    public float x;
    public float y;
    public boolean facingRight = true;

    public LayeredBoss(EnemyData data) {

        run    = load(data.renderFileRun,   0.12f, 0, 9, data);
        idle   = load(data.renderFileRun,   0.15f, 0, 0, data);
        death  = load(data.renderFileDeath, 0.15f, 0, 3, data);
        attack = load(data.renderFileHit,   0.12f, 0, 5, data);

        currentAnimation = idle;
    }

    private Animation<TextureRegion> load(String file, float frameTime,
                                          int from, int to, EnemyData data) {
        if (file == null) {
            throw new IllegalArgumentException(
                "Chemin de spritesheet manquant dans enemies.json pour : " + data.id);
        }

        Texture texture = new Texture(file);
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        textures.add(texture);

        TextureRegion[][] regions =
            TextureRegion.split(texture, data.frameWidth, data.frameHeight);

        Array<TextureRegion> all = new Array<>();
        for (TextureRegion[] row : regions) {
            for (TextureRegion region : row) {
                all.add(region);
            }
        }

        if (all.size == 0) {
            throw new IllegalStateException(
                "Aucune frame découpée dans " + file + " (frameWidth/frameHeight trop grands ?)");
        }

        int last = Math.min(to, all.size - 1);

        Array<TextureRegion> subset = new Array<>();
        for (int i = from; i <= last; i++) {
            subset.add(all.get(i));
        }

        return new Animation<>(frameTime, subset);
    }

    private void play(Animation<TextureRegion> anim) {
        if (currentAnimation == death) return;
        if (currentAnimation != anim) {
            currentAnimation = anim;
            stateTime = 0f;
        }
    }


    @Override public void setIdle()   { play(idle); }
    @Override public void setRun()    { play(run); }
    @Override public void setAttack() { play(attack); }
    @Override public void setHit()    { play(attack); }
    @Override public void setDeath()  { play(death); }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
    }
    @Override public float getDeathDuration() { return death.getAnimationDuration(); }
    @Override
    public void draw(SpriteBatch batch) {
        boolean loop = currentAnimation != death;
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, loop);

        float width = frame.getRegionWidth();
        float height = frame.getRegionHeight();

        if (facingRight) {
            batch.draw(frame, x, y, width, height);
        } else {
            batch.draw(frame, x + width, y, -width, height);
        }
    }

    @Override
    public void dispose() {
        for (Texture texture : textures) {
            texture.dispose();
        }
    }
}
