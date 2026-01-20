# --- STAGE 1: Build del binario nativo (Java 21) ---
FROM ghcr.io/graalvm/native-image-community:21 AS builder

# Installa dipendenze per la build
RUN microdnf install findutils

WORKDIR /build

# Copia file per la cache delle dipendenze
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

# Copia sorgenti e compila il binario nativo
COPY src ./src
RUN ./mvnw native:compile -Pnative -DskipTests

# --- STAGE 2: Runtime minimale (Debian Stable Slim) ---
FROM debian:stable-slim AS runtime

# Installa zlib (necessaria per binari nativi GraalVM) e pulisce subito la cache
RUN apt-get update && apt-get install -y --no-install-recommends \
    zlib1g \
    && rm -rf /var/lib/apt/lists/*

# Creazione utente di sicurezza (shell /bin/false come discusso)
RUN addgroup --system --gid 9999 dockeruser && \
    adduser --system --home /app --uid 9999 --shell /bin/false --ingroup dockeruser dockeruser

WORKDIR /app

# Copia l'eseguibile nativo dallo stage builder
COPY --from=builder /build/target/java-ssh-tunnelizer* /app/ssh-tunnelizer
COPY ./entrypoint.sh /app/entrypoint.sh

# Permessi corretti
RUN chown -R dockeruser:dockeruser /app && \
    chmod 755 /app/entrypoint.sh /app/application

USER dockeruser:dockeruser

# Variabili d'ambiente
ENV SSH_PORT=22 \
    SSH_PRIVATE_KEY="file:/app/private.key" \
    LOG_LEVEL_APP="INFO" \
    LOG_LEVEL_LIB="ERROR"

ENTRYPOINT ["/app/entrypoint.sh"]
