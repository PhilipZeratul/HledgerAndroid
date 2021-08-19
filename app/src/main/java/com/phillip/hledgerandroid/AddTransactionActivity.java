package com.phillip.hledgerandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {

    private static final String journalFileName = "journal.txt";
    private static final String TAG = "HledgerAndroid.AddTransactionActivity";
    private ArrayList<String> accounts = new ArrayList<String>();
    private EditText editTextDescription;
    private EditText editTextNumber;
    private EditText editTextNumber2;
    private Spinner spinner;
    private Spinner spinner2;
    private Button buttonDate;

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

        setupDescription();
        setupSpinner();
        setupEditText();
        setupDatePicker();
        setupFinishedButton();
    }

    private void setupDescription() {
        editTextDescription = (EditText) findViewById(R.id.editText_description);
    }

    private void setupSpinner() {
        accounts = ((HledgerAndroid)this.getApplication()).getAccounts();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, accounts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int spinnerPosition = adapter.getPosition("expenses:Food");
        int spinnerPosition2 = adapter.getPosition("liabilities:PAB Credit Card");

        spinner = (Spinner) findViewById(R.id.spinner_account_1);
        spinner.setAdapter(adapter);
        spinner.setSelection(spinnerPosition);
        spinner2 = (Spinner) findViewById(R.id.spinner_account_2);
        spinner2.setAdapter(adapter);
        spinner2.setSelection(spinnerPosition2);
    }

    private void setupEditText() {
        editTextNumber = (EditText) findViewById(R.id.editTextNumber_Account_1);
        editTextNumber2 = (EditText) findViewById(R.id.editTextNumber_Account_2);
        editTextNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    editTextNumber2.getText().clear();
                }
                else {
                    Float num = new Float(s.toString());
                    num = -num;
                    editTextNumber2.setText(num.toString());
                }
            }
        });
    }


    private void setupDatePicker() {
        buttonDate = (Button) findViewById(R.id.button_date);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();
                datePicker.show(getSupportFragmentManager(), datePicker.toString());
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        TimeZone timeZoneUTC = TimeZone.getDefault();
                        // It will be negative, so that's the -1
                        int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
                        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
                        Date date = new Date((Long) selection + offsetFromUTC);
                        buttonDate.setText(simpleFormat.format(date));
                    }
                });
            }
        });
        buttonDate.setText(new SimpleDateFormat("yyyy/MM/dd", Locale.US).format(new Date()));
    }

    private void setupFinishedButton() {
        Button button = (Button) findViewById(R.id.button_finished);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToFile();
                clearEditText();
            }
        });
    }

    private void clearEditText() {
        editTextDescription.getText().clear();
        editTextNumber.getText().clear();
        editTextNumber2.getText().clear();
    }

    // TODO: Write to .csv file
    private void writeToFile() {
        FileOutputStream out = null;
        try {
            out = openFileOutput(journalFileName, Context.MODE_PRIVATE | Context.MODE_APPEND);

            StringBuilder builder = new StringBuilder();
            builder.append(buttonDate.getText());
            builder.append(" ");
            builder.append(editTextDescription.getText());
            builder.append("\n");
            builder.append("    ");
            builder.append((String) spinner.getSelectedItem());
            builder.append("  ");
            builder.append(editTextNumber.getText());
            builder.append("\n");
            builder.append("    ");
            builder.append((String) spinner2.getSelectedItem());
            builder.append("  ");
            builder.append(editTextNumber2.getText());
            builder.append("\n");
            builder.append("\n");

            Log.v(TAG, builder.toString());
            out.write(builder.toString().getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}