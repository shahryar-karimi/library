package ir.shahryar.library.wallet.transaction;

public class Transaction {
    private long date;
    private double amount;
    private TransActionType transActionType;

    public Transaction(double amount, TransActionType transActionType) {
        this.date = System.currentTimeMillis();
        this.amount = amount;
        this.transActionType = transActionType;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransActionType getTransActionType() {
        return transActionType;
    }

    public void setTransActionType(TransActionType transActionType) {
        this.transActionType = transActionType;
    }
}
