FROM openjdk:17
EXPOSE 8000
ADD target/account-service-0.0.1-SNAPSHOT.jar account-service.jar
ENV DATABASE_HOST=host.docker.internal
ENV DATABASE_PORT=7777
ENV DATABASE_NAME=accountdb
ENV DATABASE_USERNAME=postgres
ENV DATABASE_PASSWORD=123456
ENV PROFILE=default
ENV EUREKA_HOST=registry-service
ENV EUREKA_PORT=8761
ENTRYPOINT ["java", "-jar", "/account-service.jar","--spring.profiles.active=${PROFILE}"]