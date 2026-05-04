# Stage 1: compila il codice sorgente
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Scarica Jakarta Servlet API 5.0 (usata da Tomcat 10)
RUN apt-get update -q && apt-get install -y -q curl && \
    curl -sL "https://repo1.maven.org/maven2/jakarta/servlet/jakarta.servlet-api/5.0.0/jakarta.servlet-api-5.0.0.jar" \
         -o /tmp/servlet-api.jar

COPY src/ src/
COPY web/WEB-INF/lib/ lib/

RUN find src -name "*.java" -not -path "*/test/*" > /tmp/sources.txt && \
    cat /tmp/sources.txt && \
    mkdir -p build/classes && \
    javac --release 17 \
      -cp "/tmp/servlet-api.jar:lib/mysql-connector-java-5.1.46.jar" \
      -d build/classes \
      @/tmp/sources.txt 2>&1

# Stage 2: deploy su Tomcat
FROM tomcat:10.0-jdk17

# Rimuovi le app di default di Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia i file web nella ROOT (URL pulito senza /Exchange_stickers/)
COPY web/ /usr/local/tomcat/webapps/ROOT/

# Copia le classi compilate
COPY --from=build /app/build/classes/ /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/

EXPOSE 8080
CMD ["catalina.sh", "run"]
