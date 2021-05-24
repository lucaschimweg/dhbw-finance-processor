package net.schimweg.financeProcessor.plugins.defaultTransformations.filter;

import net.schimweg.financeProcessor.execution.EvaluationException;

public class WrongTypeException extends EvaluationException {
    public WrongTypeException(String message) {
        super(message);
    }
}
