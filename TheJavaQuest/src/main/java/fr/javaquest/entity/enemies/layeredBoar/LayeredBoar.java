package fr.javaquest.entity.enemies.layeredBoar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import fr.javaquest.entity.enemies.EnemyData;
import fr.javaquest.entity.enemies.EnemyRenderer;

/**
 * Classe permettant l'affichage des annimations et de la mise à jour de position du Sprite d'un ennemi.
 * @author ssamepi
 */
public class LayeredBoar implements EnemyRenderer {
    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> run;
    private Animation<TextureRegion> takeDamage;

    private Animation<TextureRegion> currentAnimation;

    private final Array<Texture> textures = new Array<>();

    private float stateTime = 0f;

    public float x;
    public float y;

    public boolean facingRight = true;

    public LayeredBoar(EnemyData data){
        idle = load(
            data.renderFileIdle,
            0.15f,
            0,
            3,
            data.frameWidth,
            data.frameHeight
        );
        run = load(
            data.renderFileRun,
            0.15f,
            0,
            3,
            data.frameWidth,
            data.frameHeight
        );
        takeDamage = load(
            data.renderFileGetHit,
            0.15f,
            0,
            3,
            data.frameWidth,
            data.frameHeight
            );
        currentAnimation = idle;
    }

    private Animation<TextureRegion> load(
        String file,
        float frameTime,
        int from,
        int to,
        int frameWidth,
        int frameHeight
    ) {
        Texture texture = new Texture(file);

        texture.setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );

        textures.add(texture);

        TextureRegion[][] regions =
            TextureRegion.split(
                texture,
                frameWidth,
                frameHeight
            );

        TextureRegion[] all =
            new TextureRegion[
                regions.length * regions[0].length
                ];

        int index = 0;

        for (TextureRegion[] row : regions) {
            for (TextureRegion region : row) {
                all[index++] = region;
            }
        }

        TextureRegion[] subset =
            java.util.Arrays.copyOfRange(
                all,
                from,
                to + 1
            );

        return new Animation<>(
            frameTime,
            subset
        );
    }
    public void update(float delta) {
        stateTime += delta;
    }

    @Override public void setIdle()   { play(idle); }
    @Override public void setRun()    { play(run); }
    @Override public void setAttack() { play(run); }
    @Override public void setHit()    { play(takeDamage); }
    @Override public void setDeath()  { play(takeDamage); }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setFacingRight(boolean facingRight) {
        this.facingRight = !facingRight; // inverse parce que les sangliers son déssinés vers la gauche contrairement aux autres sprites
    }
    @Override public float getDeathDuration() { return takeDamage.getAnimationDuration(); }

    private void play(Animation<TextureRegion> anim) {
        if (currentAnimation != anim) {
            currentAnimation = anim;
            stateTime = 0f;
        }
    }
    public void draw(SpriteBatch batch) {
        drawLayer(batch, currentAnimation, 0, 0, 0);
    }

    private void drawLayer(
        SpriteBatch batch,
        Animation<TextureRegion> animation,
        float offSetRightX,
        float offSetLeftX,
        float offSetY
    ) {
        TextureRegion frame =
            animation.getKeyFrame(
                stateTime,
                true
            );

        float width = frame.getRegionWidth();
        float height = frame.getRegionHeight();

        if (facingRight) {

            batch.draw(
                frame,
                x + offSetRightX,
                y + offSetY,
                width,
                height
            );

        } else {
            batch.draw(
                frame,
                x - offSetLeftX + width,
                y + offSetY,
                -width,
                height
            );
        }
    }
    public void dispose() {
        for (Texture texture : textures) {
            texture.dispose();
        }
    }
}
