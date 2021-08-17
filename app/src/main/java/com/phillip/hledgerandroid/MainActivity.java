package com.phillip.hledgerandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private static final String TAG = "HledgerAndroid";
    private ArrayList<String> accounts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // TODO: Add Transaction activity.
    public void sendMessage(View view) {
        Intent intent = new Intent(this, AddTransactionActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.settings);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_load_accounts:
                actionLoadAccounts();
                return true;
            case R.id.action_save_csv:

                return true;
            default:
                return false;
        }
    }

    private static final int LOAD_ACCOUNTS = 2;

    private void actionLoadAccounts() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, LOAD_ACCOUNTS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case LOAD_ACCOUNTS:
                loadAccounts(resultData);
                break;
            default:
                break;
        }
    }

    private void loadAccounts(Intent resultData)
    {
        Uri uri = null;
        if (resultData != null) {
            uri = resultData.getData();
            try {
                InputStream in = getContentResolver().openInputStream(uri);
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                for (String line; (line = r.readLine()) != null; ) {
                    if (line.startsWith(";"))
                        continue;
                    else if (line.startsWith("account ")) {
                        String[] splitted = line.split("account |;|  ");
                        //Log.v(TAG, "+" + splitted[1] + "+");
                        accounts.add(splitted[1]);
                    }
                }
            }catch (Exception e) {}
        }
    }
}