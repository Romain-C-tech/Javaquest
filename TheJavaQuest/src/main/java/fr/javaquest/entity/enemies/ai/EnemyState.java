package fr.javaquest.entity.enemies.ai;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import fr.javaquest.entity.enemies.Enemy;

public enum EnemyState implements State<Enemy> {

    PATROL {
        @Override
        public void enter(Enemy enemy) {
            // Activer un comportement de patrouille
        }

        @Override
        public void update(Enemy enemy) {
            Vector2 enemyPos = enemy.getSteerable().getPosition();
            Vector2 playerPos = enemy.getTargetSteerable().getPosition();

            if (enemyPos.dst2(playerPos) <= DETECTION_RADIUS_SQ) {
                enemy.getStateMachine().changeState(EnemyState.CHASE);
            }
        }
    },

    CHASE {
        @Override
        public void enter(Enemy enemy) {
            enemy.setSteeringBehavior(enemy.getPursueBehavior());
        }

        @Override
        public void update(Enemy enemy) {
            Vector2 enemyPos = enemy.getSteerable().getPosition();
            Vector2 playerPos = enemy.getTargetSteerable().getPosition();
            float dist2 = enemyPos.dst2(playerPos);

            if (dist2 <= ATTACK_RADIUS_SQ) {
                enemy.getStateMachine().changeState(EnemyState.ATTACK);
            } else if (dist2 > LOSE_TARGET_RADIUS_SQ) {
                enemy.getStateMachine().changeState(EnemyState.PATROL);
            }
        }
    },

    ATTACK {
        @Override
        public void enter(Enemy enemy) {
            enemy.setSteeringBehavior(null);

            enemy.getSteerable().getLinearVelocity().setZero();

            enemy.attack();
        }

        @Override
        public void update(Enemy enemy) {
            Vector2 enemyPos = enemy.getSteerable().getPosition();
            Vector2 playerPos = enemy.getTargetSteerable().getPosition();
            float dist2 = enemyPos.dst2(playerPos);

            if (dist2 > ATTACK_RADIUS_SQ) {
                enemy.getStateMachine().changeState(EnemyState.CHASE);
            }
            if (enemy.canAttack()) {
                enemy.attack();
            }
        }
    };

    private static final float DETECTION_RADIUS_SQ = 200f * 200f;   // 40 000
    private static final float ATTACK_RADIUS_SQ = 30f * 30f;        // 900
    private static final float LOSE_TARGET_RADIUS_SQ = 300f * 300f; // 90 000

    @Override
    public void exit(Enemy enemy) {
    }

    @Override
    public boolean onMessage(Enemy enemy, Telegram telegram) {
        return false;
    }
}