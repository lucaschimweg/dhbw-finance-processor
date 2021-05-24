package net.schimweg.financeProcessor.plugins.defaultTransformations;

import net.schimweg.financeProcessor.ast.TransactionSetNode;
import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.Transaction;
import net.schimweg.financeProcessor.model.TransactionSet;

public class UnionNode implements TransactionSetNode {
    public static class UnionTransactionSet implements TransactionSet {

        private final TransactionSetNode[] sourceNodes;
        private final DataContext context;
        private TransactionSet currentSet = null;
        private int index = -1;

        public UnionTransactionSet(TransactionSetNode[] sourceNodes, DataContext context) {
            this.sourceNodes = sourceNodes;
            this.context = context;
        }

        @Override
        public Transaction current() throws EvaluationException {
            return currentSet.current();
        }

        @Override
        public boolean next() throws EvaluationException {
            if (currentSet != null && currentSet.next()) {
                return true;
            }

            ++index;

            while (index < sourceNodes.length) {
                currentSet = sourceNodes[index].execute(context);
                if (currentSet.next()) {
                    return true;
                }

                ++index;
            }

            return false;
        }
    }

    public static class UnionNodeConfig {
        public TransactionSetNode[] sources;
    }

    private final TransactionSetNode[] sources;

    public UnionNode(UnionNodeConfig config) {
        this(config.sources);
    }

    public UnionNode(TransactionSetNode[] sources) {
        this.sources = sources;
    }

    @Override
    public TransactionSet execute(DataContext context) {
        return new UnionTransactionSet(sources, context);
    }

    @Override
    public String name() {
        return null;
    }
}
