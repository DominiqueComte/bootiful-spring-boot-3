#! /usr/bin/env bash
# docker compose up -d
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost/bp
export SPRING_DATASOURCE_PASSWORD=bp
export SPRING_DATASOURCE_USERNAME=bp
JAR_VERSION=$(mvn help:evaluate -Dexpression=project.version -DforceStdout -q)
java -jar target/service-${JAR_VERSION}.jar
