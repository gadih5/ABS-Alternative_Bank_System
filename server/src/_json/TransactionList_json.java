package _json;

import bank.Transaction;

import java.util.ArrayList;

public class TransactionList_json {
    public ArrayList<Transaction> transactions;

    public TransactionList_json(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
}
