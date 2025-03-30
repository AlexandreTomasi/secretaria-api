# Usando a imagem mais recente do JDK 24
FROM eclipse-temurin:24-jdk AS builder

WORKDIR /app
COPY . .

# Instala o Maven e faz o build
RUN apt-get update && \
    apt-get install -y maven && \
    mvn clean package -DskipTests && \
    apt-get remove -y maven && \
    apt-get autoremove -y && \
    rm -rf /var/lib/apt/lists/*

# Usando a mesma imagem JDK para runtime (jre ainda não disponível)
FROM eclipse-temurin:24-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]