package fr.javaquest.entity.enemies;

import fr.javaquest.entity.HitboxSize;
import fr.javaquest.entity.equipments.Rarity;

/**
 * Tous les ennemies possèdent des caractéristiques communes.
 * <p>
 *     Tous possèdent un identifiant, un nom, des points de vie, des dégats, de la vitesse de déplacement,
 *     de la vitesse d'attaque, une boite de collision, des annimations, une arme de rareté commune.
 * </p>
 *
 * @author Romain-C-tech, ssamepi
 */
public class EnemyData {
    public String id;
    public String name;
    public int hp;
    public int damage;
    public float movementSpeed;
    public float attackSpeed;
    public HitboxSize hitbox;

    public int frameWidth;
    public int frameHeight;
    public String renderFileIdle;
    public String renderFileRun;
    public String renderFileGetHit;
    public String renderFileHit;
    public String renderFileDeath;

    public String weapon;
    public Rarity weaponRarity = Rarity.COMMON;

    public EnemyData() {}
}
