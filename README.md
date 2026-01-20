# SSH Tunnelizer

A lightweight Docker container to establish persistent SSH tunnels (Single or Multi-tunnel mode).
## How to Run
### Single Tunnel Mode
Forward a single local port to a remote destination through an SSH jump host.

```yaml
services:
  tunnel:
    image: wolfetti/java-ssh-tunnelizer:stable
    restart: unless-stopped
    volumes:
      - /path/to/your/ssh-keys:/ssh-keys:ro
    environment:
      - SSH_HOST=ssh.server.com
      - SSH_USER=username
      - SSH_PRIVATE_KEY=/ssh-keys/id_rsa
      - TUNNEL_HOST=internal.service.local
      - TUNNEL_PORT=1521
    ports:
      - "1521:1521"
```

### Multi-Tunnel Mode
Manage multiple tunnels simultaneously using a YAML configuration file.

```yaml
services:
  multi-tunnel:
    image: wolfetti/java-ssh-tunnelizer:stable
    restart: unless-stopped
    volumes:
      - /path/to/your/ssh-keys:/ssh-keys:ro
      - /path/to/your/config:/config:ro
    environment:
      - MULTI_TUNNEL_ENABLED=true
      - MULTI_TUNNEL_CONF_PATH=/config/tunnels.yml
      - SSH_HOST=ssh.server.com
      - SSH_USER=username
      - SSH_PRIVATE_KEY=/ssh-keys/id_rsa
    ports:
      - "12542:12542"
      - "12543:12543"
```

## Configuration Reference
Below are the available environment variables. Variables without a default value are mandatory."

### Environment Variables

| Variable | Description | Default |
| :--- | :--- | :--- |
| **SSH_HOST** | Hostname or IP of the SSH jump server. | |
| **SSH_PORT** | Port of the SSH jump server. | `22` |
| **SSH_USER** | SSH login username. | |
| **SSH_PRIVATE_KEY** | Path to the private key **inside** the container. | |
| **MULTI_TUNNEL_ENABLED** | Set to `true` to enable multi-tunnel mode. | `false` |
| **MULTI_TUNNEL_CONF_PATH** | Path to the YAML configuration file **inside** the container. | |
| **TUNNEL_HOST** | (Single mode) Remote target destination host. | |
| **TUNNEL_PORT** | (Single mode) Remote target destination port. | |
| **TUNNEL_LOCAL_PORT** | (Single mode) Local port to bind. If omitted, TUNNEL_PORT is used. | |
| **LOG_LEVEL_APP** | Application log level. | INFO |
| **LOG_LEVEL_LIB** | External libraries log level | ERROR |


### Multi-Tunnel YAML Schema
When MULTI_TUNNEL_ENABLED is true, use this structure for your YAML file:

```yaml
multi-tunnel:
  tunnels:
    - host: service-a.internal.svc # Remote destination host
      port: 8080 # Remote destination port
      local-port: 12542
    - host: service-b.internal.svc
      port: 3306
```

### Persistence and Volumes

- **SSH Keys**: Mount the directory containing your private keys and reference the full path in `SSH_PRIVATE_KEY`.
- **Configuration**: In multi-tunnel mode, mount the directory containing your YAML file and reference the full path in `MULTI_TUNNEL_CONF_PATH`.

## Development

For local development and testing purposes, you can configure the application by placing your credentials and configuration files directly in the project structure:

1.  **SSH Key**: Place your `private.key` file in the project root.
2.  **Configuration**: Create an `application-dev.yml` (or `.yaml`) file in `src/main/resources`. You can use the template provided in `src/main/examples` as a starting point.

> [!CAUTION]
> **Security Warning**: These files are strictly for local use and are automatically ignored by Git (via `.gitignore`). **Never** attempt to force-commit these files or hardcode sensitive credentials into the repository.

### Example of `private.key`
The private key file must be in OpenSSH format. Its content should look like this:

```text
-----BEGIN OPENSSH PRIVATE KEY-----
b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW
QyNTUxOQAAACD6S/D274S6v6F4g/7Zz4X9B2G5R/k8F1K9L2O8M/4X9AAAALi4uLi4uLi
... (rest of the key) ...
-----END OPENSSH PRIVATE KEY-----
```
