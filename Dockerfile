# Stage 1: build con Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Installa servlet-api.jar nel repo Maven locale prima di compilare
COPY web/WEB-INF/lib/servlet-api.jar /tmp/servlet-api.jar
RUN mvn install:install-file \
    -Dfile=/tmp/servlet-api.jar \
    -DgroupId=jakarta.servlet \
    -DartifactId=jakarta.servlet-api \
    -Dversion=5.0.0 \
    -Dpackaging=jar \
    -q

COPY pom.xml .
COPY src/ src/
COPY web/ web/
RUN mvn package -Dmaven.test.skip=true

# Stage 2: deploy su Tomcat
FROM tomcat:10.0-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/exchange-stickers-1.0.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
