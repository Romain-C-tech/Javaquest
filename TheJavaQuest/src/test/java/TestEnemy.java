import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.javaquest.MapViewer;
import fr.javaquest.entity.enemies.Enemy;
import fr.javaquest.entity.enemies.EnemyData;
import fr.javaquest.entity.playerEntity.PlayerSteerable;

public class TestEnemy extends Enemy {
    TestEnemy(EnemyData data, float x, float y, MapViewer mapViewer,
              PlayerSteerable target) {
        super(data, x, y, mapViewer, target);
    }

    @Override
    public void render(SpriteBatch batch) {}

    @Override
    public void dispose() {}
}