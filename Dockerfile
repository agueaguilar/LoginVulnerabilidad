# Usa una imagen base de Maven con JDK 11
FROM maven:3.8.4-openjdk-11 AS build

# Copia el archivo JAR de la aplicación al contenedor
COPY LoginApp.jar /app/LoginApp.jar

# Establece el directorio de trabajo
WORKDIR /app

# Ejecuta Maven para empaquetar la aplicación
RUN mvn clean package

# Usa una imagen más ligera de OpenJDK para ejecutar la aplicación
FROM openjdk:11-jre-slim

# Copia el archivo JAR generado al contenedor
COPY --from=build /app/target/LoginVulnerabilidad-1.0-SNAPSHOT.jar /app/LoginApp.jar

# Establece el directorio de trabajo
WORKDIR /app

# Ejecuta la aplicación
CMD ["java", "-jar", "LoginApp.jar"]