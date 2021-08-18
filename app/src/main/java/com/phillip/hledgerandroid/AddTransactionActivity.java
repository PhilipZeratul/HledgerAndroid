package com.phillip.hledgerandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddTransactionActivity extends AppCompatActivity {

    private static final String TAG = "HledgerAndroid.AddTransactionActivity";
    private ArrayList<String> accounts = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        populateSpinner();
    }

    private void populateSpinner()
    {
        accounts = ((HledgerAndroid)this.getApplication()).getAccounts();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, accounts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_account_1);
        spinner.setAdapter(adapter);
        spinner = (Spinner) findViewById(R.id.spinner_account_2);
        spinner.setAdapter(adapter);
    }
}