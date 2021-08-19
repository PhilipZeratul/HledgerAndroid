package com.phillip.hledgerandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddTransactionActivity extends AppCompatActivity {

    private static final String TAG = "HledgerAndroid.AddTransactionActivity";
    private ArrayList<String> accounts = new ArrayList<String>();
    private EditText editText;
    private EditText editText2;

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
        setupFinishedButton();
    }

    private void setupSpinner() {
        accounts = ((HledgerAndroid)this.getApplication()).getAccounts();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, accounts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int spinnerPosition = adapter.getPosition("expenses:Food");
        int spinnerPosition2 = adapter.getPosition("liabilities:PAB Credit Card");

        Spinner spinner = (Spinner) findViewById(R.id.spinner_account_1);
        spinner.setAdapter(adapter);
        spinner.setSelection(spinnerPosition);
        spinner = (Spinner) findViewById(R.id.spinner_account_2);
        spinner.setAdapter(adapter);
        spinner.setSelection(spinnerPosition2);
    }

    private void setupEditText() {
        editText = (EditText) findViewById(R.id.editTextNumberAccount_1);
        editText2 = (EditText) findViewById(R.id.editTextNumberAccount_2);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    editText2.getText().clear();
                }
                else {
                    Float num = new Float(s.toString());
                    num = -num;
                    editText2.setText(num.toString());
                }
            }
        });
    }

    private void setupFinishedButton() {
        Button button = (Button) findViewById(R.id.button_finished);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearEditText();
            }
        });
    }

    private void clearEditText() {
        editText.getText().clear();
        editText2.getText().clear();
    }

    // TODO: TimePicker

    // TODO: Write to .csv file
}