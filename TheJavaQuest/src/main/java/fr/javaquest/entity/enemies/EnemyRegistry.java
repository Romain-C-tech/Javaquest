package fr.javaquest.entity.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.Map;

/**
 * Récupération des ennemies qui sont stockées sous format Json.
 * <p>
 *     Gestion d'erreur si l'ennemie demandée n'est pas dans le fichier Json.
 * </p>
 * @author Romain-C-tech
 */
public final class EnemyRegistry {
    private static final Map<String, EnemyData> ENEMIES = new HashMap<>();
    private EnemyRegistry() {}

    @SuppressWarnings("unchecked")
    /**
     * Méthode permettant de charger les données du Json dans ENEMIES.
     * @author Romain-C-tech
     */
    public static void load() {
        Json json = new Json();
        Array<EnemyData> list = json.fromJson(
                Array.class, EnemyData.class, Gdx.files.internal("data/enemies.json"));
        ENEMIES.clear();
        for (EnemyData d : list) ENEMIES.put(d.id, d);
    }

    /**
     * Permet de récupérer les informations d'un ennemi dans les données à partir de son id.
     * @param id
     * @return
     * @author Romain-C-tech
     */
    public static EnemyData get(String id) {
        EnemyData d = ENEMIES.get(id);
        if (d == null) throw new IllegalArgumentException("Ennemi inconnu : " + id);
        return d;
    }
}
