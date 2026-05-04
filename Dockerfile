# Stage 1: compila il codice sorgente
FROM tomcat:10.0-jdk17 AS build
WORKDIR /app

COPY src/ src/
COPY web/WEB-INF/lib/ lib/

RUN find src -name "*.java" ! -path "*/test/*" > /tmp/sources.txt && \
    mkdir -p build/classes && \
    javac --release 17 \
      -cp "/usr/local/tomcat/lib/servlet-api.jar:lib/mysql-connector-java-5.1.46.jar" \
      -d build/classes \
      @/tmp/sources.txt

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
