package net.schimweg.financeProcessor.plugins.defaultTransformations;

import net.schimweg.financeProcessor.ast.TransactionSetNode;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.Transaction;
import net.schimweg.financeProcessor.model.TransactionSet;

public class FilterNode implements TransactionSetNode {
    public static class FilteredTransactionSet implements TransactionSet {

        private final TransactionSet source;
        private Transaction current;

        public FilteredTransactionSet(TransactionSet source) {
            this.source = source;
        }

        private boolean matchesFilters(Transaction transaction) {
            //return (transaction.getDirection() == TransactionDirection.INCOMING);
            return true;
        }

        @Override
        public Transaction current() {
            return current;
        }

        @Override
        public boolean next() {
            while (source.next()) {
                if (matchesFilters(source.current())) {
                    current = source.current();
                    return true;
                }
            }
            return false;
        }
    }

    public static class FilterNodeConfig {
        public TransactionSetNode source;
    }

    private final TransactionSetNode source;

    public FilterNode(FilterNodeConfig config) {
        this(config.source);
    }

    public FilterNode(TransactionSetNode source) {
        this.source = source;
    }

    @Override
    public TransactionSet execute(DataContext context) {
        return new FilteredTransactionSet(source.execute(context));
    }

    @Override
    public String name() {
        return "FILTER";
    }
}
