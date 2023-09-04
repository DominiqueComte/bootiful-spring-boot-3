#! /usr/bin/env bash
JAR_VERSION=$(mvn help:evaluate -Dexpression=project.version -DforceStdout -q)
java -jar target/client-${JAR_VERSION}.jar
