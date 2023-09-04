#  Bootiful Spring Boot 3 by Josh Long @ Spring I/O 2023

Example code from [Bootiful Spring Boot 3](https://youtu.be/FvDSL3pSKNQ), but with Maven not Gradle.

Native compilation not set up yet.

## Service

For the "normal" execution, run `docker compose up -d` to start a Postgres DB, then run `./run.sh` to start the service.

The test application TestServiceApplication does not need the Docker Compose stack to run, starting the PostgreSQL container is managed by Spring Boot.

Example links:
- http://localhost:8080/customers
- http://localhost:8080/customers/Josh
- http://localhost:8080/customers/josh (to demonstrate the error response using the [RFC 7807](https://www.rfc-editor.org/rfc/rfc7807)/[RFC 9457](https://www.rfc-editor.org/rfc/rfc9457) Problem Details format)

## Client

Run `./run.sh`.

Example links:
- http://localhost:9090/graphiql
