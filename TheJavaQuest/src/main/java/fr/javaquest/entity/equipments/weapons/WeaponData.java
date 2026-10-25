package fr.javaquest.entity.equipments.weapons;

/**
 * Toutes les armes ont un identifiant, un nom, des dégats, une vitesse d'attaque, une zone de dégats, une portée d'attaque.
 *
 * @author Romain-C-tech
 */
public class WeaponData {
    public String id;
    public String name;
    public int damage;
    public float attackSpeed;
    public WeaponHitboxData hitbox;
    public float range;

    public WeaponData() {}
}
