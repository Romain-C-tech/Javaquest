# Diagrammes UML de TheJavaQuest

Les diagrammes sont repartis par responsabilite pour rester lisibles. Les classes LibGDX
utilisees comme dependances sont simplifiees et marquees `<<LibGDX>>`.

## Application et ecrans

```mermaid
classDiagram
    direction TB

    class Main {
        +main(args: String[])$ void
    }
    class Game {
        -gameplayScreen: GameplayScreen
        -menuScreen: SceneUI
        -brightnessLevel: int
        +create()
        +getMenuScreen() Screen
        +getGameScreen() GameplayScreen
        +setGameScreen(screen: GameplayScreen)
        +cycleBrightness()
        +getBrightness() float
        +getBrightnessLabel() String
    }
    class GameplayScreen {
        -mapViewer: MapViewer
        -gameCamera: GameCamera
        -player: Player
        -playerSteerable: PlayerSteerable
        -enemies: List~Enemy~
        -dummies: List~Dummy~
        -inputs: KeyboardInputs
        -hud: Hud
        -overlay: BrightnessOverlay
        +equipPlayer()
        +spawnDummy()
        +spawnForestEnemies()
        +render(delta: float)
        +resize(width: int, height: int)
        +dispose()
    }
    class SceneUI {
        -stage: Stage
        -skin: Skin
        -javaQuest: Game
        -overlay: BrightnessOverlay
        +show()
        +render(delta: float)
        +resize(width: int, height: int)
        +dispose()
    }
    class MapViewer {
        -width: float
        -height: float
        -collisionsRectangles: Array~Rectangle~
        -restoreLifeRectangle: Array~Rectangle~
        -teleportToForestRectangles: Array~Rectangle~
        -teleportToVillageRectangles: Array~Rectangle~
        +collides(hitbox: Rectangle) boolean
        +getWidth() float
        +getHeight() float
        +getForestTeleportRectangles() Array~Rectangle~
        +getTeleportToVillageRectangles() Array~Rectangle~
        +getRestoreLifeRectangle() Array~Rectangle~
        +render(camera: OrthographicCamera)
        +dispose()
    }
    class GameCamera {
        -camera: OrthographicCamera
        -viewport: ExtendViewport
        +follow(targetX: float, targetY: float)
        +setMapSize(width: float, height: float)
        +resize(width: int, height: int)
        +apply()
        +getCamera() OrthographicCamera
    }
    class BrightnessOverlay {
        +render(brightness: float)
        +dispose()
    }
    class Hud {
        +stage: Stage
        -player: Player
        -healthOrb: HealthOrb
        -playerPos: PlayerPos
        +update()
        +resize(width: int, height: int)
        +dispose()
    }
    class HealthOrb
    class PlayerPos
    class ScreenAdapter {
        <<LibGDX>>
    }
    class LibGDXGame {
        <<LibGDX>>
    }
    class Disposable {
        <<LibGDX interface>>
    }

    Main ..> Game : lance
    Game --|> LibGDXGame
    Game o-- GameplayScreen : conserve la partie
    Game o-- SceneUI : conserve le menu
    Game ..> WeaponRegistry : charge
    Game ..> ArmorRegistry : charge
    Game ..> EnemyRegistry : charge
    Game ..> DummyRegistery : charge
    GameplayScreen --|> ScreenAdapter
    SceneUI --|> ScreenAdapter
    GameplayScreen *-- MapViewer
    GameplayScreen *-- GameCamera
    GameplayScreen *-- Player
    GameplayScreen *-- PlayerSteerable
    GameplayScreen *-- KeyboardInputs
    GameplayScreen *-- Hud
    GameplayScreen *-- BrightnessOverlay
    GameplayScreen o-- "0..*" Enemy
    GameplayScreen o-- "0..*" Dummy
    GameplayScreen --> Game : pause et luminosite
    SceneUI --> Game : navigation et luminosite
    SceneUI *-- BrightnessOverlay
    Hud *-- HealthOrb
    Hud *-- PlayerPos
    Hud --> Player : affiche les informations
    Hud ..|> Disposable
    BrightnessOverlay ..|> Disposable
```

## Entites et intelligence artificielle

