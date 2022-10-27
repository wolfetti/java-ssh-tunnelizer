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

# tunnel
CMDLINE="--tunnel.host=${TUNNEL_HOST} ${CMDLINE}"
CMDLINE="--tunnel.port=${TUNNEL_PORT} ${CMDLINE}"

if [[ "" != "$TUNNEL_LOCAL_PORT" ]]; then
    CMDLINE="--tunnel.local-port=${TUNNEL_LOCAL_PORT} ${CMDLINE}"
fi

# SSH
CMDLINE="--ssh.host=${SSH_HOST} ${CMDLINE}"
CMDLINE="--ssh.user=${SSH_USER} ${CMDLINE}"
CMDLINE="--ssh.private-key=file:${SSH_PRIVATE_KEY} ${CMDLINE}"

if [[ "22" != "$SSH_PORT" ]]; then
    CMDLINE="--ssh.port=${SSH_PORT} ${CMDLINE}"
fi

# logging
CMDLINE="--logging.level.com.jcraft.jsch=${LOG_LEVEL_LIB} ${CMDLINE}"
CMDLINE="--logging.level.org.github.wolfetti=${LOG_LEVEL_APP} ${CMDLINE}"
 
# Application startup
java -jar /app/tunnelizer.jar $CMDLINE
