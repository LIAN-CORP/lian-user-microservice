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
FROM amazoncorretto:21-alpine-jdk
WORKDIR /app

# Install OpenSSL for nimbus jwt
RUN apk add --no-cache openssl

# Generate RSA private key
RUN openssl genpkey \
    -algorithm RSA \
    -out private.pem \
    -pkeyopt rsa_keygen_bits:2048

# Generate RSA public key from the private key
RUN openssl rsa -pubout -in private.pem -out public.pem

# Copy jar file
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
