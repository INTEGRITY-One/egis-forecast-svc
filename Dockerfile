FROM registry.fedoraproject.org/fedora-minimal:latest
VOLUME /tmp
WORKDIR /usr/local/
COPY target/forecast-svc-1.0-SNAPSHOT-runner forecast-svc-runner
RUN chmod 777 ./forecast-svc-runner
ENTRYPOINT ["./forecast-svc-runner"]
EXPOSE 8080