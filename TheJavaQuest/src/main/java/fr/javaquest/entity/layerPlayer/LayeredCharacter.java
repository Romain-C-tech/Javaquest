package fr.javaquest.entity.layerPlayer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class LayeredCharacter {

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> run;
    private Animation<TextureRegion> death;
    private Animation<TextureRegion> hands;

    private Animation<TextureRegion> currentAnimation;

    private final Array<Texture> textures = new Array<>();

    private float stateTime = 0f;

    public float x;
    public float y;

    public boolean facingRight = true;

    public LayeredCharacter() {

        idle = load(
            "Asset/PixelCrawlerFreePack/Entities/Npcs/Knight/Idle/Idle-Sheet.png",
            0.15f,
            0,
            3,
            32,
            32
        );

        run = loadRun(
            "Asset/PixelCrawlerFreePack/Entities/Npcs/Knight/Run/Run-Sheet.png",
            0.15f
        );

        death = load(
            "Asset/PixelCrawlerFreePack/Entities/Npcs/Knight/Death/Death-Sheet.png",
            0.15f,
            0,
            8,
            32,
            32
        );

        hands = load(
            "Asset/PixelCrawlerFreePack/Weapons/Hands/Hands.png",
            0.15f,
            0,
            0,
            32,
            16
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

        System.out.println(
            file + " -> " +
                texture.getWidth() + "x" +
                texture.getHeight()
        );

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

    private Animation<TextureRegion> loadRun(
        String file,
        float frameTime
    ) {
        Texture texture = new Texture(file);

        texture.setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );

        textures.add(texture);

        TextureRegion[] frames = new TextureRegion[6];

        for (int i = 0; i < 6; i++) {

            int x = i * 64;

            frames[i] = new TextureRegion(
                texture,
                x + 17,
                32,
                32,
                30
            );
        }

        return new Animation<>(
            frameTime,
            frames
        );
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void setIdle() {
        if (currentAnimation != idle) {
            currentAnimation = idle;
            stateTime = 0f;
        }
    }

    public void setRun() {
        if (currentAnimation != run) {
            currentAnimation = run;
            stateTime = 0f;
        }
    }

    public void setDeath() {
        if (currentAnimation != death) {
            currentAnimation = death;
            stateTime = 0f;
        }
    }

    public void draw(SpriteBatch batch) {

        drawLayer(batch, currentAnimation);

        drawLayer(batch, hands);
    }

    private void drawLayer(
        SpriteBatch batch,
        Animation<TextureRegion> animation
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
                x,
                y,
                width,
                height
            );

        } else {

            batch.draw(
                frame,
                x + width,
                y,
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
