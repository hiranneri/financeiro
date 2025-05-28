FROM alpine/java:21.0.4-jre
VOLUME /tmp
COPY target/financeiro-0.0.1-SNAPSHOT.jar financeiro.jar
ENTRYPOINT ["java", "-jar", "/financeiro.jar"]
