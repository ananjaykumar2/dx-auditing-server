ARG VERSION="1.0.0-SNAPSHOT"

# Using maven base image in builder stage to build Java code.
FROM maven:3-eclipse-temurin-21-jammy as builder

WORKDIR /usr/share/app
COPY pom.xml .
# Downloads all packages defined in pom.xml
RUN mvn clean package
COPY src src

# Build the source code to generate the fatjar
RUN mvn clean package -Dmaven.test.skip=true

# Java Runtime as the base for final image
FROM eclipse-temurin:21.0.5_11-jre-jammy

ARG VERSION
ENV JAR="iudx.auditing.server-cluster-${VERSION}-fat.jar"

WORKDIR /usr/share/app

COPY docs/openapi.yaml docs/apidoc.html docs/

COPY iudx-pmd-ruleset.xml iudx-pmd-ruleset.xml
COPY google_checks.xml google_checks.xml


# Copying dev fatjar from builder stage to final image
COPY --from=builder /usr/share/app/target/${JAR} ./fatjar.jar

EXPOSE 9000

# Creating a non-root user
RUN useradd -r -u 1001 -g root audit-user

USER audit-user