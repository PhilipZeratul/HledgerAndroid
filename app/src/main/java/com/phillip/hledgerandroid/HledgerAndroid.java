package com.phillip.hledgerandroid;

import android.app.Application;

public class HledgerAndroid extends Application {
    private String someVariable;

    public String getSomeVariable() {
        return someVariable;
    }

    public void setSomeVariable(String someVariable) {
        this.someVariable = someVariable;
    }
}
