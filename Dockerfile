# ============================================
# BUILD STAGE
# ============================================
FROM gradle:8.7-jdk21 AS build
WORKDIR /app

# Copy only the necessary files to leverage Docker cache
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
RUN ./gradlew dependencies --no-daemon

# Copy the source code
COPY src ./src
RUN ./gradlew clean build -x test --no-daemon

# ============================================
# RUNTIME STAGE
# ============================================
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Install OpenSSL for nimbus jwt
RUN apt-get update && apt-get install -y \
    openssl \
    ca-certificates \
  && rm -rf /var/lib/apt/lists/*

# Generate RSA private key
RUN openssl genpkey \
    -algorithm RSA \
    -out private.pem \
    -pkeyopt rsa_keygen_bits:2048

# Generate RSA public key from the private key
RUN openssl genpkey -algorithm RSA -out private.pem -pkeyopt rsa_keygen_bits:2048 \
 && openssl rsa -pubout -in private.pem -out public.pem
#RUN openssl rsa -pubout -in private.pem -out public.pem

# Copy jar file
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
