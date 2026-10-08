package fr.javaquest.entity.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.Map;

public final class EnemyRegistry {
    private static final Map<String, EnemyData> ENEMIES = new HashMap<>();
    private EnemyRegistry() {}

    @SuppressWarnings("unchecked")
    public static void load() {
        Json json = new Json();
        Array<EnemyData> list = json.fromJson(
                Array.class, EnemyData.class, Gdx.files.internal("data/enemies.json"));
        ENEMIES.clear();
        for (EnemyData d : list) ENEMIES.put(d.id, d);
    }

    public static EnemyData get(String id) {
        EnemyData d = ENEMIES.get(id);
        if (d == null) throw new IllegalArgumentException("Ennemi inconnu : " + id);
        return d;
    }
}