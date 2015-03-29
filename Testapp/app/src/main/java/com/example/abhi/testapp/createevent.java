package com.example.abhi.testapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


class createevent extends ActionBarActivity {

    EditText eventname;
    EditText name;
    TextView names;
    Button addpeople;
    Button createevent;
    CharSequence usernames;
    ArrayList<String> namelist;
    boolean end = false;

    private static final String events = "https://groupic.firebaseio.com/#-JlT7Z5wK02qu3uB4LPd|415764419e416d4626e2c2fd87e8d361";
    private static final String users = "https://groupicuser.firebaseio.com/#-JlT7Z5wK02qu3uB4LPd|415764419e416d4626e2c2fd87e8d361";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);

        eventname = (EditText) findViewById(R.id.enternewname);
        name = (EditText) findViewById(R.id.usernametoadd);

        int eventid = (eventname.toString().charAt(0));
        eventid *= (eventid*Math.random()*100)%26;
        final String id = eventname.toString()+eventid;

        Firebase ref = new Firebase(events);
        ref.child(id).setValue(eventname.toString());
        names = (TextView) findViewById(R.id.currentuserdisplay);

        addpeople = (Button) findViewById(R.id.addperson);
        addpeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernames = usernames+", "+name.toString();
                namelist.add(name.toString());
                name.setText("");
                addtext();
            }
        });

        createevent = (Button) findViewById(R.id.createnewevent);
        createevent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Firebase userref = new Firebase(users);
                for(int i=0;i<namelist.size();i++){
                    final String nameli = namelist.get(i);
                    userref.child(namelist.get(i)).child("events").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String,String> mp = (Map<String, String>) dataSnapshot.getValue();
                            mp.put(id,eventname.toString());
                            userref.child(nameli).child("events").setValue(mp);
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                }
                Intent iter = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(iter);
            }



        });


    }

    public void addtext(){

        if (usernames.length() != 0) {
            names.setText(usernames);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_createevent, menu);
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
}
