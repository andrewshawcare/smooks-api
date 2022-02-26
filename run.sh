#!/bin/sh

down() {
  docker-compose \
    -f docker-compose.yaml \
    -f telemetry/docker-compose.yaml \
    down -v
}

build() {
    docker-compose build
}

up() {
   docker-compose \
     -f docker-compose.yaml \
     -f telemetry/docker-compose.yaml \
     up
}

down && build && up

trap down EXIT