# Backend challenge

## Dependencias
- Docker
- Docker compose

## Ejecutar con docker compose

habiendo clonado el repositorio

    docker compose up --build

Servicio disponible en 

    localhost:8080
Documentación interactiva con swagger ui

    http://localhost:8080/webjars/swagger-ui/index.html
    	
## Test suite

    mvn test

## Endpoints

    POST api/sum
request body:

    {
        "operand1":Double,
        "operand2:" Double
    }
operands pueden ser null, y el request body tambien puede ser null

    Get api/history
Muestra todas las operaciones guardadas en BDD hechos en api/sum

   ## Estructura del proyecto
   El proyecto intenta acercarse lo más posible a arquitectura hexagonal

```
challenge/
├── .dockerignore
├── .gitattributes
├── .gitignore
├── compose.yaml
├── Dockerfile
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── .mvn/
│   └── wrapper/
│       ├── maven-wrapper.jar
│       ├── maven-wrapper.properties
│       └── MavenWrapperDownloader.java
├── docs/
│   └── pull_request_template.md
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── tenpo/
│   │   │           └── challenge/
│   │   │               ├── ChallengeApplication.java
│   │   │               ├── application/
│   │   │               ├── domain/
│   │   │               ├── infrastructure/
│   │   │               └── interfaces/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── tenpo/
│                   └── challenge/
│                       ├── ChallengeApplicationTests.java
│                       ├── application/
│                       ├── domain/
│                       ├── infrastructure/
│                       └── interfaces/
```

## Posibles mejoras o proximos pasos
- Paginación  > api/history
- Mayor coverage de tests unitarios sobretodo en la parte de la llamada a terceros
 