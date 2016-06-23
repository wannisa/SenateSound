package com.example.admin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.admin.myapplication.adapter.ListVocabularyAdapter;
import com.example.admin.myapplication.adapter.ShowEng;
import com.example.admin.myapplication.adapter.ShowVocabularyAdapter;
import com.example.admin.myapplication.database.DatabaseHalper;
import com.example.admin.myapplication.mobel.vocabulary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 8/6/2559.
 */
public class detailactivity extends Activity {
    private ListView lvocabulary;
    private ListView lvocabulary1;
    private ShowVocabularyAdapter adapter;
    private ShowEng adapter1;
    private List<vocabulary> mVocabularyList;
    private DatabaseHalper mDBHelper;
    private ImageView soundth;
    private ImageView sounden;
    private String en;
    private String th;
    private String l;
    MediaPlayer mMedia;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        en = "en";
        th = "th";

        lvocabulary = (ListView)findViewById(R.id.showThai);
        lvocabulary1 = (ListView)findViewById(R.id.showEng);
        mDBHelper = new DatabaseHalper(this);
        Intent intent = this.getIntent();
        final String ID = intent.getStringExtra("ID");
        l = en+ID;
        final int i = Integer.parseInt(ID);
        final Button btn1 = (Button) findViewById(R.id.back);
        // Perform action on click
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

         ImageView soundth = (ImageView) findViewById(R.id.imageButton);
        // Perform action on click
        soundth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mMedia = MediaPlayer.create(detailactivity.this, R.raw.th10);
                mMedia.start();
            }
        });
        ImageView sounden = (ImageView) findViewById(R.id.imageButton2);
        // Perform action on click
        sounden.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mMedia = MediaPlayer.create(detailactivity.this, R.raw.en10);
                mMedia.start();
            }
        });
//        mMedia.stop();
        mVocabularyList = mDBHelper.showdetail(i);
        adapter = new ShowVocabularyAdapter(this,mVocabularyList);
        adapter1 = new ShowEng(this,mVocabularyList);
        lvocabulary.setAdapter(adapter);
        lvocabulary1.setAdapter(adapter1);
        File database = getApplicationContext().getDatabasePath(DatabaseHalper.DBNAME);
        if(false == database.exists()){
            mDBHelper.getReadableDatabase();
            if(coppyDatabase(this)){
                Toast.makeText(this, "Coppy Database Succes",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Coppy Data Errer", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    public boolean coppyDatabase(Context context){
        try{
            InputStream inputStream = context.getAssets().open(DatabaseHalper.DBNAME);
            String outFileName = DatabaseHalper.DBLOCATION + DatabaseHalper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while((length = inputStream.read(buff))>0){
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.v("detailactivity","DB copied");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
