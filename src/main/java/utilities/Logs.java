package utilities;

import org.apache.log4j.Logger;

public class Logs {
    private static final Logger log = Logger.getLogger(Logs.class);

    public static void info(String message) {
        log.info(message);
    }

    public static void error(String message, Throwable throwable) {
        log.error(message, throwable);
        throw new AssertionError(throwable);
    }

    public static void error(Exception error) {
        log.error(" --- Exception occurs --- ", error);
        throw new AssertionError(error);
    }

    public static void error(String message) {
        log.error(message);
    }
}
