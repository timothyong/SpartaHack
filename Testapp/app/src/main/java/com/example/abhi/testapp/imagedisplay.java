package com.example.abhi.testapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.firebase.client.Firebase;


public class imagedisplay extends ActionBarActivity {

    private String eventid = "";
    private String username = "";
    private Firebase baseref;
    private static final String events = "https://groupic.firebaseio.com/#-JlT7Z5wK02qu3uB4LPd|415764419e416d4626e2c2fd87e8d361";
    private static final String users = "https://groupicuser.firebaseio.com/#-JlT7Z5wK02qu3uB4LPd|415764419e416d4626e2c2fd87e8d361";
    private picadapter picadap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagedisplay);
        getSupportActionBar().hide();
        Intent in  = getIntent();
        eventid = in.getStringExtra("idval");
        username = in.getStringExtra("username");

        //get firebase location for this particular event
        baseref = new Firebase(events).child(eventid);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_imagedisplay, menu);
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
    public void onStart(){
        super.onStart();
        ListView picview = (ListView) findViewById(R.id.piclistview);

        picadap = new picadapter(baseref,this,R.layout.activity_imagedisplay,username);
        picview.setAdapter(picadap);


    }
}
