FROM quay.io/whisk/graalvm:latest
VOLUME /tmp
ARG JAR_FILE
WORKDIR /usr/local/
COPY target/forecast-svc-1.0-SNAPSHOT.jar ./forecast-svc.jar
COPY target/forecast-svc-1.0-SNAPSHOT-runner.jar ./forecast-svc-runner.jar
COPY target/lib ./lib
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/local/forecast-svc-runner.jar"]
EXPOSE 8080