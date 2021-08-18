package com.phillip.hledgerandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private static final int INTENT_LOAD_ACCOUNTS = 2;
    private static final String TAG = "HledgerAndroid";
    private static final String accountsFileName = "accounts.txt";
    private ArrayList<String> accounts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadDefaultAccounts();
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, AddTransactionActivity.class);
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

    private void actionLoadAccounts() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, INTENT_LOAD_ACCOUNTS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case INTENT_LOAD_ACCOUNTS:
                loadNewAccounts(resultData);
                break;
            default:
                break;
        }
    }

    private void loadDefaultAccounts() {
        try {
            FileInputStream in = openFileInput(accountsFileName);
            parseAccounts(in);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadNewAccounts(Intent resultData)
    {
        Uri uri = null;
        if (resultData != null) {
            uri = resultData.getData();
            try {
                InputStream in = getContentResolver().openInputStream(uri);
                parseAccounts(in);
                saveAccounts(in);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parseAccounts(InputStream in) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        for (String line; (line = r.readLine()) != null; ) {
            if (line.startsWith(";"))
                continue;
            else if (line.startsWith("account ")) {
                String[] splitted = line.split("account |;|  ");
                //Log.v(TAG, "+" + splitted[1] + "+");
                accounts.add(splitted[1]);
            }
        }
        // Save accounts to global variable.
        ((HledgerAndroid)this.getApplication()).setAccounts(accounts);
    }

    // TODO: Figure this out!
    private void saveAccounts(InputStream in) {
        FileOutputStream out = null;
        try {
            out = openFileOutput(accountsFileName, Context.MODE_PRIVATE);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}