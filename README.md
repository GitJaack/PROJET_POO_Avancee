Exécution du Programme

La classe principale de l'application est TestMain.java. Vous pouvez exécuter le programme en utilisant la méthode main de cette classe. Ou alors directement depuis le terminal avec la commande :

java -jar chemin/vers/le/.jar chemin/vers/le/fichier/donnees

Si aucun chemin de fichier n'est fourni en argument, le programme démarrera avec une configuration vide.

Fonctionnalités Implémentées :

Gestion des Villes, des Routes et des Recharges:
Ajout de nouvelles villes avec vérification des doublons.
Ajout de routes entre les villes avec vérification des doublons.
Ajout ou suppression des zones de recharge sans violé la contrainte d'accessibilité.

Configuration Manuelle :
L'utilisateur peut configurer manuellement la communauté en ajoutant ou supprimant les zones de recharge.

Résolution Automatique :
Une méthode resoudreAutomatiquement() tente de résoudre automatiquement la configuration.

Chargement et Sauvegarde de Données :
Prend en charge le chargement de données à partir d'un fichier.
Les données peuvent être sauvegardées dans un fichier.

Fonctionnalités qui peuvent être améliorées :
L'algorithme 2 présenté dans le projet est implementé sans modification personelle.
