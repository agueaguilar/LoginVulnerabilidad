# Usa una imagen base de Maven con JDK 11 para la construcción
FROM maven:3.8.4-openjdk-11 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y el código fuente al contenedor
COPY pom.xml .
COPY src ./src

# Ejecuta Maven para empaquetar la aplicación
RUN mvn clean package

# Usa una imagen más ligera de OpenJDK para ejecutar la aplicación
FROM openjdk:11-jre-slim

# Crea el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR generado al contenedor
COPY --from=build /app/target/LoginApp.jar /app/LoginApp.jar

# Ejecuta la aplicación
CMD ["java", "-jar", "LoginApp.jar"]

# Para instalar Git, si es necesario, en un contenedor Jenkins específico
# Utiliza un Dockerfile separado para Jenkins

# Dockerfile para Jenkins
FROM jenkins/jenkins:lts

# Instalar Git
USER root
RUN apt-get update && apt-get install -y git

# Cambia al usuario Jenkins
USER jenkins
