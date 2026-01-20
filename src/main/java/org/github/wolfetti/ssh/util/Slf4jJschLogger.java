package org.github.wolfetti.ssh.util;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Logger;

/**
 * @author Fabio Frijo
 */
public class Slf4jJschLogger implements Logger {
    private final org.slf4j.Logger slf4jLogger = org.slf4j.LoggerFactory.getLogger(JSch.class);

    @Override
    public boolean isEnabled(int level) {
        return switch (level) {
            case Logger.DEBUG -> slf4jLogger.isDebugEnabled();
            case Logger.INFO -> slf4jLogger.isInfoEnabled();
            case Logger.WARN -> slf4jLogger.isWarnEnabled();
            case Logger.ERROR, Logger.FATAL -> slf4jLogger.isErrorEnabled();
            default -> true;
        };
    }

    @Override
    public void log(int level, String message) {
        switch (level) {
            case Logger.DEBUG -> slf4jLogger.debug(message);
            case Logger.INFO -> slf4jLogger.info(message);
            case Logger.WARN -> slf4jLogger.warn(message);
            case Logger.ERROR, Logger.FATAL -> slf4jLogger.error(message);
            default -> slf4jLogger.info(message);
        }
    }
}
