package com.example.clarachen.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.preference.PreferenceManager;
public class PreferenceStorageView extends AppCompatActivity {

    public final static String STORE_PREFERENCES = "storePrefFinal.txt";

    //counter for the number of entry to the Store Preference
    public int counter = 0;
    public SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/yyyy-hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_storage_view);

        /**Show the Up Button in the action bar.
        setupActionBar();
        */

        //Setup SharedPreference instance
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //set the counter starting from 0
        counter = sharedPreferences.getInt("Counter", 0);

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        counter = sharedPreferences.getInt("Counter", 0);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.

    //@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
     */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu. This adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.preference_storage_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                /** This ID represents the Home or Up button. In the case
                 * of this activity, the up button is shown. Use NavUtils
                 * to allow users to navigate one level in the application
                 * structure . For more details, see the Navigation pattern
                 * on Android Design:
                 *
                 *http://developer.android.com/design/patterns/navigation.html#up-vs-back
                 */
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onPause(){
        super.onPause();
    }


    public void savePreference(View view){

        //save the preferences (if not null) in a file
        EditText bookTXT = (EditText) findViewById(R.id.editText_book);
        String bookName = bookTXT.getText().toString();
        EditText authorTXT = (EditText) findViewById(R.id.editText_author);
        String authorName=authorTXT.getText().toString();
        EditText desTXT=(EditText) findViewById(R.id.editText_description);
        String  description = desTXT.getText().toString();

        if(bookName!=null && authorName != null && description!=null){
            try
            {
                counter +=1;
                SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
                Editor editor=sharedPreferences.edit();
                editor.putInt("Counter", counter);
                editor.commit();

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(STORE_PREFERENCES,MODE_APPEND));
                String message ="\nSaved Preference "+ counter +", "+sdf.format(new Date());
                outputStreamWriter.write(message);
                outputStreamWriter.close();
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(this,Storage.class);
        startActivity(intent);
    }

    public void cancelPreference(View view){
        Intent intent = new Intent(this, Storage.class);
        startActivity(intent);
    }

}
