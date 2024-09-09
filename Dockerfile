FROM openjdk:17

WORKDIR /app

COPY ./ojbackend.jar /app/

ENV BACKEND_SERVER_PORT=8081

RUN mkdir -p /data/tmp /data/public/avatar /data/testCase

VOLUME ["/data","/data/tmp","/data/public/avatar"]

CMD ["java","-jar", "/app/ojbackend.jar", "--spring.profiles.active=prod"]

EXPOSE $BACKEND_SERVER_PORT
