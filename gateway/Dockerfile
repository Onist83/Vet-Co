# Étape 1 : build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : image finale légère
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar Vet&CoApp.jar
EXPOSE 8822
ENTRYPOINT ["java", "-jar", "Vet&CoApp.jar"]