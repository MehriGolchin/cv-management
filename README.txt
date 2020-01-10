# CV Management 

Address: git@github.com:MehriGolchin/cv-management.git

Etudiantes: Mehri GOLCHIN KHARAZI - Chabha MEDJNOUN

## Environment

Version JDK : 11
Serveur TomEE : http://jean-luc.massat.perso.luminy.univ-amu.fr/ens/arch-app/ress/apache-tomee-7.1.0-plus.tar.gz


## Getting Involved

- Le projet est composé de 2 packages de couche métier:

1. Models et Services

# Les models:
- Person
- Activity

# Les Beans:
- PersonService 
- ActivityService
Elles se composent des méthodes pour retourner les entités, créer, mettre à jour ou supprimer qui sont accessible à un utilisateur connecté et des méthodes qui retournent toutes les personnes trouvé par prénom, nom et titre d'activité et accessible à tous les utilisateurs. Ces classes sont Stateless et elles sont partagées entre les utilisateurs. 

- LoginService
Elle est responsable de gérer la connexion des utilisateurs et est de type Stateful.


2. Tests

# Lest tests:
- PersonTest
- ActivityTest
- LoginTest
Ces classes testent toutes les méthodes avec leurs permissions. Pour faire marcher les permissions, il y a des classes(helper) qui permettent à notre code de test de s'exécuter dans l'étendue de sécurité souhaitée.


Et les Controllers:

3. Controllers
- ActivityController
- LoginController
- PersonController
- SessionManagement

La partie du client est basée sur la technologie JSF/Primefaces comprenant les fichiers:

- resources(css, js)
- template
- cofig de faces
- les sources xhtml









