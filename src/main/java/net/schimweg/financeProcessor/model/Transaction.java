package net.schimweg.financeProcessor.model;

public class Transaction implements FinanceObject {
    private final Amount amount;
    private final String subject;
    private final int costCenter;
    private final TransactionDirection direction;

    public Transaction(Amount amount, String subject, int costCenter, TransactionDirection direction) {
        this.amount = amount;
        this.subject = subject;
        this.costCenter = costCenter;
        this.direction = direction;
    }

    public Amount getAmount() {
        return amount;
    }

    public String getSubject() {
        return subject;
    }

    public int getCostCenter() {
        return costCenter;
    }

    public TransactionDirection getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", subject='" + subject + '\'' +
                ", costCenter=" + costCenter +
                ", direction=" + direction +
                '}';
    }
}
