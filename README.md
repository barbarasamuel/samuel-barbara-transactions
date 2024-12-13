# Projet Pay my buddy
##A propos
Pay my buddy est un projet permettant à tout utilisateur s'étant enregistré et connecté de faire des virements vers un autre compte lui appartenant ou vers le compte d'un(e) ami(e) existant dans la liste proposée.
Tout virement entraîne un prélèvement chez l'utilisateur de 0.5% de la somme virée destiné au développeur de l'application.
Une fois connecté, il est possible de modifier son login ou son mot de passe, d'envoyer un message à l'administrateur.
On peut prolonger sa connexion grâce à une case à cocher RememberMe ou se déconnecter du site web.
Les transactions effectuées par l'utilisateur connecté sont listées par défaut de la plus récente à la plus ancienne.

##Documentation
Le diagramme de classe UML avec le type de relation entre les classes modèles et les classes entity en passant par le package mapper, et un extrait du design pattern MVC mis en place où les classes modèles sont en relation avec les classes controller et les classes entity sont en relation avec les classes repository.

Le modèle physique des données où la table connection:
- vérifie qu'il n'y ait pas deux fois la même valeur dans le champ email.
- permet grâce à sa jointure avec la table relation, à une connection (un utilisateur) d'avoir plusieurs amis (d'autres connections).
- permet grâce à sa jointure avec la table bank_account à une connection d'avoir plusieurs comptes bancaires.
- permet grâce à sa jointure avec la table transactions à une connection d'avoir plusieurs transactions. 
- permet grâce à sa jointure avec la table contact à une connection d'envoyer plusieurs messages.

Les scripts SQL de la base de données

