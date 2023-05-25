FROM alpine:3
RUN apk --no-cache add openjdk11-jre-headless
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
