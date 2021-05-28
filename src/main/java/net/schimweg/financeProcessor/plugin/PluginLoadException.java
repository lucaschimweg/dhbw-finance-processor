package net.schimweg.financeProcessor.plugin;

/**
 * Represents an Exception, occurred during the load process of a Plugin
 */
public class PluginLoadException extends Exception {
    /**
     * Creates a new instance
     * @param message Describes what went wrong
     */
    public PluginLoadException(String message) {
        super(message);
    }

    /**
     * Creates a new instance
     * @param message Describes what went wrong
     * @param cause The Throwable that caused the Exception
     */
    public PluginLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
