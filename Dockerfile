FROM openjdk:11-jdk
COPY target/hotel-booking-1.0.0-SNAPSHOT-runner.jar hotel-booking-1.0.0-SNAPSHOT-runner.jar
ENTRYPOINT ["java","-jar","/hotel-booking-1.0.0-SNAPSHOT-runner.jar"]
