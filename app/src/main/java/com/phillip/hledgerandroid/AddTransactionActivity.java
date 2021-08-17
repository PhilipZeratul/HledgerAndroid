package com.phillip.hledgerandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddTransactionActivity extends AppCompatActivity {

    private static final String TAG = "HledgerAndroid.AddTransactionActivity";

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
//        Spinner spinner = (Spinner) findViewById(R.id.spinner_account_1);
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.planets_array, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
        ((HledgerAndroid)this.getApplication()).setSomeVariable("foo");
        String s = ((HledgerAndroid) this.getApplication()).getSomeVariable();

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra(String.valueOf(R.string.intent_bundle));
        Log.v(TAG, String.valueOf(R.string.intent_bundle));
        ArrayList<Object> object = (ArrayList<Object>) args.getSerializable("ARRAYLIST");
    }
}