#!/bin/bash
OK=1

chech_env_var () {
    local env_var=
    env_var=$(declare -p "$1" 2>/dev/null)
    if !  [[ -v $1 && $env_var =~ ^declare\ -x ]]; then
      echo "Error: Define $1 environment variable"
      OK=0
    fi
}

if [[ "" == "$DEV_MODE" ]]; then
    chech_env_var SSH_USER
    chech_env_var SSH_PORT
    chech_env_var SSH_HOST
    chech_env_var SSH_PRIVATE_KEY
    chech_env_var TUNNEL_HOST
    chech_env_var TUNNEL_PORT
    chech_env_var LOG_LEVEL_APP
    chech_env_var LOG_LEVEL_LIB

    if [[ "$OK" == "0" ]]; then
        echo "."
        echo "."
        echo "."
        echo "."
        echo "."
        echo "."
        echo "Please setup all mandatory environment variables and retry"
        echo "."
        echo "."
        echo "."
        echo "."
        echo "."
        echo "."
        echo "."
        sleep 15
        exit 1
    fi
fi

CMDLINE=""

# Local dev mode, use the included dev profile (application-dev.yml)
if [[ "local" == "$DEV_MODE" ]]; then
    CMDLINE="--ssh.private-key=file:/app/ssh/private.key ${CMDLINE}"
    CMDLINE="--spring.profiles.active=dev ${CMDLINE}"

# Normal mode
else 
    # tunnel
    CMDLINE="--tunnel.host=${TUNNEL_HOST} ${CMDLINE}"
    CMDLINE="--tunnel.port=${TUNNEL_PORT} ${CMDLINE}"

    if [[ "" != "$TUNNEL_LOCAL_PORT" ]]; then
        CMDLINE="--tunnel.local-port=${TUNNEL_LOCAL_PORT} ${CMDLINE}"
    fi

    # SSH
    CMDLINE="--ssh.host=${SSH_HOST} ${CMDLINE}"

    if [[ "22" != "$SSH_PORT" ]]; then
        CMDLINE="--ssh.port=${SSH_PORT} ${CMDLINE}"
    fi

    CMDLINE="--ssh.private-key=file:${SSH_PRIVATE_KEY} ${CMDLINE}"
    CMDLINE="--ssh.user=${SSH_USER} ${CMDLINE}"

    # logging
    if [[ "$LOG_LEVEL_LIB" != "ERROR" ]]; then
        CMDLINE="--logging.level.com.jcraft.jsch=${LOG_LEVEL_LIB} ${CMDLINE}"
        CMDLINE="--logging.level.org.springframework=${LOG_LEVEL_LIB} ${CMDLINE}"
    fi
    if [[ "$LOG_LEVEL_APP" != "INFO" ]]; then
        CMDLINE="--logging.level.org.github.wolfetti=${LOG_LEVEL_APP} ${CMDLINE}"
    fi
fi
 
# Application startup
JARFILE=$(/bin/ls /app/*.jar)
if [[ "" != "$DEV_MODE" ]]; then
    echo ""
    echo "Executing command: [java -jar $JARFILE $CMDLINE]"
    echo ""
fi

java -jar $JARFILE $CMDLINE
