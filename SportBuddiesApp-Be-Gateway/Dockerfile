# La variante -slim está basada en Debian y tiene apt-get disponible.
FROM openjdk:17.0.2-jdk-slim

# Instalar el paquete tzdata para las zonas horarias
RUN apt-get update && apt-get install -y tzdata

# Establecer la zona horaria a Europe/Madrid
ENV TZ=Europe/Madrid

# Crea el directorio dentro del target
WORKDIR /app

# Copiar archivos necesarios para resolver las dependencias
COPY pom.xml ./
COPY .mvn ./.mvn
COPY mvnw ./

# Copiamos el settings.xml
COPY settings.xml /root/.m2/settings.xml

ENV JAVA_OPTS="-Duser.timezone=Europe/Madrid -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=8453,server=y,suspend=n"

# Exponer el puerto de depuración
EXPOSE 8453


# Cambiar permisos para el archivo mvnw
RUN chmod +x mvnw

# Compilamos el proyecto saltando los test, no compile el test y no va a ejecutar nada relacionado al codigo fuente.
RUN ./mvnw clean package -Dmaven-test-skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

# Copiar el resto de los archivos del proyecto
COPY src ./src

# Compilar el proyecto saltando los test
RUN ./mvnw clean package -DskipTests

# Exponer el puerto 8090
EXPOSE 8090

ENTRYPOINT ["java", "-jar", "target/SportBuddiesApp-Be-Gateway-0.0.1.jar"]
