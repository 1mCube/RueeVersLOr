# README - Projet Dev "Ruée Vers L'Or"

## Présentation du Projet
**Ruée Vers L'Or** est un jeu ludopédagogique dans le cadre d’un projet pédagogique de **BUT Informatique (S1)** à l’**IUT de Lille**.

Le jeu propose un objectif amussant tout en travaillant sa culture générale, l'anglais, les maths ou l'histoire.

---

## Structure du Projet

Question.class

Question.java

RueeVersLOr.class

RueeVersLOr.java

.
├── ressources/    (contient les fichiers .txt pour une partie de l'affichage du jeu et les fichiers en .csv pour la sauvegarde et la banque de question

---

## Navigation et Contenu

## Objectif du jeu

Avoir le score le plus élever, ce score augmenter avec les gold récupérer en partie.

Au lancement, vous pouvez vous connecter/inscrire pour que vous puissiez sauvegarde votre progression.

---

### Menu

Suite a votre connection, vous arrivez au menu. Vous disposez de 4 choix : 

1 - Lancer une **Run**

Au lancement d'une run, vous apparaissez sur une map (carte ou vous pouvez vous déplacer), elle est représsenter par un cadriage. Où votre personnage (un cowboy représenter par un C) peu se déplacer à l'aide saisie (z,q,s,d). 
Vous devez aller combattre des monstres(représenter par un M), vous pouvez récuperer des butins(représenter par un □) et des murs(représenter par un ■).
Les coffres à butins offre entre 1 et 5 de gold.

2 - Accéder au **Marchand**

Le marchand permet d'acheter des points de vie en échange de gold. Votre gold va diminuer mais pas votre score (leaderboard).

3 - Voir le **Leaderboard** (classement)

affiche le classement des meilleures joueurs du jeux

4 - **Quitter** le jeu

quitte le jeu

---

### Système de combat 

le combat se lance quand vous aller sur un case ou se trouve un monstre.
Pour chaque question, vous devez choisir une arme qui défini le niveau choisi (de 1 à 3), plus le niveau est haut plus vous infligerez de dégats au monstre.
En contre partie, les questions sont plus difficiles selon la dificulté.

Si vous arriver à tuer le monstre, vous recevrer entre 1 et 5 de gold. Ce qui augmente votre score aussi.

Mais si vous vous perdez et mourrez. Votre score diminue de 5 points.

---

## Équipe de Développement

* **Simon QUENOY** — [simon.quenoy.etu@univ-lille.fr](mailto:simon.quenoy.etu@univ-lille.fr)
* **Yohan MICHEL** — [yohan.michel.etu@univ-lille.fr](mailto:yohan.michel.etu@univ-lille.fr)
