# Prende servlet-api.jar dall'immagine Tomcat ufficiale
FROM tomcat:10.0-jdk17 AS tomcat

# Stage build: usa JDK con il JAR preso da Tomcat
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY --from=tomcat /usr/local/tomcat/lib/servlet-api.jar /app/lib/servlet-api.jar
COPY web/WEB-INF/lib/mysql-connector-java-5.1.46.jar /app/lib/mysql-connector-java-5.1.46.jar
COPY src/ src/

RUN find src -name "*.java" -not -path "*/test/*" > /tmp/sources.txt && \
    mkdir -p build/classes && \
    javac --release 17 \
      -cp "lib/servlet-api.jar:lib/mysql-connector-java-5.1.46.jar" \
      -d build/classes \
      @/tmp/sources.txt

# Stage finale: Tomcat con le classi compilate
FROM tomcat:10.0-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*
COPY web/ /usr/local/tomcat/webapps/ROOT/
COPY --from=build /app/build/classes/ /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/
EXPOSE 8080
CMD ["catalina.sh", "run"]
