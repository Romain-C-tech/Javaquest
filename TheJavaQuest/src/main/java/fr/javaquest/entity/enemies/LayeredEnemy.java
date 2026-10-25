package fr.javaquest.entity.enemies;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.javaquest.MapViewer;
import fr.javaquest.entity.enemies.ai.EnemyState;
import fr.javaquest.entity.enemies.layeredBoar.LayeredBoar;
import fr.javaquest.entity.enemies.layeredBoss.LayeredBoss;
import fr.javaquest.entity.playerEntity.PlayerSteerable;

/**
 * Classe permettant de gérer le type de rendu en fonction de l'ennemi
 * avec gestion des annimations en fonction du comportement de l'ennemi
 * @author ssamepi
 */
public class LayeredEnemy extends Enemy {

    private final EnemyRenderer renderer;
    private float deathTimer = 0f;
    private static final float DEATH_DURATION = 0.8f;
    private float hitTimer = 0f;
    private static final float HIT_DURATION = 0.3f;

    public LayeredEnemy(EnemyData data, float x, float y, MapViewer mapViewer, PlayerSteerable targetSteerable) {
        super(data, x, y, mapViewer, targetSteerable);
        if (data.id.contains("boar")) {
            renderer = new LayeredBoar(data);
        } else {
            renderer = new LayeredBoss(data);
        }
    }

    @Override
    public void update(float delta) {
        if (isDead()) {
            deathTimer += delta;
            renderer.setDeath();
            renderer.setPosition(hitbox.x, hitbox.y);
            renderer.setFacingRight(isFacingRight);
            renderer.update(delta);
            return;
        }
        super.update(delta);

        hitTimer -= delta;

        State<Enemy> state = getStateMachine().getCurrentState();

        if (hitTimer > 0){
            renderer.setHit();
        }
        else if (state == EnemyState.CHASE) {
            renderer.setRun();
        }
        else if (state == EnemyState.ATTACK) {
            renderer.setAttack();
        }
        else {
            renderer.setIdle();
        }

        renderer.setPosition(hitbox.x, hitbox.y);
        renderer.setFacingRight(isFacingRight);
        renderer.update(delta);
    }

    /**
     * Méthode permettant la vérification de la la fin de l'annimation de mort de l'ennemi.
     * @return boolean
     * @author ssamepi
     */
    public boolean isDeathAnimationFinished() {
        return isDead() && deathTimer >= DEATH_DURATION;
    }

    @Override
    public boolean isRemovable() {
        return isDead() && deathTimer >= renderer.getDeathDuration();
    }

    @Override
    public void render(SpriteBatch batch) {
        renderer.draw(batch);
    }
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        hitTimer = HIT_DURATION;
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
