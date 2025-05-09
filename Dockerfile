# === Step 1: Build Frontend ===
FROM node:current-bullseye-slim AS frontend-builder
WORKDIR /app
COPY frontend ./frontend
WORKDIR /app/frontend
RUN npm install && npm run build

# === Step 2: Build Backend (with frontend) ===
FROM maven:3.9-eclipse-temurin-17 AS backend-builder
WORKDIR /app

COPY backend ./backend
COPY DB ./backend/src/main/resources

COPY --from=frontend-builder /app/frontend/dist/* ./backend/src/main/resources/static

WORKDIR /app/backend
RUN mvn clean package -DskipTests

# === Step 3: Run Application ===
FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY --from=backend-builder /app/backend/target/*.jar ./server.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "server.jar"]
