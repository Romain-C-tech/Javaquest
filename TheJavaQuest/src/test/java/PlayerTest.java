
import com.badlogic.gdx.math.Rectangle;
import fr.javaquest.MapViewer;
import fr.javaquest.entity.Entity;
import fr.javaquest.entity.enemies.Enemy;
import fr.javaquest.entity.enemies.EnemyRegistry;
import fr.javaquest.entity.equipments.Rarity;
import fr.javaquest.entity.equipments.armors.Armor;
import fr.javaquest.entity.equipments.armors.ArmorRegistry;
import fr.javaquest.entity.equipments.weapons.Weapon;
import fr.javaquest.entity.equipments.weapons.WeaponRegistry;
import fr.javaquest.entity.playerEntity.LayeredPlayer;
import fr.javaquest.entity.playerEntity.Player;
import fr.javaquest.entity.playerEntity.PlayerSteerable;
import fr.javaquest.inputs.KeyboardInputs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(GdxExtension.class)
@DisplayName("Tests unitaires du joueur")
public class PlayerTest {
    private Player player;
    private LayeredPlayer visualMock;
    private Enemy target;
    private MapViewer mapViewer;

    @BeforeEach
    public void setUp() {
        mapViewer = mock(MapViewer.class);
        player = new Player(100, 100, mapViewer);
        WeaponRegistry.load();
        ArmorRegistry.load();
        EnemyRegistry.load();

        PlayerSteerable targetable =
                new PlayerSteerable(player, mock(KeyboardInputs.class));

        target = new TestEnemy(
                EnemyRegistry.get("wild_boar"),
                124, 100, mock(MapViewer.class), targetable
        );

        visualMock = mock(LayeredPlayer.class);
        doCallRealMethod().when(visualMock).isFacingRight();
        player.setVisual(visualMock);
    }

    @Test
    @DisplayName("Test d'initialisation du joueur sans équipement")
    public void given_nothing_when_creation_then_basicPlayerStats() {
        assertEquals(200, player.getMaxHp());
        assertEquals(200f, player.getMovementSpeed(), 1.0);
        assertEquals(5, player.getDamage());
        assertEquals(1f, player.getAttackSpeed(), 1f);
        assertNull(player.getWeapon());
    }

    @Test
    @DisplayName("Test d'initialisation du joueur avec de l'équipement")
    public void given_playerEquipments_when_creation_then_playerStatsWithEquipmentStats() {
        // Given
        player.equip(new Weapon(WeaponRegistry.get("iron_sword"), Rarity.NORMAL));
        player.equip(new Armor(ArmorRegistry.get("iron_helmet"), Rarity.COMMON));
        player.equip(new Armor(ArmorRegistry.get("iron_chestplate"), Rarity.LEGEND));
        player.equip(new Armor(ArmorRegistry.get("iron_leggings"), Rarity.NORMAL));

        // Then
        assertEquals(510,player.getMaxHp());
        assertEquals(44, player.getDamage());
        assertEquals(1f, player.getAttackSpeed(),0.1f);
    }

    @Test
    @DisplayName("Test de restoration des points de vie")
    public void given_playerNeedsHeal_when_nearToAngelStatue_then_hpAreRestored() {
        // Given
        player.takeDamage(50);

        // When
        player.restoreLife(10);

        // Then
        assertEquals(200, player.getMaxHp());
        assertEquals(160, player.getHp());
    }

    @Test
    @DisplayName("Test de restoration maximale des points de vie.")
    public void given_playerIsFullHealth_when_nearToAngleStatue_then_hpIsNotHigherThanMaxHp() {
        //Given
        int maximumHp = player.getMaxHp();

        //When
        player.restoreLife(50);

        // Then
        assertEquals(maximumHp,player.getHp());
    }

    @Test
    @DisplayName("Test de restoration de points de vie quand la restoration de points de vie exède le manque.")
    public void given_playerIsCloseToFullHp_when_restoreMoreHpThanPlayerNeed_then_hpPlayerIsMaxHpOfPlayer() {
        //Given
        player.takeDamage(5);

        //When
        player.restoreLife(10);

        //Then
        assertEquals(player.getMaxHp(), player.getHp());
    }

    @Test
    @DisplayName("Test des points de vie du joueur quand les dégats subis sont suppérieur à ses points de vie actuel.")
    public void given_playerIsCloseToDeath_when_playerTakeDamageHigherThenHisActualHp_then_hpPlayerEqualToZero(){
        //Given
        int actualHp = player.getHp();

        //When
        player.takeDamage((player.getHp()+5));

        //Then
        assertEquals(0, player.getHp());
        assertTrue(player.isDead());
    }

