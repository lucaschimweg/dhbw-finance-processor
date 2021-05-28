package net.schimweg.financeProcessor.model;

/**
 * Represents the direction in which a Transaction is done
 */
public enum TransactionDirection {
    /**
     * Represents that money is transferred from outside to the user
     */
    INCOMING,
    /**
     * Represents that money is transferred from the user to an external recipient
     */
    OUTGOING
}
