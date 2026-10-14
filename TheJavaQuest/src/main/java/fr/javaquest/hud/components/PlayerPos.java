package fr.javaquest.hud.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import fr.javaquest.entity.playerEntity.Player;

public class PlayerPos  extends Actor implements Disposable {
    protected Player player;
    protected float x;
    protected float y;
    private final BitmapFont font;


    public PlayerPos(Player player) {
        this.player = player;
        this.font = new BitmapFont();
        this.font.setColor(Color.ORANGE);

        this.x = player.getHitbox().x;
        this.y = player.getHitbox().y;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (player != null && player.getHitbox() != null) {
            this.x = player.getHitbox().x;
            this.y = player.getHitbox().y;
        }

        if (getStage() != null) {
            this.setPosition(20,1550);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        String text = String.format("Pos: X: %d | Y: %d", (int) this.x, (int) this.y);
        font.getData().setScale(2.0F);
        font.draw(batch, text, getX(), getY());
        font.getData().setScale(1.0f);
    }

    @Override
    public void dispose() {
        if (font != null) font.dispose();
    }
}
