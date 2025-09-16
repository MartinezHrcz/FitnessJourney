FROM eclipse-temurin:24-jdk AS build

RUN apt-get update && \
    apt-get install -y wget && \
    wget https://downloads.apache.org/maven/maven-3/3.8.9/binaries/apache-maven-3.8.9-bin.tar.gz && \
    tar -xvzf apache-maven-3.8.9-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.8.9/bin/mvn /usr/bin/mvn

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:24-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]