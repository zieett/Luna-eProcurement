FROM openjdk:17
EXPOSE 8000
ENV DATABASE_HOST=postgres
ENV DATABASE_PORT=5432
ENV DATABASE_NAME=accountdb
ENV DATABASE_USERNAME=postgres
ENV DATABASE_PASSWORD=123456
ENV PROFILE=default
ENV EUREKA_HOST=registry-service
ENV EUREKA_PORT=8761
ADD target/account-service-0.0.1-SNAPSHOT.jar account-service.jar
ENTRYPOINT ["java", "-jar", "/account-service.jar","--spring.profiles.active=${PROFILE}"]