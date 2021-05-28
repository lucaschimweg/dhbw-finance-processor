package net.schimweg.financeProcessor.model;

import java.util.HashSet;

/**
 * A fully-in-memory TransactionSet based on a HashSet
 * @see TransactionSet
 * @see MaterializedFinanceObject
 */
public class MaterializedTransactionSet extends HashSet<Transaction> implements MaterializedFinanceObject {
}
