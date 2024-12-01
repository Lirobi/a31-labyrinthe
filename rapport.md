# Rapport du premier rendu (rendu1)

## Répartition du travail

Lilian s'occupe de créer la vue : swing, la gestion des layouts, la gestion des images, le lien avec le controller.
Arno s'occupe du modèle et du controller.

### Lilian

L'étape suivante à été de coder un algorithme permettant de déterminer la rotation à effectuer sur une image à partir de l'attribut ArrayList<Direction> d'un objet Tile donné en paramètre. L'algorithme se trouve dans la fonction getTileImage qui retourne l'image correspondant à la tile.
J'ai aussi rajouté une fonction toBufferedImage dans la classe ImageHelper de sorte à pouvoir passer le bon type aux fonctions permettant d'effectuer la rotation des images.

### Arno

La classe GameController gère le cycle de vie du modèle. Il crée les tuiles à l'aide de la factory pour s'assurer de la bonne forme de la tuile demandée et éviter d'écrire à la main le tableau de directions mais juste demander la forme que l'on veut, cela évite les risques d'erreurs. Il crée aussi les quatre joueurs et distribuent aléatoirement leurs objectifs.
Un joueur et une tuile ne connaissent pas l'environnement dans lequel ils évoluent seul Board le sait.

## Choix de conception

Nous avons commencé par concevoir le modèle puis nous avons conçu tout autour le patron de conception _observer_ pour créer la vue et écrire les premières méthodes du controller. Après ça nous avons écrit les quelques lignes du main qui ne devraient plus changer.

### Difficultés rencontrées

- Pour représenter la position du joueur nous avons utilisé un tableau de deux éléments x et y cependant cette strcuture ressemble beaucoup à un vecteur deux dimensions. Nous avons donc crées une classe Vector2D pour représenter la position d'un joueur. Cela permet de mieux manipuler la structure.
- Au départ nous étions parti sur un tableau de booléen pour représenter la forme de la tuile après conseil du professeur nous l'avons transformé en ArrayList de Direction qui est énumération des quatre directions possibles. Cela est plus facile à gérer et respecte le paradigme objet.
- La dernière tuile, celle que l'on peut insérer dans le plateau, appartient à la classe Board et non au controller car seule la vue observe le modèle.
- La classe Board génère une erreur si on essaye d'insérer une tuile dans une ligne ou une colonne fixe, le controller doit s'assurer de ne pas lever cette erreur (ce mécanisme pourra évoluer avec la modification de la vue)
- La classe Board possède un dictionnaire de clé Player et de valeur Vector2D, pour l'uml nous avons fait comme il nous semblait pertinent.

### Conceptions manquantes

- Nous n'avons pas encore réfléchit à comment éviter qu'un joueur ne refasse l'action précédente. (un attribut dans Board certainement ou un bouton désactivé dans la vue)
- Les tuiles qui restent fixent d'une partie à une autre (nous pensons ajouter dans la méthode init de Board la création de tuiles de rotation et de forme et d'objectif prédéfinis)

## Implémentations déjà effectuées

Nous avons fini d'implémenter le modèle et le controller.
Il ne manque plus que la vue qui est en cours de programmation car cette dernière est la plus longue à implémenter et à faire fonctionner.
Il est tout à fait possible que les méthodes actuelles du controller changent pour être mieux adapter à la vue.
Le modèle n'a aucune raison de changer sauf si nous rencontrons de trop grandes difficultés ou s'il faut rajouter des méthodes auxquelles nous n'avons pas encore pensées.

Les images ont été faites via photoshop.

## Prochaines étapes

- Ecrire des tests unitaires qui permettront de s'assurer du bon fonctionneement du modèle indépendemment de la vue.
- Finir rapidement la vue pour tester le jeu via l'interface graphique et ainsi améliorer le projet.
- Créer l'écran de fin et donc la gestion de fin de partie (pour l'instant le programme quitte brutalement)
