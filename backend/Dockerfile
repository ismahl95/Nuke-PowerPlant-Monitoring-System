# Usar la imagen base de Java
FROM openjdk:17-jdk-slim

# Copiar el archivo JAR de la aplicación a la imagen Docker
COPY target/*.jar app.jar

# Exponer el puerto en el que corre la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]