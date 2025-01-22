# Etapa 1: Build
FROM openjdk:17.0.2-jdk-slim AS builder

# Establecer directorio de trabajo
WORKDIR /build

# Copiar archivos esenciales de Maven para aprovechar la cache
COPY pom.xml ./
COPY .mvn ./.mvn
COPY mvnw ./
COPY settings.xml /root/.m2/settings.xml

# Descargar dependencias (esto se cachea si el código no cambia)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copiar el resto del proyecto al contenedor
COPY src ./src

# Recogemos el valor definido dentro de nuestro compose
ARG MAVEN_OPTS

# Construir la aplicación, saltando pruebas
RUN ./mvnw clean package -DskipTests ${MAVEN_OPTS}

# Etapa 2: Runtime
FROM openjdk:17.0.2-jdk-slim

# Establecer directorio de trabajo
WORKDIR /app

# Instalar tzdata para la configuración de zona horaria
RUN apt-get update && apt-get install -y --no-install-recommends tzdata && \
    rm -rf /var/lib/apt/lists/*

# Establecer la zona horaria
ENV TZ=Europe/Madrid

# Copiar el archivo JAR desde la etapa de build
COPY --from=builder /build/target/*.jar SportBuddiesApp-Be-Main-0.0.1.jar

# Exponer el puerto del servicio
EXPOSE 9000

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "SportBuddiesApp-Be-Main-0.0.1.jar"]