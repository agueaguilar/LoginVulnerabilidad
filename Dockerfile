# Usa una imagen base de OpenJDK
FROM openjdk:11-jre-slim

# Copia el archivo JAR de la aplicación al contenedor
COPY LoginApp.jar /app/LoginApp.jar

# Establece el directorio de trabajo
WORKDIR /app

# Ejecuta la aplicación
CMD ["java", "-jar", "LoginApp.jar"]
