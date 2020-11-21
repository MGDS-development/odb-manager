
FROM maven:3-openjdk-11 AS base

FROM base AS build
WORKDIR /build


COPY pom.xml /build/pom.xml
RUN mvn dependency:go-offline

COPY aux /aux

RUN mvn install:install-file -Dfile=/aux/ids-utils-1.1.0-fat.jar -DgroupId=de.fraunhofer.fokus.ids -DartifactId=ids-utils -Dversion=1.1.0 -Dpackaging=jar

COPY src /build/src
RUN mvn package

FROM base AS deploy
COPY --from=build /build/target/odb-manager-*-fat.jar /home/app/odb-manager.jar
WORKDIR /home/app

ENTRYPOINT ["java", "-jar", "odb-manager.jar"]
