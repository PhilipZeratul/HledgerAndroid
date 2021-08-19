package com.phillip.hledgerandroid;

import android.app.Application;

import java.util.ArrayList;

public class HledgerAndroid extends Application {

    private ArrayList<String> accounts = new ArrayList<String>();
    private static final String accountsFileName = "accounts.txt";
    private static final String journalFileName = "journal.txt";

    public ArrayList<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<String> accounts) {
        this.accounts = accounts;
    }

    public String getAccountsFileName() {
        return accountsFileName;
    }

    public String getJournalFileName() {
        return journalFileName;
    }
}
