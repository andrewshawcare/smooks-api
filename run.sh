#!/bin/sh
docker-compose \
  -f docker-compose.yaml \
  -f telemetry/docker-compose.yaml \
  down -v \
&& docker-compose build \
&& docker-compose \
  -f docker-compose.yaml \
  -f telemetry/docker-compose.yaml \
  up