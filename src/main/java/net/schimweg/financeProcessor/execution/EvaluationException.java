package net.schimweg.financeProcessor.execution;

/**
 * An exception representing that something went wrong during the evaluation
 */
public class EvaluationException extends Exception {
    /**
     * Create a new empty EvaluationException
     */
    public EvaluationException() {
    }

    /**
     * Create a new EvaluationException from a message
     * @param message The message giving further information on the exception
     */
    public EvaluationException(String message) {
        super(message);
    }

    /**
     * Create a new EvaluationException with a message and a causing other Throwable
     * @param message The message giving further information on the exception
     * @param cause The Throwable that caused the EvaluationException
     */
    public EvaluationException(String message, Throwable cause) {
        super(message, cause);
    }
}
