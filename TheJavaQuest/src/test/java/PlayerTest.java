import fr.javaquest.MapViewer;
import fr.javaquest.entity.equipments.Rarity;
import fr.javaquest.entity.equipments.armors.Armor;
import fr.javaquest.entity.equipments.armors.ArmorRegistry;
import fr.javaquest.entity.equipments.weapons.Weapon;
import fr.javaquest.entity.equipments.weapons.WeaponRegistry;
import fr.javaquest.entity.playerEntity.LayeredPlayer;
import fr.javaquest.entity.playerEntity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(GdxExtension.class)
@DisplayName("Tests unitaires du joueur")
public class PlayerTest {
    private Player player;
    private LayeredPlayer visualMock;

    @BeforeEach
    public void setUp() {
        MapViewer mapViewer = mock(MapViewer.class);
        player = new Player(100, 100, mapViewer);
        WeaponRegistry.load();
        ArmorRegistry.load();

        visualMock = mock(LayeredPlayer.class);
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
    @DisplayName("Test de mouvement latéral")
    public void given_playerInputs_when_pressButtonLeft_then_moveToTheLeft() {
        // Given : delta de 0.5 seconde -> distance = 200 * 0.5 = 100 pixels.
        // Nouveau X attendu = 100 - 100 = 0
        float delta = 0.5F;

        // When
        player.moveLeft(delta);

        // Then
        assertEquals(0F,player.getHitbox().x, 0.001F);

        assertFalse(player.getVisual().isFacingRight());
        verify(visualMock, times(1)).setRun();
    }
}
