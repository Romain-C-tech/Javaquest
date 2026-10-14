package fr.javaquest.entity.playerEntity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class ShieldRenderer {

    private Animation<TextureRegion> CurrentShield;
    private Animation<TextureRegion> ShieldWood;
    private Animation<TextureRegion> ShieldCopper;
    private Animation<TextureRegion> ShieldIron;

    private final Array<Texture> textures = new Array<>();

    private float stateTime = 0f;

    public float x;
    public float y;

    public boolean facingRight = true;

    public ShieldRenderer(){
        ShieldCopper = load(
            "Asset/PixelCrawlerFreePack/Weapons/Shield/ShieldCopperAnimation.png",
            0.15f,
            0,
            0,
            16,
            16
        );
        ShieldIron = load(
            "Asset/PixelCrawlerFreePack/Weapons/Shield/ShieldIronAnimation.png",
            0.15f,
            0,
            0,
            16,
            16
        );
        ShieldWood = load(
            "Asset/PixelCrawlerFreePack/Weapons/Shield/ShieldWoodAnimation.png",
            0.15f,
            0,
            0,
            16,
            16
        );

        CurrentShield = null;

    }
    public Animation<TextureRegion> getCurrentShield() {
        return CurrentShield;
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

    public void setCurrentShield(Animation<TextureRegion> currentShield) {
        CurrentShield = currentShield;
    }
}
