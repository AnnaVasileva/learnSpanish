Learn Spanish Web Application
=======
Enjoy the power of active-learning by using flashcards, grammar lessons, and tests for each level.
-------

This project was built in order to finish my master degree in Sofia University "St. Kliment Ohridski".

**Thesis:** Secure software system for learning a foreign language via flashcards

**Required Software:**
  * Oracle Database (I'm using Oracle Database 12c, if you do not know how to install it check here - https://www.oracletutorial.com/getting-started/install-oracle/).
  * Suitable IDE for Java-based Spring Boot application (I'm using Eclipse Mars.2 Release (4.5.2), if you do not know how to install it check here -  https://bgasparotto.com/install-eclipse-mars-java-web-plugins with Spring Tools)
  * Lombok library (I'm using Lombok v1.18.12, if you do not know how to install it follow the steps:
	    1. Add the lombok's dependency
      2. Instal lombok.jar from https://projectlombok.org/download
      3. Go to project > right click > Properties > Java Compiler > Annotation Processing > check Enable project specific settings
      4. Restart IDE
      5. In Help > About Eclips IDE you should be able to see Lombok v1.18.12 "Envious Ferret" is installed. https://projectlombok.org/)
      
**Install:**
  1. `git clone https://github.com/AnnaVasileva/learnspanish.git`
  2. Navigate to `yourProjectDirectory\learnspanish\src\main\resources\application.properties` and populate datasources' fields according to your  database' settings.
  3. Run the application - learnspanish > right click > Run As > Spring Boot App or use the hot keys - Alt+Shift+X, B
  4. Navigate to http://localhost:8080.
