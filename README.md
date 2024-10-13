# NukePowerplant

## Descripción del Proyecto

Nuke PowerPlant Monitoring System es una aplicación diseñada para la **monitorización nacional** de plantas nucleares. El sistema centraliza la gestión de datos críticos, el seguimiento de los sistemas de control, y el mantenimiento de las plantas nucleares, proporcionando una visión integral del estado operativo de los reactores y sus componentes.

El objetivo principal de la aplicación es asegurar que los operadores de las plantas nucleares puedan **monitorear en tiempo real** el estado de los reactores, registrar incidentes y anomalías, gestionar planes de mantenimiento y coordinar respuestas ante emergencias. La plataforma también ofrece funcionalidades avanzadas como el análisis de sensores en tiempo real, el seguimiento de reportes y la gestión de planes de entrenamiento para los operadores.

## Funcionalidades Principales

- **Monitoreo en tiempo real de reactores nucleares**: Visualización y análisis de datos provenientes de sensores instalados en los reactores, como temperatura, presión y más.
- **Gestión de sistemas de control**: Registro y monitoreo de los sistemas de control distribuidos (DCS), PLCs, SCADA, y sistemas de seguridad instalados en cada reactor.
- **Mantenimiento y planes de revisión**: Gestión de planes de mantenimiento, incluyendo el estado de los equipos clave y el progreso de las inspecciones.
- **Registro de anomalías y emergencias**: Detección de incidentes y anomalías, como alta presión en sistemas de refrigeración, con registro de gravedad y fecha.
- **Gestión de operadores**: Supervisión del personal capacitado y sus entrenamientos relacionados con la seguridad nuclear y el manejo de reactores.
- **Planes de emergencia**: Creación y gestión de planes de emergencia para cada planta, con seguimiento de incidentes y planes de evacuación.

## Estructura del Sistema

La aplicación gestiona las siguientes entidades principales:

- **Supplier**: Proveedores de materiales y equipos a las plantas nucleares.
- **Material**: Recursos suministrados por los proveedores, como combustible nuclear.
- **Report**: Informes de inspecciones y revisiones realizadas en las plantas.
- **Nuclear Plant**: Centrales nucleares monitorizadas por el sistema.
- **Reactor**: Reactores nucleares individuales dentro de una planta.
- **Control System**: Sistemas de control asociados a los reactores (DCS, SCADA, PLCs).
- **Sensor**: Dispositivos que monitorizan parámetros críticos del reactor.
- **Sensor Reading**: Lecturas de los sensores registradas a lo largo del tiempo.
- **Maintenance Plan**: Planes de mantenimiento preventivo y correctivo de la planta.
- **Maintenance**: Actividades de mantenimiento realizadas en los equipos o sistemas de un reactor.
- **Anomaly**: Registro de anomalías detectadas en el reactor, como presión alta o fallos de sistemas.
- **Emergency Plan**: Planes de acción ante emergencias específicas para cada planta.
- **Incident**: Registro de incidentes menores o mayores en la planta.
- **Operator**: Operadores que supervisan los reactores nucleares.
- **Training**: Programas de formación y capacitación para los operadores de planta.

## Tecnologías Utilizadas

### Dependencias Principales

| **Dependencia**                             | **Descripción**                                                                                             | **Versión**     |
|---------------------------------------------|-------------------------------------------------------------------------------------------------------------|----------------|
| `org.springframework.boot:spring-boot-starter-web` | Starter de Spring Boot para crear aplicaciones web usando MVC y otros servicios web.                             | 2.7.18         |
| `org.springframework.boot:spring-boot-starter-data-jpa` | Dependencia para la gestión de bases de datos relacionales con JPA y Spring Data JPA.                              | 2.7.18         |
| `org.springframework.boot:spring-boot-starter-validation` | Starter de Spring Boot para la validación de beans con Hibernate Validator y la especificación Bean Validation. | 2.7.18         |
| `com.h2database:h2`                         | Base de datos en memoria H2, utilizada principalmente en entornos de prueba.                                 | 1.4.200        |
| `org.projectlombok:lombok`                  | Biblioteca que reduce el código boilerplate con generación automática de getters, setters, etc.               | 1.18.22        |
| `org.mapstruct:mapstruct`                   | Herramienta de mapeo automático de objetos entre diferentes clases (DTO y entidades).                        | 1.5.5.Final    |
| `org.mapstruct:mapstruct-processor`         | Procesador de anotaciones para MapStruct, utilizado en la generación de código.                              | 1.5.5.Final    |
| `org.springdoc:springdoc-openapi-ui`        | Herramienta para la generación de documentación de APIs utilizando OpenAPI y Swagger UI.                     | 1.7.0          |
| `org.springframework.boot:spring-boot-devtools` | Herramienta para acelerar el desarrollo con recarga automática y otras funcionalidades útiles.                  | 2.7.18         |
| `org.springframework.boot:spring-boot-starter-test` | Starter para pruebas unitarias e integrales con Spring Boot, incluyendo JUnit, Mockito y Hamcrest.                | 2.7.18         |
| `org.mockito:mockito-core`                  | Biblioteca de mocking utilizada en pruebas unitarias para simular la interacción de objetos.                  | 4.5.1          |
| `org.mockito:mockito-junit-jupiter`         | Integra Mockito con JUnit 5 para realizar pruebas con anotaciones de Mockito.                                | 4.5.1          |
| `com.github.springtestdbunit:spring-test-dbunit` | Herramienta para realizar pruebas de integración con bases de datos en el contexto de Spring.                    | 1.3.0          |
| `org.assertj:assertj-core`                  | Biblioteca para realizar aserciones con una API fluida y fácil de leer en pruebas unitarias.                  | 3.22.0         |
| `org.springframework.boot:spring-boot-starter-security` | Starter de Spring Boot para implementar mecanismos de seguridad.                                              | 2.7.18         |
| `io.jsonwebtoken:jjwt`                      | Biblioteca para trabajar con JWT (JSON Web Tokens), utilizada para autenticación y autorización.               | 0.9.1          |
| `org.springframework.security:spring-security-crypto` | Herramienta para la encriptación y manejo seguro de contraseñas.                                              | 5.7.2          |

## Instalación y Ejecución

### Prerrequisitos

- **Java 17** instalado.
- **Maven 3.6+** para la gestión del proyecto.

### Pasos para ejecutar el backend de la aplicación:

1. Clonar el repositorio:
    ```bash
    git clone https://github.com/ismahl95/NukePowerplant.git
    cd NukePowerplant/backend
    ```

2. Construir el proyecto:
    ```bash
    mvn clean install
    ```

3. Ejecutar la aplicación:
    ```bash
    mvn spring-boot:run
    ```

4. Acceder a la documentación Swagger:
    - Abrir un navegador y navegar a: `http://localhost:8080/swagger-ui.html`

## Contribuciones

Si deseas contribuir al proyecto, puedes realizar un **fork** del repositorio, crear una nueva rama con tus cambios y abrir un **pull request**. Asegúrate de seguir las buenas prácticas de desarrollo y ejecutar las pruebas antes de enviar tus cambios.
