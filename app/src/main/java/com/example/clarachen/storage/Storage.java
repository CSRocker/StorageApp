package com.example.clarachen.storage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Storage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_storage, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    @Override
    public void onResume(){
        super.onResume();

        try
        {
            InputStream inputStream = openFileInput(PreferenceStorageView.STORE_PREFERENCES);
            if(inputStream!=null)
            {
                InputStreamReader temp = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(temp);
                String str;
                StringBuilder buf = new StringBuilder();
                while((str= bufferedReader.readLine())!=null){
                    buf.append(str +"\n");
                }
                inputStream.close();
                TextView savedPref = (TextView) findViewById(R.id.data_storage_log);
                savedPref.setText(buf.toString());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public void preference(View view){
        Intent intent = new Intent(this,PreferenceStorageView.class);
        startActivity(intent);
    }

    public void sqliteDatabase(View view){
        Intent intent = new Intent(this, SQLiteView.class);
        startActivity(intent);
    }

    public void close(View view){

        Storage.this.finish();
        System.exit(0);
    }

}
