FROM debian:bullseye-slim

RUN export DEBIAN_FRONTEND=noninteractive; apt-get update && apt-get -y upgrade \
    && apt-get -y --no-install-recommends install wget apt-transport-https ca-certificates \
    && mkdir -p /etc/apt/keyrings && wget -O - https://packages.adoptium.net/artifactory/api/gpg/key/public | tee /etc/apt/keyrings/adoptium.asc \
    && echo "deb [signed-by=/etc/apt/keyrings/adoptium.asc] https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" | tee /etc/apt/sources.list.d/adoptium.list \
    && apt-get update && apt-get -y --no-install-recommends install temurin-11-jre \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

RUN addgroup --system --gid 9999 dockeruser && adduser --system --home /app --uid 9999 --shell /bin/sh --ingroup dockeruser dockeruser
COPY ./entrypoint.sh /app/entrypoint.sh
COPY ./target/*.jar /app/tunnelizer.jar
RUN chown -R dockeruser:dockeruser /app
RUN chmod 755 /app/entrypoint.sh
USER dockeruser:dockeruser
WORKDIR /app
ENV SSH_PORT 22
ENV SSH_PRIVATE_KEY "file:/app/private.key"
ENV LOG_LEVEL_APP "INFO"
ENV LOG_LEVEL_LIB "ERROR"
ENTRYPOINT ["/app/entrypoint.sh"]
