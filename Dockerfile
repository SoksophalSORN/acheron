FROM eclipse-temurin:17-jdk AS builder

WORKDIR /acheron

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /acheron

COPY --from=builder /acheron/target/*.jar acheron.jar

ENTRYPOINT ["java", "-jar", "acheron.jar"]
