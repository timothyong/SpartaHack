package com.example.abhi.testapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private static final String events = "https://groupic.firebaseio.com/#-JlT7Z5wK02qu3uB4LPd|415764419e416d4626e2c2fd87e8d361";
    private static final String users = "https://groupicuser.firebaseio.com/#-JlT7Z5wK02qu3uB4LPd|415764419e416d4626e2c2fd87e8d361";
    private String username="";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //set up the firebase
        Firebase event = new Firebase(events);
        Firebase user = new Firebase(users);



        return true;
    }

    public void onStart(){
        super.onStart();
        ListView eventslistview = (ListView) findViewById(R.id.eventslist);

        Intent it = getIntent();
        username = it.getStringExtra("user");

        //the username would be pulled from the user object in the intent passed from the authorization page
        Firebase usernamelist = new Firebase(users).child(username);// get into the url for the user and then access the event ids

        final ArrayList<String> names = new ArrayList<String>();

        final Map<String,String> idname;
        usernamelist.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String,userobj> eve= (Map<String,userobj>)dataSnapshot.getValue();//userobj = userobject
                userobj use = eve.get("username");
                idname = use.eventidnamemap; //a map with the value as id and keys as names
                for(String str : idname.keySet())
                    names.add(str);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,names);
        eventslistview.setAdapter(adapter);

        eventslistview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String itemname = (String) parent.getItemAtPosition(position);//get the event id
                Intent it = new Intent(getApplicationContext(),nextscreen.class);
                it.putExtra("idval",idname.get(itemname));
                it.putExtra("username",username);
                startActivity(it);
            }
        });
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