    @Test
    @DisplayName("Test de mouvement latéral gauche")
    public void given_playerInputs_when_pressButtonLeft_then_moveToTheLeft() {
        // Given : delta de 0.5 seconde -> distance = 200 * 0.5 = 100 pixels.
        // Nouveau X attendu = 100 - 100 = 0
        float delta = 0.5F;

        // When
        player.moveLeft(delta);

        // Then
        assertEquals(0F,player.getHitbox().x, 0.001F);
        assertFalse(visualMock.isFacingRight());
        verify(visualMock, times(1)).setRun();
    }

    @Test
    @DisplayName("Test de mouvement latéral droit")
    public void given_playerInputs_when_pressButtonRight_then_moveToTheRight() {
        // Given : delta de 0.5 seconde -> distance = 200 * 0.5 = 100 pixels.
        // Nouveau X attendu = 100 + 100 = 200
        float delta = 0.5f;

        // When
        player.moveRight(delta);

        // Then
        assertEquals(200F, player.getHitbox().x, 0.001F);
        assertTrue(visualMock.isFacingRight());
        verify(visualMock, times(1)).setRun();
    }

    @Test
    @DisplayName("Test de mouvement, le joueur va vers la droite et rencontre un mur.")
    public void given_wallInTheRight_when_moveRight_then_positionDoNotChange(){
        //Given
        when(mapViewer.collides(any(Rectangle.class))).thenReturn(true);
        float xBefore = player.getHitbox().x;

        //When
        player.moveRight(1);

        //Then
        assertEquals(xBefore, player.getHitbox().x);
        assertTrue(visualMock.isFacingRight());
    }

    @Test
    @DisplayName("Test de mouvement, le joueur va vers la gauche et rencontre un mur.")
    public void given_wallInTheleft_when_moveLeft_then_positionDoNotChange(){
        //Given
        when(mapViewer.collides(any(Rectangle.class))).thenReturn(true);
        float yBefore = player.getHitbox().y;

        //When
        player.moveLeft(1);

        //Then
        assertEquals(yBefore, player.getHitbox().y);
        assertFalse(visualMock.isFacingRight());


    }


    @Test
    @DisplayName("Test de mouvement, jump quand le joueur est sur le sol.")
    public void given_onGround_when_pressButtonSpace_then_jump() {
        // Given
        player.setOnGround(true);
        player.setVelocityY(0f);

        float expectedJumpSpeed = Player.getJumpSpeed();

        // When
        player.jump();

        // Then
        assertFalse(player.isOnGround());
        assertEquals(expectedJumpSpeed, player.getVelocityY(), 0.001f);
    }

    @Test
    @DisplayName("Test de mouvement, jump quand le joueur est en l'air.")
    public void give_notOnGround_when_pressButtonSpace_then_jump() {
        // Given
        player.setOnGround(false);
        player.setVelocityY(50f);

        // When
        player.jump();

        // Then
        assertFalse(player.isOnGround());
        assertEquals(50f, player.getVelocityY(), 0.001f);
    }

    @Test
    @DisplayName("Test de la méthode d'attaque : vérification du Cooldown")
    public void given_attackOnCD_when_attack_then_doesNoting() {
        // Given
        player.setAttackCooldown(5f);
        ArrayList<Enemy> targets = new ArrayList<>();
        targets.add(target);

        // When
        player.attack(targets);

        // Then
        verify(visualMock, never()).startAttack();
        assertEquals(40,target.getHp());
    }

    @Test
    @DisplayName("Test de la méthode d'attaque : vérification du déclenchement de l'attaque quand il est dans la range")
    public void given_attackReady_when_attack_then_canAttack() {
        // Given
        player.setAttackCooldown(0f);
        player.getVisual().facingRight = true;
        player.equip(new Weapon(WeaponRegistry.get("iron_sword"), Rarity.NORMAL));
        ArrayList<Enemy> targets = new ArrayList<>();
        targets.add(target);

        // When
        player.attack(targets);

        // Then
        verify(visualMock, times(1)).startAttack();
        assertTrue(player.getVisual().isFacingRight());
        assertEquals(0f,target.getHp());
    }

    @Test
    @DisplayName("Test de la méthode d'attaque : vérification de la portée ")
    public void given_attackReadyButNotInRange_when_attack_then_targetDontLooseHp() {
        // Given
        player.setAttackCooldown(0f);
        player.getVisual().facingRight = true;
        player.equip(new Weapon(WeaponRegistry.get("iron_sword"), Rarity.NORMAL));
        ArrayList<Enemy> targets = new ArrayList<>();
        targets.add(target);
        target.setPosition(200,100);

        // When
        player.attack(targets);

        // Then
        verify(visualMock, times(1)).startAttack();
        assertTrue(player.getVisual().isFacingRight());
        assertEquals(40f, target.getHp());
    }
}
