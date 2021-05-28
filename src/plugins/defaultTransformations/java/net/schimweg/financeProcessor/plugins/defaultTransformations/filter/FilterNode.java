package net.schimweg.financeProcessor.plugins.defaultTransformations.filter;

import net.schimweg.financeProcessor.ast.TransactionSetNode;
import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.Transaction;
import net.schimweg.financeProcessor.model.TransactionSet;

public class FilterNode implements TransactionSetNode {
    public static class FilteredTransactionSet implements TransactionSet {

        private final TransactionSet source;
        private final Matcher matcher;
        private Transaction current;

        public FilteredTransactionSet(TransactionSet source, Matcher matcher) {
            this.source = source;
            this.matcher = matcher;
        }

        @Override
        public Transaction current() {
            return current;
        }

        @Override
        public boolean next() throws EvaluationException {
            while (source.next()) {
                if (matcher.matches(source.current())) {
                    current = source.current();
                    return true;
                }
            }
            return false;
        }
    }

    private final TransactionSetNode source;
    private Matcher matcher;
    private EvaluationException matcherException;

    public FilterNode(FilterNodeConfig config) {
        this.source = config.source;
        try {
            // Precompile matcher
            this.matcher = new Matcher(config.conditions);
        } catch (EvaluationException e) {
            //e.printStackTrace();
            this.matcherException = e;
        }
    }

    @Override
    public TransactionSet execute(DataContext context) throws EvaluationException {
        if (matcher == null) {
            throw matcherException;
        }
        return new FilteredTransactionSet(source.execute(context), matcher);
    }
}
