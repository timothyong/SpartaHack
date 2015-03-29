package com.example.abhi.testapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Iterator;
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
    private Map<String,String> idmap;
    Button addeveent;

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


        Intent it = getIntent();
        username = it.getStringExtra("user");
        idmap = (Map<String, String>) it.getSerializableExtra("map");



        //the username would be pulled from the user object in the intent passed from the authorization page
        Firebase usernamelist = new Firebase(users).child(username);// get into the url for the user and then access the event ids

        createadapter(idmap);

        //final Map<String,String> idname;
        /*usernamelist.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String,user> eve= (Map<String,user>)dataSnapshot.getValue();//userobj = userobject
                //userobj use = eve.get("username");
                Map<String,String> idname = eve.get(username).geteventidmap(); //a map with the value as id and keys as names
                for(String str : idname.keySet())
                    names.add(str);
                createadapter(idname,names);
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
        */
        /*ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,names);
        eventslistview.setAdapter(adapter);

        eventslistview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String itemname = (String) parent.getItemAtPosition(position);//get the event id
                Intent it = new Intent(getApplicationContext(),imagedisplay.class);
                it.putExtra("idval",idname.get(itemname));
                it.putExtra("username",username);
                startActivity(it);
            }
        });
        */
    }

    public void createadapter(Map<String,String> ids){
        /*
        old content
        ListView eventslistview = (ListView) findViewById(R.id.eventslist);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,names);
        eventslistview.setAdapter(adapter);

        eventslistview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String itemname = (String) parent.getItemAtPosition(position);//get the event id
                Intent it = new Intent(getApplicationContext(),imagedisplay.class);
                it.putExtra("idval", mp.get(itemname));
                it.putExtra("username",username);
                startActivity(it);
            }
        });
        */

        final ArrayList<String> names = new ArrayList<String>();
        final ArrayList<String> idlist = new ArrayList<String>();

        Iterator iter = idmap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry pair = (Map.Entry)iter.next();
            names.add(pair.getValue().toString());
            idlist.add(pair.getKey().toString());
        }

        addeveent = (Button) findViewById(R.id.addeventbutton);

        ListView eventslist = (ListView)findViewById(R.id.eventslist);
        ArrayAdapter adap = new ArrayAdapter(this,android.R.layout.simple_list_item_1,names);
        eventslist.setAdapter(adap);

        addeveent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(),createevent.class);
                startActivity(inte);
            }
        });

        eventslist.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int a = idlist.indexOf(parent.getItemAtPosition(position));
                String eventid = idlist.get(a);
                Intent intent = new Intent(getApplicationContext(),imagedisplay.class);
                intent.putExtra("eventid",eventid);
                startActivity(intent);
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
