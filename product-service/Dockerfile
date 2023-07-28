FROM openjdk:17
EXPOSE 8500
ADD target/product-service-0.0.1-SNAPSHOT.jar product-service.jar
ENV DATABASE_HOST=host.docker.internal
ENV DATABASE_PORT=7777
ENV DATABASE_NAME=productdb
ENV DATABASE_USERNAME=postgres
ENV DATABASE_PASSWORD=0989172935
ENV PROFILE=default
ENV FE_HOST=fe
ENV EUREKA_HOST=registry-service
ENV EUREKA_PORT=8761
ENTRYPOINT ["java", "-jar", "/product-service.jar","--spring.profiles.active=${PROFILE}"]