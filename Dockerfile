# ============================================
# Etapa 1: Construcción con Maven
# ============================================
FROM maven:3.8.8-eclipse-temurin-11-alpine AS build

WORKDIR /app

# Copiar TODOS los archivos del proyecto
COPY . .

# Compilar el proyecto completo
# Primero instala los módulos internos y luego compila
RUN mvn clean install -DskipTests -B && \
    mvn package -DskipTests -B

# ============================================
# Etapa 2: Imagen de ejecución
# ============================================
FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

# Copiar el JAR compilado desde la etapa de build
COPY --from=build /app/rest-input-adapter/target/*.jar app.jar

# Crear directorio para logs
RUN mkdir -p /app/logs && \
    addgroup -S spring && \
    adduser -S spring -G spring && \
    chown -R spring:spring /app

# Usar usuario no-root
USER spring:spring

# Exponer el puerto
EXPOSE 3000

# Variables de entorno por defecto
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:3000/actuator/health || exit 1

# Comando para ejecutar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]