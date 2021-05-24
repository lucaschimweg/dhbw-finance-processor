package net.schimweg.financeProcessor.plugins.defaultTransformations.filter;

public interface Operator<T1, T2> {
    boolean isFulfilled(T1 x, T2 y) throws WrongTypeException;
}
