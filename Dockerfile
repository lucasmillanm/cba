FROM openjdk:17
MAINTAINER Lucas Millan
COPY build/libs/*.jar cba-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","cba-0.0.1-SNAPSHOT.jar"]