```mermaid
classDiagram
    direction TB

    class Fighter {
        <<interface>>
        +getMaxHp() int
        +getHp() int
        +getDamage() int
        +getMovementSpeed() float
        +getAttackSpeed() float
        +getWeapon() Weapon
        +getHitbox() Rectangle
        +takeDamage(amount: int)
    }
    class Combatant {
        <<interface>>
    }
    class Entity {
        <<abstract>>
        #hp: int
        #maxHp: int
        #damage: int
        #movementSpeed: float
        #attackSpeed: float
        #weapon: Weapon
        #armors: Map~ArmorSlot, Armor~
        #hitbox: Rectangle
        +setPosition(x: float, y: float)
        +isDead() boolean
        +keepInside(mapWidth: float, mapHeight: float)
        +update(delta: float)*
    }
    class Player {
        -visual: LayeredPlayer
        -mapViewer: MapViewer
        -attackCooldown: float
        +moveLeft(delta: float)
        +moveRight(delta: float)
        +jump()
        +attack(targets: List~Entity~)
        +restoreLife(amount: int)
        +equip(weapon: Weapon)
        +equip(armor: Armor)
        +update(delta: float)
        +render(batch: SpriteBatch)
    }
    class Enemy {
        <<abstract>>
        #data: EnemyData
        #mapViewer: MapViewer
        -steerable: EnemySteerable
        -targetSteerable: PlayerSteerable
        -stateMachine: DefaultStateMachine
        +attack()
        +update(delta: float)
        +isRemovable() boolean
        +render(batch: SpriteBatch)*
        +dispose()*
    }
    class LayeredEnemy {
        -renderer: EnemyRenderer
        -deathTimer: float
        -hitTimer: float
        +update(delta: float)
        +render(batch: SpriteBatch)
        +isRemovable() boolean
        +dispose()
    }
    class Dummy {
        <<abstract>>
        #data: DummyData
        +render(batch: SpriteBatch)*
        +dispose()*
    }
    class LayeredDummy {
        -character: NpcRenderer
        +render(batch: SpriteBatch)
        +dispose()
    }
    class EnemyData {
        +id: String
        +name: String
        +hp: int
        +damage: int
        +movementSpeed: float
        +attackSpeed: float
        +hitbox: HitboxSize
        +frameWidth: int
        +frameHeight: int
        +renderFileIdle: String
        +renderFileRun: String
        +renderFileGetHit: String
        +renderFileHit: String
        +renderFileDeath: String
        +weapon: String
        +weaponRarity: Rarity
    }
    class DummyData {
        +id: String
        +name: String
        +hitbox: HitboxSize
        +renderFile: String
    }
    class HitboxSize {
        +width: float
        +height: float
    }
    class PlayerSteerable {
        -player: Player
        -inputs: KeyboardInputs
        -position: Vector2
        -linearVelocity: Vector2
        +update()
        +getPlayer() Player
    }
    class EnemySteerable {
        -enemy: Enemy
        -position: Vector2
        -linearVelocity: Vector2
        +updatePositionFromHitbox()
        +applySteering(steering: SteeringAcceleration, delta: float)
    }
    class EnemyState {
        <<enumeration>>
        PATROL
        CHASE
        ATTACK
    }
    class DefaultStateMachine {
        <<LibGDX AI>>
    }
    class KeyboardInputs {
        -player: Player
        -enemies: List~Entity~
        +update(delta: float)
    }
    class Steerable {
        <<LibGDX AI interface>>
    }
    class State {
        <<LibGDX AI interface>>
    }

    note for Combatant "Aucune classe ne l'implemente actuellement."

    Entity ..|> Fighter
    Player --|> Entity
    Enemy --|> Entity
    LayeredEnemy --|> Enemy
    Dummy --|> Entity
    LayeredDummy --|> Dummy
    Entity --> Weapon : arme equipee
    Entity --> Armor : armures par emplacement
    Player --> MapViewer : collisions
    Player *-- LayeredPlayer : apparence
    Enemy *-- EnemySteerable
    Enemy --> PlayerSteerable : cible
    Enemy *-- DefaultStateMachine : machine a etats
    Enemy --> MapViewer : collisions
    Enemy --> EnemyData
    Dummy --> DummyData
    EnemyData --> HitboxSize
    DummyData --> HitboxSize
    PlayerSteerable ..|> Steerable
    EnemySteerable ..|> Steerable
    EnemyState ..|> State
    DefaultStateMachine --> EnemyState : etat courant
    PlayerSteerable --> Player
    PlayerSteerable --> KeyboardInputs
    EnemySteerable --> Enemy
    KeyboardInputs --> Player
    KeyboardInputs --> "0..*" Entity : cibles d'attaque
    EnemyState ..> Enemy : pilote les comportements
```

