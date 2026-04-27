# Étape 1 : build de l'application
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copie des fichiers Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Donne le droit d'exécution au wrapper Maven
RUN chmod +x mvnw

# Télécharge les dépendances
RUN ./mvnw dependency:go-offline

# Copie du code source
COPY src src

# Build du projet sans les tests
RUN ./mvnw clean package -DskipTests

# Étape 2 : image finale légère
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copie du jar généré
COPY --from=build /app/target/*.jar app.jar

# Port exposé par l'application
EXPOSE 9898

# Commande de lancement
ENTRYPOINT ["java", "-jar", "app.jar"]