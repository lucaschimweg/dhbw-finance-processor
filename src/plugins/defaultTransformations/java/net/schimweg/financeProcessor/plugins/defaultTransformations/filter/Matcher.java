package net.schimweg.financeProcessor.plugins.defaultTransformations.filter;

import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.Transaction;

import java.util.HashMap;
import java.util.function.Function;

public class Matcher {
    private final static HashMap<String, Function<Transaction, Object>> accessors;
    private final static HashMap<String, Operator<Object, Object>> operators;

    static {
        accessors = new HashMap<>();
        accessors.put("subject", Transaction::getSubject);
        accessors.put("amount_value", t -> t.getAmount().getValue());
        accessors.put("amount_currency", t -> t.getAmount().getCurrency().toString());
        accessors.put("cost_center", Transaction::getCostCenter);
        accessors.put("direction", Transaction::getDirection);

        operators = new HashMap<>();
        operators.put("equals", Object::equals);
        operators.put("smaller", asNumericFunc((x, y) -> x < y));
        operators.put("smaller_equals", asNumericFunc((x, y) -> x <= y));
        operators.put("larger", asNumericFunc((x, y) -> x > y));
        operators.put("larger_equals", asNumericFunc((x, y) -> x >= y));
    }

    private static Operator<Object, Object> asNumericFunc(Operator<Long, Long> function) {
        return (x, y) -> {
            if (x instanceof Long && y instanceof Long) {
                return function.isFulfilled((Long) x, (Long) y);
            }
            throw new WrongTypeException("Operator not applicable to given types");
        };
    }

    private static class CompiledRule {
        private final Function<Transaction, ?> accessor;
        private final Operator<Object, Object> operator;
        private final Object value;

        private CompiledRule(Function<Transaction, ?> accessor, Operator<Object, Object> operator, Object value) {
            this.accessor = accessor;
            this.operator = operator;
            this.value = value;
        }

        public boolean doesApply(Transaction t) throws WrongTypeException {
            return this.operator.isFulfilled(accessor.apply(t), value);
        }
    }

    private final CompiledRule[] rules;

    public Matcher(Condition[] conditions) throws EvaluationException {
        rules = new CompiledRule[conditions.length];
        for (int i = 0; i < conditions.length; i++) {

            Object value = conditions[i].numValue;
            if (value == null) {
                value = conditions[i].stringValue;
            }
            if (value == null) {
                value = conditions[i].directionValue;
            }
            if (value == null) {
                throw new EvaluationException("No value given");
            }

            Function<Transaction, ?> accessor = accessors.get(conditions[i].field);
            if (accessor == null) {
                throw new EvaluationException("Accessor not found");
            }

            Operator<Object, Object> operator = operators.get(conditions[i].type);
            if (operator == null) {
                throw new EvaluationException("Operator not found");
            }

            rules[i] = new CompiledRule(accessor, operator, value);
        }
    }

    public boolean matches(Transaction t) throws WrongTypeException {
        for (CompiledRule rule : rules) {
            if (!rule.doesApply(t)) {
                return false;
            }
        }
        return true;
    }
}
