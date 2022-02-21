FROM maven:3-openjdk-17-slim as build
WORKDIR /opt/app

ENV JAVAAGENT_GROUP_ID=io.opentelemetry.javaagent
ENV JAVAAGENT_ARTIFACT_ID=opentelemetry-javaagent
ENV JAVAAGENT_ARTIFACT_VERSION=1.11.0
ENV JAVAAGENT_ARTIFACT=${JAVAAGENT_GROUP_ID}:${JAVAAGENT_ARTIFACT_ID}:${JAVAAGENT_ARTIFACT_VERSION}
RUN mvn dependency:get -Dartifact=${JAVAAGENT_ARTIFACT} \
    && mvn dependency:copy -Dartifact=${JAVAAGENT_ARTIFACT} -DoutputDirectory=. -Dmdep.stripVersion=true \
    && mv ${JAVAAGENT_ARTIFACT_ID}.jar javaagent.jar

COPY pom.xml .
RUN mvn --batch-mode de.qaware.maven:go-offline-maven-plugin:resolve-dependencies

COPY ./src ./src
ENV MAVEN_OPTS='--add-opens java.base/java.lang=ALL-UNNAMED'
RUN mvn --batch-mode --offline package
RUN jar tf ./target/smooks-api-1.jar

FROM openjdk:17
COPY --from=build /opt/app/javaagent.jar ./javaagent.jar
COPY --from=build /opt/app/target/smooks-api-1.jar ./smooks-api-1.jar

ENTRYPOINT ["java", "-javaagent:./javaagent.jar", "-jar", "smooks-api-1.jar"]