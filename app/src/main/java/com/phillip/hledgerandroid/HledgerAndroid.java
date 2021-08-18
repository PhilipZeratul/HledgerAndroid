package com.phillip.hledgerandroid;

import android.app.Application;

import java.util.ArrayList;

public class HledgerAndroid extends Application {

    private ArrayList<String> accounts = new ArrayList<String>();

    public ArrayList<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<String> accounts) {
        this.accounts = accounts;
    }
}
