# Rapport du premier rendu


## Lilian
Mon binôme a commencé le projet avec quelques classes de base, que j'ai ensuite organisé en packages pour le MVC, j'ai ensuite fait une première implémentation de l'observer que nous avons ensuite retravaillé pour mieux l'adapter à la situation.

J'ai ensuite codé la vue pour pouvoir afficher les tuiles du labyrinthe, j'ai crée les images des tuiles avec photoshop.
J'ai ensuite crée la disposition de base de la vue avec java swing. 
L'étape suivante à été de coder un algorithme permettant de déterminer la rotation à effectuer sur une image à partir de l'attribut ArrayList<Direction> d'un objet Tile donné en paramètre. L'algorithme se trouve dans la fonction getTileImage qui retourne l'image correspondant à la tile. 
J'ai aussi rajouté une fonction toBufferedImage dans la classe ImageHelper de sorte à pouvoir passer le bon type aux fonctions permettant d'effectuer la rotation des images.