package com.phillip.hledgerandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

        setupSpinner();
        setupEditText();
    }

    private void setupSpinner() {
        accounts = ((HledgerAndroid)this.getApplication()).getAccounts();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, accounts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_account_1);
        spinner.setAdapter(adapter);
        spinner = (Spinner) findViewById(R.id.spinner_account_2);
        spinner.setAdapter(adapter);
    }

    private void setupEditText() {
        EditText editText = (EditText) findViewById(R.id.editTextNumberAccount_1);
        EditText editText2 = (EditText) findViewById(R.id.editTextNumberAccount_2);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                Float num = new Float(s.toString());
                num = -num;
                editText2.setText(num.toString());
            }
        });
    }
}