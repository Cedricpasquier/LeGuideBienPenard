# LeGuideBienPenard


## Presentation

Ce projet respect une architecture MVC avec des singletons dans un application android codé en Java.  
Cette application est une check-list d'objets nécessaires afin de passer un bon confinement.  
Elle dispose également de session utilisateurs afin de proposer des check-lists propres à chacun.  
Cette application utilise le service d’authentification Firebase de Google, elle fait également des appels API Rest sur la real-time database de Firebase.


## Prérequis

- Installation d’Android Studio.
- Import du SDK Firebase authentification sur Android Studio.
- Récupérer la branche develop


## Consignes respéctées

- Écran avec une liste d’éléments
- Écran avec le détail d’un élément
- Appel WebService à une API Rest, PATCH, PUT, GET.
- Stockage de données en cache
- Fonctions supplémentaires :
  - Utilisation d’une architecture MVC
  - Utilisation de fragment
  - Utilisation de Singletons
  - Respect d’un Gitflow
  - Écran d’authentification avec création de compte et vérification du courriel
  - Attention particulière sur le design global de l'application


## Fonctionnalités

### Écran de connexion

- Première écran affichant un champ identifiant et mot de passe

![e1connexion](https://https://github.com/Dedridec/LeGuideBienPenard/blob/master/img_readme/e1connexion.png)

- Les champs entrées sont vérifié et affichent une erreur si une donnée n'est pas conforme

![e1connexionError1](https://https://github.com/Dedridec/LeGuideBienPenard/blob/master/img_readme/e1ConnexionError1.png)![e1connexionError2](https://https://github.com/Dedridec/LeGuideBienPenard/blob/master/img_readme/e1ConnexionError2.png)

- En bas de l'écran, un bouton créer compte permet de changer l'interface afin de créer un compte

![e1creationCompte](https://https://github.com/Dedridec/LeGuideBienPenard/blob/master/img_readme/e1creationCompte.png) ![e1creationCompte2](https://https://github.com/Dedridec/LeGuideBienPenard/blob/master/img_readme/e1creationCompte2.png)

- Quand un compte est créé, un e-mail de vérification est envoyé sur l’adresse renseignée

![emailVerification](https://https://github.com/Dedridec/LeGuideBienPenard/blob/master/img_readme/emailVerification.png) ![emailVerification2](https://https://github.com/Dedridec/LeGuideBienPenard/blob/master/img_readme/emailVerification2.png)


### Écran principal

- L'affichage de l'écran principal commence par une progressBar, elle est présente le temps que l'appel à l’API n’a pas de réponse

![e2load](https://https://github.com/Dedridec/LeGuideBienPenard/blob/master/img_readme/e2load.png)

-	Si vous êtes connecté au réseau, la progressBar disparaît et l’écran affiche un recyclerView avec les éléments de réponse à l’appel API Rest. Ces éléments sont des objets et comporte un champ checkbox qui signifie que vous le possédez ou non.

![e2recyclerView](https://https://github.com/Dedridec/LeGuideBienPenard/blob/master/img_readme/e2recyclerView.png)

-	Lors d’un clic sur une checkbox une requête de type PUT et envoyé sur l’API Rest afin d’enregistré la modification. 

![e2checkbox](https://https://github.com/Dedridec/LeGuideBienPenard/blob/master/img_readme/e2checkbox.png)

-	Si vous n’êtes pas connecté au réseau, une fenêtre pop-up viendra vous notifier que vous n’êtes plus connecté et que les modifications faites ne seront pas enregistrées. Dès lors la recyclerVIew utilisera des données sauvegardées en cache. Cette fonctionnalité peut être testé en se déconnectant du réseau et en rafraîchissant la page à l’aide du bouton en haut à droite

![e2deconnected](https://https://github.com/Dedridec/LeGuideBienPenard/blob/master/img_readme/e2deconnected.png)


### Écran détails des objets

-	Cet écran affiche les détails sur l’objet cliqué dans la recyclerView. Les données affichées sont préchargé en cache dans l’écran précédant. 
Les informations sur l’objet sont :
    - Son importance
    - Les lieux où on-peut le trouver
    - Une courte description

![e3batte](https://https://github.com/Dedridec/LeGuideBienPenard/blob/master/img_readme/e3batte.png) ![e3refregirateur](https://https://github.com/Dedridec/LeGuideBienPenard/blob/master/img_readme/e3refregirateur.png) ![e3livre](https://https://github.com/Dedridec/LeGuideBienPenard/blob/master/img_readme/e3livre.png)




