FROM openjdk:17
EXPOSE 8000
ENV PROFILE=default
ENV FE_HOST=fe
ENV EUREKA_HOST=registry-service
ENV EUREKA_PORT=8761
ADD target/account-service-0.0.1-SNAPSHOT.jar account-service.jar
ENTRYPOINT ["java", "-jar", "/account-service.jar","--spring.profiles.active=${PROFILE}"]