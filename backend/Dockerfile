# Usar la imagen base de Java
FROM openjdk:17-jdk-slim

# Añadir un argumento para la versión de la app
ARG JAR_FILE=target/*.jar

# Copiar el archivo JAR de la aplicación a la imagen Docker
COPY ${JAR_FILE} app.jar

# Exponer el puerto en el que corre la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]