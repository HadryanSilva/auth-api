FROM bellsoft/liberica-runtime-container:jdk-21-musl AS builder

WORKDIR /app
ADD ./ /app/auth-api
RUN cd auth-api && ./mvnw -Dmaven.test.skip=true clean package

FROM bellsoft/liberica-runtime-container:jre-21-slim-musl AS runtime
WORKDIR /app
COPY --from=builder /app/auth-api/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/app/app.jar"]