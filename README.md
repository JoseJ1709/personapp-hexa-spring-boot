# PersonApp - Aplicación REST con Arquitectura Hexagonal

Aplicación Spring Boot que implementa una API REST para la gestión de personas, teléfonos, profesiones y estudios, utilizando arquitectura hexagonal (ports and adapters) con soporte para MariaDB y MongoDB.

## Tabla de Contenidos

- [Descripción](#descripción)
- [Requisitos Previos](#requisitos-previos)
- [Configuración del Ambiente](#configuración-del-ambiente)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Compilación](#compilación)
- [Despliegue](#despliegue)
- [Endpoints de la API](#endpoints-de-la-api)
- [Pruebas](#pruebas)
- [Scripts DDL y DML](#scripts-ddl-y-dml)
- [Solución de Problemas](#solución-de-problemas)

---

## Descripción

PersonApp es una aplicación desarrollada con **Spring Boot** que implementa una arquitectura hexagonal para la gestión de información de personas. La aplicación soporta dos adaptadores de entrada (REST y CLI) y dos adaptadores de salida (MariaDB y MongoDB).

### Características

- Arquitectura Hexagonal (Ports and Adapters)
- API REST con Swagger 3 (OpenAPI)
- Soporte para MariaDB y MongoDB
- Aplicación CLI interactiva
- CRUD completo para todas las entidades
- Docker Compose para despliegue completo

### Modelo de Datos

- **persona**: Información personal (cc, nombre, apellido, género, edad)
- **telefono**: Teléfonos asociados a personas (número, operador, dueño)
- **profesion**: Profesiones disponibles (id, nombre, descripción)
- **estudios**: Estudios realizados por personas (profesión, persona, fecha, universidad)

---

## Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

- **JDK 11** o superior
- **Maven 3.6+**
- **Docker** y **Docker Compose** (recomendado para despliegue completo)
- **Lombok** configurado en tu IDE
  - IntelliJ IDEA: Settings → Plugins → Lombok (instalar y habilitar)
  - Eclipse: Instalar desde https://projectlombok.org/setup/eclipse

### Verificar Instalación

```bash
java -version    # Debe mostrar Java 11 o superior
mvn -version     # Debe mostrar Maven 3.6+
docker --version # Debe mostrar Docker instalado
docker compose version # Debe mostrar Docker Compose instalado
```

---

## Configuración del Ambiente

### Opción 1: Configuración con Docker Compose (Recomendado)

Esta opción levanta automáticamente todas las dependencias (bases de datos y aplicación).

#### 1. Clonar o descargar el proyecto

```bash
git clone <URL_DEL_REPOSITORIO>
cd personapp-hexa-spring-boot
```

#### 2. Configurar variables de entorno (opcional)

Las configuraciones por defecto están en `docker-compose.yml`. Si necesitas cambiar puertos o credenciales, edita el archivo.

#### 3. Levantar los servicios

```bash
docker compose up -d
```

Este comando:
- Levanta MariaDB en el puerto **3307**
- Levanta MongoDB en el puerto **27017**
- Ejecuta automáticamente los scripts de inicialización (DDL y DML)
- Compila y levanta la API REST en el puerto **3000**

#### 4. Verificar que los servicios estén corriendo

```bash
docker compose ps
```

Deberías ver 3 contenedores corriendo:
- `personapp-mariadb`
- `personapp-mongodb`
- `personapp-rest-api`

#### 5. Ver los logs de la aplicación

```bash
docker compose logs -f personapp-rest
```

### Opción 2: Configuración Manual (Desarrollo)

#### 1. Instalar y configurar las bases de datos

**MariaDB:**
- Instalar MariaDB y configurar en puerto **3307**
- Crear base de datos `persona_db`
- Usuario: `persona_db`, Contraseña: `persona_db`

**MongoDB:**
- Instalar MongoDB y configurar en puerto **27017**
- Crear base de datos `persona_db`
- Usuario: `persona_db`, Contraseña: `persona_db`, Auth DB: `admin`

#### 2. Ejecutar scripts de inicialización

**Para MariaDB:**
```bash
mysql -u persona_db -ppersona_db -h localhost -P 3307 < scripts/persona_ddl_maria.sql
mysql -u persona_db -ppersona_db -h localhost -P 3307 persona_db < scripts/persona_dml_maria.sql
```

**Para MongoDB:**
```bash
mongosh -u persona_db -p persona_db --authenticationDatabase admin persona_db < scripts/persona_init_mongo.js
mongosh -u persona_db -p persona_db --authenticationDatabase admin persona_db < scripts/persona_ddl_mongo.js
mongosh -u persona_db -p persona_db --authenticationDatabase admin persona_db < scripts/persona_dml_mongo.js
```

#### 3. Configurar aplicación.properties

Edita `rest-input-adapter/src/main/resources/application.properties` si es necesario ajustar las conexiones a las bases de datos.

---

## Estructura del Proyecto

```
personapp-hexa-spring-boot/
├── application/              # Capa de aplicación (casos de uso)
│   ├── port/
│   │   ├── in/              # Puertos de entrada (interfaces)
│   │   └── out/             # Puertos de salida (interfaces)
│   └── usecase/             # Implementación de casos de uso
├── domain/                   # Capa de dominio (entidades)
├── cli-input-adapter/        # Adaptador de entrada CLI
├── rest-input-adapter/       # Adaptador de entrada REST
├── maria-output-adapter/     # Adaptador de salida MariaDB
├── mongo-output-adapter/     # Adaptador de salida MongoDB
├── common/                   # Utilidades y anotaciones comunes
├── scripts/                  # Scripts DDL y DML
│   ├── persona_ddl_maria.sql
│   ├── persona_dml_maria.sql
│   ├── persona_ddl_mongo.js
│   └── persona_dml_mongo.js
├── docker-compose.yml        # Configuración Docker Compose
├── Dockerfile               # Imagen Docker de la aplicación
└── pom.xml                  # Configuración Maven padre
```

### Arquitectura Hexagonal

- **Domain**: Entidades del dominio (Person, Phone, Profession, Study)
- **Application**: Casos de uso y puertos (interfaces)
- **Adapters**: Implementaciones concretas
  - **Input Adapters**: REST API, CLI
  - **Output Adapters**: MariaDB Repository, MongoDB Repository

---

## Compilación

### Compilar el proyecto completo

Desde la raíz del proyecto:

```bash
mvn clean install
```

Este comando:
- Compila todos los módulos
- Ejecuta las pruebas (si las hay)
- Genera los JARs en cada módulo `target/`

### Compilar sin ejecutar pruebas

```bash
mvn clean install -DskipTests
```

### Compilar solo un módulo específico

```bash
cd rest-input-adapter
mvn clean package
```

---

## Despliegue

### Opción 1: Despliegue con Docker Compose (Producción)

#### Desplegar todos los servicios

```bash
docker compose up -d
```

#### Detener los servicios

```bash
docker compose down
```

#### Detener y eliminar volúmenes (datos)

```bash
docker compose down -v
```

#### Reconstruir la imagen de la aplicación

```bash
docker compose up -d --build
```

### Opción 2: Despliegue Manual

#### Ejecutar la API REST

**Opción A: Con Maven**
```bash
cd rest-input-adapter
mvn spring-boot:run
```

**Opción B: Con JAR compilado**
```bash
java -jar rest-input-adapter/target/rest-input-adapter-0.0.1-SNAPSHOT.jar
```

#### Ejecutar la aplicación CLI

En una terminal diferente:
```bash
cd cli-input-adapter
mvn spring-boot:run
```

O con JAR:
```bash
java -jar cli-input-adapter/target/cli-input-adapter-0.0.1-SNAPSHOT.jar
```

### Verificar el despliegue

Una vez desplegada, la aplicación estará disponible en:

- **API REST**: http://localhost:3000
- **Swagger UI**: http://localhost:3000/swagger-ui.html
- **API Docs (JSON)**: http://localhost:3000/api-docs
- **Health Check**: http://localhost:3000/actuator/health

---

## Endpoints de la API

La API REST expone los siguientes endpoints bajo el prefijo `/api/v1`:

### Personas (`/api/v1/persona`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/persona/{database}` | Obtener todas las personas (database: `MARIA` o `MONGO`) |
| POST | `/api/v1/persona` | Crear una nueva persona |
| PUT | `/api/v1/persona/{dni}` | Actualizar una persona por DNI |
| DELETE | `/api/v1/persona/{dni}/{database}` | Eliminar una persona |

**Ejemplo de request body (POST/PUT):**
```json
{
  "cc": 123456789,
  "nombre": "Juan",
  "apellido": "Pérez",
  "genero": "M",
  "edad": 30
}
```

### Teléfonos (`/api/v1/telefono`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/telefono/{database}` | Obtener todos los teléfonos |
| POST | `/api/v1/telefono` | Crear un nuevo teléfono |
| PUT | `/api/v1/telefono/{numero}` | Actualizar un teléfono por número |
| DELETE | `/api/v1/telefono/{numero}/{database}` | Eliminar un teléfono |

**Ejemplo de request body (POST/PUT):**
```json
{
  "num": "3001234567",
  "oper": "Claro",
  "duenio": 123456789
}
```

### Profesiones (`/api/v1/profesion`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/profesion/{database}` | Obtener todas las profesiones |
| POST | `/api/v1/profesion` | Crear una nueva profesión |
| PUT | `/api/v1/profesion/{id}` | Actualizar una profesión por ID |
| DELETE | `/api/v1/profesion/{id}/{database}` | Eliminar una profesión |

**Ejemplo de request body (POST/PUT):**
```json
{
  "id": 1,
  "nom": "Ingeniero de Software",
  "des": "Desarrollo de aplicaciones y sistemas"
}
```

### Estudios (`/api/v1/estudios`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/estudios/{database}` | Obtener todos los estudios |
| POST | `/api/v1/estudios` | Crear un nuevo estudio |
| PUT | `/api/v1/estudios/{personId}/{professionId}` | Actualizar un estudio |
| DELETE | `/api/v1/estudios/{personId}/{professionId}/{database}` | Eliminar un estudio |

**Ejemplo de request body (POST/PUT):**
```json
{
  "id_prof": 1,
  "cc_per": 123456789,
  "fecha": "2015-06-15",
  "univer": "Universidad Nacional"
}
```

**Nota:** El parámetro `{database}` puede ser `MARIA` o `MONGO` (case-insensitive).

---

## Pruebas

### 1. Probar con Swagger UI

1. Abre tu navegador en: **http://localhost:3000/swagger-ui.html**
2. Verás todos los endpoints disponibles organizados por entidad
3. Para probar un endpoint:
   - Haz clic en el endpoint deseado
   - Haz clic en "Try it out"
   - Ingresa los datos requeridos
   - Haz clic en "Execute"
   - Revisa la respuesta

### 2. Probar con cURL

#### Obtener todas las personas (MariaDB)
```bash
curl -X GET http://localhost:3000/api/v1/persona/MARIA
```

#### Obtener todas las personas (MongoDB)
```bash
curl -X GET http://localhost:3000/api/v1/persona/MONGO
```

#### Crear una nueva persona
```bash
curl -X POST http://localhost:3000/api/v1/persona \
  -H "Content-Type: application/json" \
  -d '{
    "cc": 123456789,
    "nombre": "Juan",
    "apellido": "Pérez",
    "genero": "M",
    "edad": 30
  }'
```

#### Actualizar una persona
```bash
curl -X PUT http://localhost:3000/api/v1/persona/123456789 \
  -H "Content-Type: application/json" \
  -d '{
    "cc": 123456789,
    "nombre": "Juan Carlos",
    "apellido": "Pérez",
    "genero": "M",
    "edad": 31
  }'
```

#### Eliminar una persona
```bash
curl -X DELETE http://localhost:3000/api/v1/persona/123456789/MARIA
```

### 3. Probar con Postman

1. Importa la colección desde Swagger:
   - Ve a http://localhost:3000/api-docs
   - Copia el JSON
   - En Postman: File → Import → Raw text → Pega el JSON
2. O crea las peticiones manualmente usando los endpoints documentados arriba

### 4. Verificar conexión a las bases de datos

**MariaDB:**
```bash
docker exec -it personapp-mariadb mysql -u persona_db -ppersona_db persona_db -e "SELECT * FROM persona LIMIT 5;"
```

**MongoDB:**
```bash
docker exec -it personapp-mongodb mongosh -u persona_db -p persona_db --authenticationDatabase admin persona_db --eval "db.persona.find().limit(5).pretty()"
```

---

## Scripts DDL y DML

Los scripts de base de datos se encuentran en la carpeta `scripts/`:

### MariaDB

- **`persona_ddl_maria.sql`**: Script DDL que crea las tablas:
  - `persona` (cc, nombre, apellido, genero, edad)
  - `profesion` (id, nom, des)
  - `telefono` (num, oper, duenio)
  - `estudios` (id_prof, cc_per, fecha, univer)
  - Incluye claves primarias y foráneas

- **`persona_dml_maria.sql`**: Script DML que inserta datos de ejemplo:
  - 5 profesiones
  - 5 personas
  - 5 teléfonos
  - 5 estudios

### MongoDB

- **`persona_init_mongo.js`**: Script de inicialización que crea el usuario y la base de datos

- **`persona_ddl_mongo.js`**: Script DDL (en MongoDB las colecciones se crean automáticamente, este script crea el usuario)

- **`persona_dml_mongo.js`**: Script DML que inserta datos de ejemplo en las colecciones:
  - `profesion`
  - `persona`
  - `telefono`
  - `estudios`

### Ejecutar scripts manualmente

Si no usas Docker Compose, puedes ejecutar los scripts manualmente:

**MariaDB:**
```bash
mysql -u persona_db -ppersona_db -h localhost -P 3307 < scripts/persona_ddl_maria.sql
mysql -u persona_db -ppersona_db -h localhost -P 3307 persona_db < scripts/persona_dml_maria.sql
```

**MongoDB:**
```bash
mongosh -u persona_db -p persona_db --authenticationDatabase admin persona_db < scripts/persona_init_mongo.js
mongosh -u persona_db -p persona_db --authenticationDatabase admin persona_db < scripts/persona_ddl_mongo.js
mongosh -u persona_db -p persona_db --authenticationDatabase admin persona_db < scripts/persona_dml_mongo.js
```

---

## Solución de Problemas

### Error: Puerto ya en uso

Si el puerto 3000 está ocupado:

**Opción 1:** Cambiar el puerto en `rest-input-adapter/src/main/resources/application.properties`:
```properties
server.port=3001
```

**Opción 2:** Usar variable de entorno:
```bash
export SERVER_PORT=3001
# O en Windows:
set SERVER_PORT=3001
```

**Opción 3:** Cambiar en `docker-compose.yml`:
```yaml
ports:
  - "3001:3000"
```

### Error: No se puede conectar a la base de datos

1. **Verifica que las bases de datos estén corriendo:**
   ```bash
   docker compose ps
   ```

2. **Verifica las credenciales** en `application.properties` o `docker-compose.yml`:
   - MariaDB: usuario `persona_db`, contraseña `persona_db`
   - MongoDB: usuario `persona_db`, contraseña `persona_db`, auth DB `admin`

3. **Verifica los puertos:**
   - MariaDB: **3307** (no 3306)
   - MongoDB: **27017**

4. **Revisa los logs:**
   ```bash
   docker compose logs personapp-rest
   docker compose logs mariadb
   docker compose logs mongodb
   ```

### Error: Lombok no funciona

Asegúrate de tener el plugin de Lombok instalado y habilitado en tu IDE:

- **IntelliJ IDEA**: 
  - Settings → Plugins → Buscar "Lombok" → Instalar
  - Settings → Build, Execution, Deployment → Compiler → Annotation Processors → Habilitar

- **Eclipse**: 
  - Descargar desde https://projectlombok.org/setup/eclipse
  - Ejecutar el instalador

### La aplicación no compila

```bash
# Limpiar y recompilar
mvn clean install -U

# Si persiste, eliminar directorios target
find . -name "target" -type d -exec rm -rf {} +
# O en Windows:
for /d /r . %d in (target) do @if exist "%d" rd /s /q "%d"
```

### Error: Imagen Docker no se encuentra

Si ves el warning sobre `personapp-rest` al hacer `docker compose up`:
- Es normal, Docker intenta buscar la imagen en un registro remoto
- El build se ejecutará automáticamente desde el Dockerfile
- Puedes ignorar el warning

### La aplicación tarda mucho en iniciar

- Es normal, especialmente la primera vez
- La aplicación espera a que las bases de datos estén saludables
- Revisa los logs: `docker compose logs -f personapp-rest`

---

## Notas Importantes

- La aplicación tiene **dos adaptadores de entrada** independientes:
  - **CLI**: Para uso desde terminal (menú interactivo)
  - **REST**: Para uso como API web

- La aplicación soporta **dos bases de datos**:
  - **MariaDB**: Base de datos relacional (puerto 3307)
  - **MongoDB**: Base de datos NoSQL (puerto 27017)

- Los scripts de inicialización se ejecutan automáticamente cuando se levantan los contenedores por primera vez.

- Los logs se guardan en:
  - Local: `logs/persona.log`
  - Docker: Volumen `app_logs`

- El parámetro `{database}` en los endpoints puede ser `MARIA` o `MONGO` (case-insensitive).

---

## Checklist de Verificación

Antes de entregar, verifica:

- [ ] Docker y Docker Compose instalados
- [ ] Java 11 instalado
- [ ] Maven instalado
- [ ] Lombok configurado en el IDE
- [ ] Bases de datos corriendo (MariaDB y MongoDB)
- [ ] API REST accesible en http://localhost:3000
- [ ] Swagger UI accesible en http://localhost:3000/swagger-ui.html
- [ ] Endpoints responden correctamente
- [ ] Scripts DDL y DML ejecutados correctamente
- [ ] Datos de ejemplo cargados en ambas bases de datos

---

## Tecnologías Utilizadas

- **Java 11**
- **Spring Boot**
- **Spring Data JPA** (MariaDB)
- **Spring Data MongoDB**
- **Swagger 3 (OpenAPI)**
- **Docker & Docker Compose**
- **Maven**
- **Lombok**

---

## Licencia

Ver archivo [LICENSE](LICENSE) para más detalles.

---

## Autor
Santiago Botero Pacheo
José Jaramillo
Andrés Centanaro

Desarrollado como parte del Laboratorio 2 - Arquitectura de Software.

