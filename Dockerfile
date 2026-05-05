FROM tomcat:10.0-jdk17

# Rimuovi le app di default di Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia i file web nella ROOT
COPY web/ /usr/local/tomcat/webapps/ROOT/

# Copia i sorgenti e compila direttamente con servlet-api.jar di Tomcat
COPY src/ /tmp/src/

RUN find /tmp/src -name "*.java" -not -path "*/test/*" > /tmp/sources.txt && \
    cat /tmp/sources.txt && \
    mkdir -p /usr/local/tomcat/webapps/ROOT/WEB-INF/classes && \
    javac --release 17 \
      -cp "/usr/local/tomcat/lib/servlet-api.jar:/usr/local/tomcat/webapps/ROOT/WEB-INF/lib/mysql-connector-java-5.1.46.jar" \
      -d /usr/local/tomcat/webapps/ROOT/WEB-INF/classes \
      @/tmp/sources.txt

EXPOSE 8080
CMD ["catalina.sh", "run"]
