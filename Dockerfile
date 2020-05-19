FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ADD ./target/exercise-0.0.1-SNAPSHOT.jar exercise.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/exercise.jar"]