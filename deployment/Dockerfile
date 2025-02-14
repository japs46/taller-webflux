FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY applications/app-service/build/libs/taller-webflux-nequi.jar taller-webflux-nequi.jar

ENV JAVA_OPTS=" -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar taller-webflux-nequi.jar" ]