package org.github.wolfetti.jssht;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Logger;

/**
 *
 * @author Fabio Frijo
 */
public class Slf4jJschLogger implements Logger {
    private final org.slf4j.Logger slf4jLogger = org.slf4j.LoggerFactory.getLogger(JSch.class);

    @Override
    public boolean isEnabled(int level) {
        switch (level) {
            case Logger.DEBUG: 
                return slf4jLogger.isDebugEnabled();
            case Logger.INFO: 
                return slf4jLogger.isInfoEnabled();
            case Logger.WARN: 
                return slf4jLogger.isWarnEnabled();
            case Logger.ERROR:
            case Logger.FATAL: 
                return slf4jLogger.isErrorEnabled();
            default:
                return true;
        }
    }

    @Override
    public void log(int level, String message) {
        switch (level) {
            case Logger.DEBUG: 
                slf4jLogger.debug(message);
                break;
            case Logger.INFO: 
                slf4jLogger.info(message);
                break;
            case Logger.WARN: 
                slf4jLogger.warn(message);
                break;
            case Logger.ERROR:
            case Logger.FATAL: 
                slf4jLogger.error(message);
                break;
            default:
                slf4jLogger.info(message);
                break;
        }
    }
}
