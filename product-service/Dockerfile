FROM openjdk:17
EXPOSE 8500
ENV PROFILE=default
ENV FE_HOST=fe
ENV EUREKA_HOST=registry-service
ENV EUREKA_PORT=8761
ADD target/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar
ENTRYPOINT ["java", "-jar", "/auth-service.jar","--spring.profiles.active=${PROFILE}"]