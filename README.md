# JavaQuest

JavaQuest est un jeu 2D développé en Java avec LibGDX. Le joueur peut explorer le
village et la forêt, affronter des ennemis, s'équiper et suivre sa vie depuis le HUD.

## Prérequis

- JDK 21
- Apache Maven

## Lancer le jeu

Depuis le dossier du module `TheJavaQuest` :

```bash
cd TheJavaQuest
mvn compile
```

Lancez ensuite `fr.javaquest.Main` depuis votre IDE, avec l'option JVM
`--enable-preview`. Le projet est configuré pour compiler avec les options preview de
Java 21 et démarre le jeu en plein écran. Les ressources (cartes, sprites et interface)
sont chargées depuis `TheJavaQuest/src/main/resources`.

## Commandes

| Action | Commandes |
| --- | --- |
| Se déplacer à gauche | `A` ou flèche gauche |
| Se déplacer à droite | `D` ou flèche droite |
| Sauter | `Espace`, `W` ou flèche haut |
| Attaquer | `F` |
| Utiliser une zone de téléportation | `E` |
| Ouvrir le menu / mettre en pause | `Échap` |

Dans le menu, vous pouvez reprendre la partie, relancer une partie, changer la luminosité
ou quitter le jeu.

## Diagrammes UML

Le document contient les diagrammes de classes, la machine à états des ennemis et un
diagramme de séquence d'attaque :

[Consulter les diagrammes UML](./DiagramUml.md)

## Tests unitaires

Les tests utilisent JUnit 5 et Mockito. L'extension `GdxExtension` initialise LibGDX en
mode headless, ce qui permet de tester la logique sans ouvrir la fenêtre du jeu.

Depuis `TheJavaQuest`, exécutez tous les tests avec :

```bash
mvn -DargLine=--enable-preview test
```

Pour lancer uniquement les tests du joueur :

```bash
mvn -DargLine=--enable-preview -Dtest=PlayerTest test
```

L'option `--enable-preview` doit être activée pour la compilation comme pour l'exécution
des tests. Les rapports Surefire sont générés dans `TheJavaQuest/target/surefire-reports/`.

## Structure du projet

```text
README.md                      # Présentation et commandes
DiagramUml.md                  # Diagrammes UML
TheJavaQuest/
├── pom.xml
├── assets/                    # Ressources du jeu
└── src/
    ├── main/java/fr/javaquest/ # Code du jeu
    ├── main/resources/         # Cartes, sprites, données et interface
    └── test/java/              # Tests unitaires
```
