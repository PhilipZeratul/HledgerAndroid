package com.phillip.hledgerandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;

public class FileSelectionActivity extends AppCompatActivity {

    private File privateRootDir;
    File[] files;
    String[] fileNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_selection);

        // Setup toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent resultIntent = new Intent("com.phillip.hledgerandroid.ACTION_RETURN_FILE");
        privateRootDir = getFilesDir();
        files = privateRootDir.listFiles();
        setResult(Activity.RESULT_CANCELED, null);
        /*
         * Display the file names in the ListView fileListView.
         * Back the ListView with the array imageFilenames, which
         * you can create by iterating through imageFiles and
         * calling File.getAbsolutePath() for each File
         */

    }
}