## Machine a etats de l'ennemi

La machine a etats est initialisee en `PATROL`. Les distances sont mesurees entre le
centre de l'ennemi et celui du joueur. Les transitions suivent les seuils implementes
dans `EnemyState`.

```mermaid
stateDiagram-v2
    direction LR

    [*] --> PATROL : creation de l'ennemi

    PATROL --> CHASE : distance <= 200
    CHASE --> ATTACK : distance <= 30
    CHASE --> PATROL : distance > 300
    ATTACK --> CHASE : distance > 30

    note right of PATROL
        Aucun comportement de patrouille
        supplementaire n'est actuellement applique.
    end note
    note right of CHASE
        L'ennemi poursuit le joueur
        avec son comportement Pursue.
    end note
    note right of ATTACK
        L'ennemi s'arrete et attaque
        quand son cooldown le permet.
    end note
```

La mort est geree par `LayeredEnemy` pour jouer l'animation puis retirer l'ennemi ;
elle ne constitue pas un etat de `EnemyState`.

## Sequence d'une attaque du joueur

Ce diagramme montre le traitement de la touche `F`, de la verification du cooldown
jusqu'a l'application des degats a la premiere cible touchee.

```mermaid
sequenceDiagram
    actor Joueur
    participant Ecran as GameplayScreen
    participant Entrees as KeyboardInputs
    participant Gdx as Gdx.input
    participant Player
    participant Visuel as LayeredPlayer
    participant Arme as Weapon
    participant Cible as Entity cible

    Ecran->>Entrees: update(delta)
    Joueur->>Gdx: appuie sur F
    Entrees->>Gdx: isKeyJustPressed(F)
    Gdx-->>Entrees: true
    Entrees->>Player: attack(enemies)
    Player->>Player: canAttack()

    alt cooldown termine
        Player->>Visuel: startAttack()
        Player->>Player: demarre le cooldown
        Player->>Arme: getAttackHitbox(hitbox, direction)
        Arme-->>Player: zone d'attaque
        loop pour chaque cible
            Player->>Cible: zone.overlaps(hitbox)
            alt cible touchee
                Player->>Player: getDamage()
                Player->>Cible: takeDamage(degats)
                Cible->>Cible: reduit les PV et les borne a zero
                break premiere cible touchee
                    Note over Player,Cible: La boucle s'arrete apres la premiere cible touchee.
                end
            end
        end
    else cooldown actif
        Note over Player: L'attaque est ignoree.
    end
```

## Rendu des personnages

```mermaid
classDiagram
    direction TB

    class EnemyRenderer {
        <<interface>>
        +update(delta: float)
        +draw(batch: SpriteBatch)
        +dispose()
        +setIdle()
        +setRun()
        +setAttack()
        +setHit()
        +setDeath()
        +setPosition(x: float, y: float)
        +setFacingRight(facingRight: boolean)
        +getDeathDuration() float
    }
    class LayeredBoar {
        -idle: Animation
        -run: Animation
        -takeDamage: Animation
        -currentAnimation: Animation
        +update(delta: float)
        +draw(batch: SpriteBatch)
        +dispose()
    }
    class LayeredBoss {
        -idle: Animation
        -run: Animation
        -attack: Animation
        -death: Animation
        -currentAnimation: Animation
        +update(delta: float)
        +draw(batch: SpriteBatch)
        +dispose()
    }
    class LayeredEnemy {
        -renderer: EnemyRenderer
        +render(batch: SpriteBatch)
    }
    class LayeredPlayer {
        -weaponRenderer: WeaponRenderer
        -shieldRenderer: ShieldRenderer
        +update(delta: float)
        +draw(batch: SpriteBatch)
        +dispose()
    }
    class WeaponRenderer {
        -currentWeapon: Animation
        -textures: Array~Texture~
        +setWeapon()
        +startAttack()
        +updateAnimation(delta: float)
        +dispose()
    }
    class ShieldRenderer {
        -currentShield: Animation
        -textures: Array~Texture~
        +getCurrentShield() Animation
    }
    class LayeredDummy {
        -character: NpcRenderer
        +render(batch: SpriteBatch)
        +dispose()
    }
    class NpcRenderer {
        -texture: Texture
        -frame: TextureRegion
        +draw(batch: SpriteBatch)
        +dispose()
    }

    LayeredBoar ..|> EnemyRenderer
    LayeredBoss ..|> EnemyRenderer
    LayeredEnemy *-- EnemyRenderer : choisit selon EnemyData
    LayeredPlayer *-- WeaponRenderer
    LayeredPlayer *-- ShieldRenderer
    LayeredDummy *-- NpcRenderer
```

