
# Test Technique - La Française des Jeux
Test technique pour un recrutement chez la Française des Jeux, projet "Parions Sport".

## Instructions du test
Le teste consiste en deux écrans:
- **Page d'accueil**: écran qui liste des équipes de football d'un championnat (ex: la Ligue 1) sur la base d'une recherche effectuée par l'utilisateur.
- **Page détails**: écran qui affiche des détails complets sur une équipe de football suite à une sélection de cette équipe depuis l'écran précédent.

Chaque écran présente un call API à effectuer. La documentation relative à cet API se trouve ici: [https://www.thesportsdb.com/api.php](https://www.thesportsdb.com/api.php).

L'architecture attendue est le **MVP** et le langage de programme attendu est le **Kotlin**. 
Une attention particulière sera portée sur les **tests unitaires**.

L'objectif est de démonter une bonne maîtrise des outils récents et des bonnes pratiques de développement.

## À propos de la réalisation du test
### Architecture du code
Le projet utilise le framework d'injection de dépendances `Hilt` (Dagger2) d'Android X. De fait l'ensemble des couches (MVP + partie API / Data Source) est injectable afin de faciliter les tests unitaires. 

Le projet a été découpé en module de la façon suivante:
- le module `app`, pour l'application ;
- le module `core` pour tout ce qui est commun au projet, notamment les classes de bases pour l'architecture du code ;
- le module `network` pour la couche réseau ;
- le module `feature_home_page` pour l'écran d'accueil et
- le module `feature_team_details` pour l'écran de détails d'une équipe.

*Note: il existe un module `core_test` car malheureusement il est impossible d'avoir du code de test (type classes de base) dans la partie test d'un module qui soit utilisable dans un autre module.*

### Librairies
Les principales dépendances du projet retenues sont:
- Android Architecture Components dans leurs versions stables
- Material Design (via Material Components)
- Couche réseau: `okhttp` + `Retrofit2` + `Gson`
- Librairies utilisées pour le _mocking_ dans les tests unitaires:
	- `MockK` pour le code métier (Presenters et Models) et
	- `MockWebServer` pour la couche réseau.

### Divers
La navigation UI de l'application est assurée avec la `Navigation` d'Android X. 
*Note: malheureusement, du fait du découpage en modules, il est impossible d'utiliser le plugin "Safe Nav Args" associé à cet Architecture Component.*

Le projet supporte Android 5.0 au minimum, cible et compile avec Android 13. Les dernières versions de Gradle et Kotlin sont employées.

Enfin, le repo est configuré avec GitFlow.
