# Telemetry

## Getting started

To add telemetry reporting to your application, add this folder to your project and ensure it is instrumented with an
[instrumentation library](https://opentelemetry.io/docs/concepts/instrumenting/).

For example, to add [Java instrumentation](https://github.com/open-telemetry/opentelemetry-java-instrumentation), add
the following to your Dockerfile (this presumes you are launching a JAR named `application.jar`):

```dockerfile
#...
ENV JAVAAGENT_GROUP_ID=io.opentelemetry.javaagent
ENV JAVAAGENT_ARTIFACT_ID=opentelemetry-javaagent
ENV JAVAAGENT_ARTIFACT_VERSION=1.11.0
ENV JAVAAGENT_ARTIFACT=${JAVAAGENT_GROUP_ID}:${JAVAAGENT_ARTIFACT_ID}:${JAVAAGENT_ARTIFACT_VERSION}
RUN mvn dependency:get -Dartifact=${JAVAAGENT_ARTIFACT} \
    && mvn dependency:copy -Dartifact=${JAVAAGENT_ARTIFACT} -DoutputDirectory=. -Dmdep.stripVersion=true \
    && mv ${JAVAAGENT_ARTIFACT_ID}.jar javaagent.jar
#...
ENTRYPOINT ["java", "-javaagent:./javaagent.jar", "-jar", "application.jar"]
```

A collection of environment variables need to be set in your `docker-compose.yaml` to use all telemetry features:

```yaml
environment:
      OTEL_EXPORTER_OTLP_ENDPOINT: http://opentelemetry-collector:4317
      OTEL_LOGS_EXPORTER: otlp
      OTEL_SERVICE_NAME: org.acme.service
      OTEL_RESOURCE_ATTRIBUTES: job=app
```

Once complete, you can invoke your application with telemetry as follows:

```shell
docker-compose -f docker-compose.yaml -f telemetry/docker-compose.yaml up
```

And remember to use the same convention when invoking `docker-compose down`:

```shell
docker-compose -f docker-compose.yaml -f telemetry/docker-compose.yaml down
```