## Equipements et donnees

```mermaid
classDiagram
    direction TB

    class Equipment {
        <<interface>>
        +getName() String
        +getRarity() Rarity
    }
    class Weapon {
        -data: WeaponData
        -rarity: Rarity
        +getDamage() int
        +getAttackSpeed() float
        +getAttackHitbox(hitbox: Rectangle, facingRight: boolean) Rectangle
        +getRarity() Rarity
    }
    class WeaponData {
        +id: String
        +name: String
        +damage: int
        +attackSpeed: float
        +hitbox: WeaponHitboxData
        +range: float
    }
    class WeaponHitboxData {
        +width: float
        +height: float
        +offsetY: float
    }
    class WeaponRegistry {
        -WEAPONS: Map~String, WeaponData~
        +load()
        +get(id: String)$ WeaponData
    }
    class Armor {
        -data: ArmorData
        -rarity: Rarity
        +getHp() int
        +getSlot() ArmorSlot
        +getName() String
    }
    class ArmorData {
        +id: String
        +name: String
        +slot: ArmorSlot
        +hp: int
    }
    class ArmorRegistry {
        -ARMORS: Map~String, ArmorData~
        +load()
        +get(id: String)$ ArmorData
    }
    class ArmorSlot {
        <<enumeration>>
        HELMET
        CHESTPLATE
        LEGGINGS
    }
    class Rarity {
        <<enumeration>>
        COMMON
        UNCOMMON
        NORMAL
        LEGEND
        EASTEREGG
        +multiplier: float
    }
    class EnemyRegistry {
        -ENEMIES: Map~String, EnemyData~
        +load()
        +get(id: String)$ EnemyData
    }
    class DummyRegistery {
        -DUMMY: Map~String, DummyData~
        +load()
        +get(id: String)$ DummyData
    }
    class Entity {
        <<abstract>>
    }
    class EnemyData
    class DummyData

    Weapon ..|> Equipment
    Armor ..|> Equipment
    Entity --> "0..1" Weapon : arme equipee
    Entity --> "0..*" Armor : armures par emplacement
    Weapon *-- WeaponData
    WeaponData *-- WeaponHitboxData
    Weapon --> Rarity
    Armor *-- ArmorData
    Armor --> Rarity
    ArmorData --> ArmorSlot
    EnemyRegistry ..> EnemyData : charge les donnees
    DummyRegistery ..> DummyData : charge les donnees
    WeaponRegistry ..> WeaponData : charge les donnees
    ArmorRegistry ..> ArmorData : charge les donnees
    EnemyData --> Rarity
```

## HUD

```mermaid
classDiagram
    direction TB

    class Hud {
        -stage: Stage
        -player: Player
        -healthOrb: HealthOrb
        -playerPos: PlayerPos
        +update()
        +resize(width: int, height: int)
        +dispose()
    }
    class HealthOrb {
        -max: float
        -valeur: float
        +setValeur(value: float)
        +setEchelle(echelle: int)
        +act(delta: float)
        +draw(batch: Batch, parentAlpha: float)
        +dispose()
    }
    class PlayerPos {
        #player: Player
        +act(delta: float)
        +draw(batch: Batch, parentAlpha: float)
        +dispose()
    }
    class Player
    class Actor {
        <<LibGDX>>
    }
    class Disposable {
        <<LibGDX interface>>
    }

    Hud *-- HealthOrb
    Hud *-- PlayerPos
    Hud --> Player : lit la vie
    HealthOrb --|> Actor
    PlayerPos --|> Actor
    HealthOrb ..|> Disposable
    PlayerPos ..|> Disposable
    Hud ..|> Disposable
    PlayerPos --> Player : affiche la position
```

### Legende

- `--|>` : heritage ; `..|>` : implementation d'une interface.
- `*--` : composition ; `o--` : agregation ; `-->` / `..>` : association ou dependance.
- Les membres sont limites aux attributs et operations utiles a la comprehension des relations.
