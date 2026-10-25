package fr.javaquest.entity.equipments.armors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.Map;

/**
 * Récupération des armures qui sont stockées sous format Json.
 * <p>
 *     Gestion d'erreur si l'armure demandée n'est pas dans le fichier Json.
 * </p>
 * @author Romain-C-tech
 */

public final class ArmorRegistry {
    private static final Map<String, ArmorData> ARMORS = new HashMap<>();

    private ArmorRegistry() {}


    /**
     * Chargement des armures.
     * <p>
     *     Récupération des armures stockées dans le fichier Json.
     *     Destruction des armures contenues dans une liste.
     *     Insertion des armures précédemment trouvées pour les insérées dans la liste avec leurs informations.
     * </p>
     */

    @SuppressWarnings("unchecked")
    public static void load() {
        Json json = new Json();
        Array<ArmorData> list = json.fromJson(
                Array.class, ArmorData.class,
                Gdx.files.internal("data/armors.json"));

        ARMORS.clear();
        for (ArmorData data : list) {
            ARMORS.put(data.id, data);
        }
    }

    /**
     * Récupération de l'armure qui correspond à celle demandée.
     * @param id est le nom de l'armure.
     * @return renvois toutes les données associées à l'arme demandée.
     *
     * @author Romain-C-tech
     */
    public static ArmorData get(String id) {
        ArmorData data = ARMORS.get(id);
        if (data == null) {
            throw new IllegalArgumentException("Armure inconnue : " + id);
        }
        return data;
    }
}
