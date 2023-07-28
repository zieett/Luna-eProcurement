FROM openjdk:17
EXPOSE 8761
ADD target/registry-service-0.0.1-SNAPSHOT.jar registry-service.jar
ENV PROFILE=default
ENTRYPOINT ["java", "-jar", "/registry-service.jar.jar","--spring.profiles.active=${PROFILE}"]