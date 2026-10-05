package fr.javaquest.entity.layerPlayer;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LayeredAllWeapons {


    private Animation<TextureRegion> currentWeapon;

    public void draw(
        SpriteBatch batch,
        float x,
        float y,
        float stateTime,
        boolean facingRight
    ) {
        TextureRegion frame =
            currentWeapon.getKeyFrame(stateTime, true);

        float w = frame.getRegionWidth();
        float h = frame.getRegionHeight();

        if (facingRight) {
            batch.draw(frame, x, y, w, h);
        } else {
            batch.draw(frame, x + w, y, -w, h);
        }
    }
}
