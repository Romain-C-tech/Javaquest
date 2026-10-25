package fr.javaquest.entity.equipments.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.Map;

/**
 * Récupération des armes qui sont stockées sous format Json.
 * <p>
 *     Gestion d'erreur si l'arme demandée n'est pas dans le fichier Json.
 * </p>
 * @author Romain-C-tech
 */
public final class WeaponRegistry {
    private static final Map<String, WeaponData> WEAPONS = new HashMap<>();

    private WeaponRegistry() {}

    /**
     * Chargement des armes.
     * <p>
     *     Récupération des armes stockées dans le fichier Json.
     *     Destruction des armes contenues dans une liste.
     *     Insertion des armes précédemment trouvées pour les insérées dans la liste avec leurs informations.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public static void load() {
        Json json = new Json();
        Array<WeaponData> list = json.fromJson(
                Array.class, WeaponData.class,
                Gdx.files.internal("data/weapons.json"));

        WEAPONS.clear();
        for (WeaponData data : list) {
            WEAPONS.put(data.id, data);
        }
    }

    /**
     * Récupération de l'arme qui correspond à celle demandée.
     * @param id est le nom de l'arme.
     * @return renvois toutes les données associées à l'arme demandée.
     *
     * @author Romain-C-tech
     */
    public static WeaponData get(String id) {
        WeaponData data = WEAPONS.get(id);
        if (data == null) {
            throw new IllegalArgumentException("Arme inconnue : " + id);
        }
        return data;
    }
}
