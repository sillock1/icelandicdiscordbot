FROM gradle:7.2.0-jdk16 AS build
COPY . /app/
WORKDIR /app
RUN gradle --build-cache assemble

FROM openjdk:16-alpine
RUN mkdir /app
RUN wget https://github.com/mozilla/geckodriver/releases/download/v0.29.1/geckodriver-v0.29.1-linux64.tar.gz
RUN tar -xzvf *.tar.gz -C /usr/local/bin
RUN apk update
RUN apk add firefox-esr
COPY --from=build /app/build/libs/*.jar /app/IcelandicDiscordBot.jar
ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar", "/app/IcelandicDiscordBot.jar"]