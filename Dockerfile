# ========== BUILD STAGE ==========
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Build source
COPY src ./src
RUN mvn clean package -DskipTests

# ========== RUN STAGE ==========
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy jar từ build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render sẽ gán PORT qua env var)
EXPOSE 5501

# Lệnh chạy: dùng shell form để biến $PORT được thay thế
ENTRYPOINT java -Dserver.port=$PORT -jar app.jar