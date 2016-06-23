package com.example.admin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.admin.myapplication.adapter.ListEng;
import com.example.admin.myapplication.database.DatabaseHalper;
import com.example.admin.myapplication.mobel.vocabulary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class SearchEng extends Activity {
    private Switch E;
    private ImageButton put;
    private EditText Search;
    private ListView lvocabulary;
    private ListEng adapter;
    private List<vocabulary> mVocabularyList;
    private DatabaseHalper mDBHelper;
    //    private SQLiteDatabase mDBHelper;
    public String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchshoweng);
        Intent intent = this.getIntent();
        String d = intent.getStringExtra("Data");

        put = (ImageButton) findViewById(R.id.search_button);
        // Perform action on click

        put.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Search = (EditText) findViewById(R.id.serchedit);
                data = String.valueOf(Search);
                Intent intent = new Intent(getApplicationContext(), SearchEng.class);
                intent.putExtra("Data", Search.getText().toString());
                startActivity(intent);
                finish();

            }
        });


        E = (Switch) findViewById(R.id.switch1);
        E.setChecked(true);
        E.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (E.isChecked() == false) {
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent1);
                } else if (E.isChecked() == true) {
                }
            }
        });

        lvocabulary = (ListView) findViewById(R.id.listView_vocabsearch);
        mDBHelper = new DatabaseHalper(this);
        mVocabularyList = mDBHelper.SearchEditTextEng(d);
        adapter = new ListEng(this, mVocabularyList);
        lvocabulary.setAdapter(adapter);
        File database = getApplicationContext().getDatabasePath(DatabaseHalper.DBNAME);
        if (false == database.exists()) {
            mDBHelper.getReadableDatabase();
            if (coppyDatabase(this)) {
                Toast.makeText(this, "Coppy Database Succes", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Coppy Data Errer", Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }

    public boolean coppyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(DatabaseHalper.DBNAME);
            String outFileName = DatabaseHalper.DBLOCATION + DatabaseHalper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.v("Search", "DB copied");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}