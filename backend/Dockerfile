# Usar la imagen base de Java
FROM openjdk:17-jdk-slim

# Definir el nombre específico del archivo JAR
ARG JAR_FILE=target/nuke-powerplant-back-1.0.0.jar

# Copiar el archivo JAR de la aplicación a la imagen Docker
COPY ${JAR_FILE} app.jar

# Exponer el puerto en el que corre la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]