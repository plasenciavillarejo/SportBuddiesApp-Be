# Usar una imagen con OpenJDK y Slim para un contenedor más ligero
FROM openjdk:17.0.2-jdk-slim

# Instalar el paquete tzdata para las zonas horarias
RUN apt-get update && apt-get install -y tzdata

# Establecer la zona horaria a Europe/Madrid
ENV TZ=Europe/Madrid

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivos necesarios para la configuración de Maven y permisos
COPY pom.xml ./
COPY .mvn ./.mvn
COPY mvnw ./

# Copiamos el settings.xml
COPY settings.xml /root/.m2/settings.xml

# Cambiar permisos para el archivo mvnw
RUN chmod +x mvnw

# Instalar dependencias sin compilar el proyecto
RUN ./mvnw clean package -Dmaven-test-skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

# Copiar el resto del proyecto al contenedor
COPY src ./src

# Compilar el proyecto, saltando los tests
RUN ./mvnw clean package -DskipTests

# Exponer el puerto de depuración y el puerto del servicio
EXPOSE 8453
EXPOSE 9000

# Comando de inicio
CMD ["java", "-Duser.timezone=Europe/Madrid", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8454", "-jar", "target/SportBuddiesApp-Be-Oauth-0.0.1.jar"]
