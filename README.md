# InstantSystemTestApp

Bonjour ! 

Pour cette petite application j'ai choisi de développer la partie UI en utilisant Jetpack compose car la techno est dans le vent en ce moment et je pense que c'est un super outils ! 
Aussi jetpack compose s'inscrivait bien dans la problématique d'affichage d'une liste d'articles de par la facilité qu'elle offre pour écrire des listes (Recyclerview sur de l'UI classique, lazy layout en compose).
Pour ce qui est de l'architecture, le MVVM était imposé donc je l'ai respecté ! J'ai utilisé le pattern Repository pour les appels API (n'ayant pas d'enjeu pour quand l'application n'a pas de connexion internet ou autre, j'ai seulement implémenté la partie remote sans faire apparaitre de local).
Etant très familier avec okhttp et retrofit c'est les librairies que j'ai choisi pour mes appels API. Pour afficher des images depuis le Web j'ai utilisé Coil qui est une librairie que je connais depuis la sortie de Compose en bêta et avec laquelle je n'ai jamais eu de soucis.
Pour ce qui est parsing Json, j'ai choisi d'utiliser Jackson qui est l'outil que j'utilise pour tous mes devs perso depuis que je travail sur android.
