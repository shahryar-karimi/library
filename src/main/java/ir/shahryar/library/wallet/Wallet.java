package ir.shahryar.library.wallet;

import ir.shahryar.library.wallet.transaction.TransActionType;
import ir.shahryar.library.wallet.transaction.Transaction;

import java.util.ArrayList;

public class Wallet {
    private double holding;
    private final ArrayList<Transaction> transactionsHistory;

    public Wallet(double holding) {
        this.holding = holding;
        this.transactionsHistory = new ArrayList<>();
    }

    public double getHolding() {
        return holding;
    }

    public void setHolding(double holding) {
        this.holding = holding;
    }

    public ArrayList<Transaction> getTransactionsHistory() {
        return new ArrayList<>(transactionsHistory);
    }

    public void addTransAction(Transaction transaction) {
        if (transaction.getTransActionType() == TransActionType.DEPOSIT) {
            holding = holding + transaction.getAmount();
        } else {
            holding = holding - transaction.getAmount();
        }
        transactionsHistory.add(transaction);
    }
}
