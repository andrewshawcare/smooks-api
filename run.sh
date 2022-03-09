#!/bin/sh

down() {
  docker-compose \
    -f docker-compose.yaml \
    -f consumer/docker-compose.yaml \
    -f event-streaming/docker-compose.yaml \
    -f telemetry/docker-compose.yaml \
    down -v
}

build() {
    docker-compose \
    -f docker-compose.yaml \
    -f consumer/docker-compose.yaml \
    -f event-streaming/docker-compose.yaml \
    -f telemetry/docker-compose.yaml \
    build
}

up() {
   docker-compose \
     -f docker-compose.yaml \
     -f consumer/docker-compose.yaml \
     -f event-streaming/docker-compose.yaml \
     -f telemetry/docker-compose.yaml \
     up
}

down && build && up

trap down EXIT