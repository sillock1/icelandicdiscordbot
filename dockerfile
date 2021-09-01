FROM gradle:7.2.0-jdk16 AS build
COPY . /app/
WORKDIR /app
RUN gradle --build-cache assemble

FROM openjdk:16
RUN mkdir /app
COPY --from=build /app/build/libs/*.jar /app/IcelandicDiscordBot.jar
ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar", "/app/IcelandicDiscordBot.jar"]