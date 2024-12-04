FROM openjdk:17-jdk-slim
WORKDIR /ebanking
COPY target/eBanking-0.0.1-SNAPSHOT.jar /ebanking/ebanking.jar
EXPOSE 8080
CMD [ "java", "-jar", "ebanking.jar"]