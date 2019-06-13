FROM maven:3.6.1-jdk-8
ADD . /app/myretail
WORKDIR /app/myretail
RUN mvn clean install
CMD java -jar /app/myretail/target/myretail-0.0.1-SNAPSHOT.jar