FROM openjdk:17

ENV KAFKA_VERSION=3.1.0
ENV KAFKA_SCALA_VERSION=2.13
RUN \
  curl https://dlcdn.apache.org/kafka/${KAFKA_VERSION}/kafka_${KAFKA_SCALA_VERSION}-${KAFKA_VERSION}.tgz \
    --location \
    --output /kafka.tgz \
  && tar -xzf /kafka.tgz \
  && rm /kafka.tgz

WORKDIR /kafka_${KAFKA_SCALA_VERSION}-${KAFKA_VERSION}

RUN \
  ./bin/kafka-storage.sh format \
    -t "$(./bin/kafka-storage.sh random-uuid)" \
    -c ./config/kraft/server.properties \
  && sed --in-place --regexp-extended \
    "s|^advertised.listeners=.*|advertised.listeners=PLAINTEXT://kafka:9092|" \
    ./config/kraft/server.properties

ENTRYPOINT ["./bin/kafka-server-start.sh"]
CMD ["./config/kraft/server.properties"]