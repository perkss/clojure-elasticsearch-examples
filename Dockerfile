FROM clojure AS build-env
WORKDIR /usr/src/example
COPY project.clj /usr/src/example/
RUN lein deps
COPY . /usr/src/example
RUN mv "$(lein uberjar | sed -n 's/^Created \(.*standalone\.jar\)/\1/p')" elasticsearch-example-standalone.jar

FROM openjdk:8-jre-alpine
WORKDIR /example
COPY --from=build-env /usr/src/example/elasticsearch-example-standalone.jar /example/elasticsearch-example.jar
ENTRYPOINT ["java", "-jar", "/example/elasticsearch-example.jar"]