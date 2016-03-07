package com.example.clarachen.storage;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences.Editor;

import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLiteView extends AppCompatActivity {

    public int counter =0;
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM/DD/yyyy-hh:mm a");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_view);

       /* //Set up Action Bar
        setupActionBar();*/

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        counter = sharedPreferences.getInt("SQL_COUNTER", 0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sqlite_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        counter = sharedPreferences.getInt("SQL_COUNTER", 0);
    }

/*

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupActionBar(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
*/


    public void saveSqlite(View view){

        EditText editText = (EditText) findViewById(R.id.editText_blogmessage);
        String message = editText.getText().toString();
        SqliteDBController sqliteDBController = new SqliteDBController(getBaseContext());
        sqliteDBController.open();
        long retValue = sqliteDBController.insert(message);
        sqliteDBController.close();
        if(retValue!=1){
            Context context = getApplicationContext();
            CharSequence txt = "Message saved Successfully!";
            int duration = Toast.LENGTH_LONG;
            Toast.makeText(context, txt, duration).show();

            try{
                counter +=1;

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                Editor editor = sharedPreferences.edit();
                editor.putInt("SQL_COUNTER", counter);
                editor.commit();

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(PreferenceStorageView.STORE_PREFERENCES,MODE_APPEND));
                outputStreamWriter.write("\nSQLite "+counter+", "+simpleDateFormat.format(new Date()));
                outputStreamWriter.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

        Intent intent = new Intent(this, Storage.class);
        startActivity(intent);
    }

    public void cancelSqlite(View view){
        Intent intent = new Intent(this, Storage.class);
        startActivity(intent);
    }

}
