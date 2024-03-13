package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuditLog {
    private static final Logger logger = LogManager.getLogger(AuditLog.class);

    public static void log(String user, String action, String requestParameters) {
        String logMessage = String.format("User: %s | Action: %s | Parameters: %s", user, action, requestParameters);
        logger.info(logMessage);
    }
}
