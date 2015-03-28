package com.example.abhi.testapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;


public class authorization extends ActionBarActivity {

    private EditText username = null;
    private EditText password = null;
    Button newuser;
    Button login;


    //url to the databases
    private static final String events = "https://groupic.firebaseio.com/#-JlT7Z5wK02qu3uB4LPd|415764419e416d4626e2c2fd87e8d361";
    private static final String users = "https://groupicuser.firebaseio.com/#-JlT7Z5wK02qu3uB4LPd|415764419e416d4626e2c2fd87e8d361";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_authorization, menu);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        final String user = username.toString();
        final String pass = password.toString();
        login = (Button) findViewById(R.id.loginbutton);
        newuser = (Button) findViewById(R.id.newuser);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

       //everything in here after login button click

        Firebase userlist = new Firebase(users).child(user);// add code to check if user exists or not
        userlist.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.hasChild(users+"/"+user)){
                    logintheuser(users+"/"+user,pass,user);
                }
                else
                    displayerror(user,1);//1 means error not due to wrong password
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

            }
        });

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),newuser.class);
                startActivity(it);
            }
        });

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

    public void logintheuser(String add, final String password,final String username){
        Firebase gotuser = new Firebase(add);
        gotuser.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String,user> use = (Map<String,user>)dataSnapshot.getValue();
                if(use.get(username).getState()==true)
                       displayerror(username,2);
                use.get(username).setState(true);
                if(password==use.get(username).getPassword()){
                    Intent it = new Intent(getApplicationContext(),MainActivity.class);
                    it.putExtra("user",username);
                    startActivity(it);
                }
                else
                    displayerror(username,3);//3 if passwrod entered is wrong
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
    }

    public void displayerror(String user,int err){
        if(err==3){
            Toast tos = Toast.makeText(getApplicationContext(),"password entered for "+user+" is wrong, try again",Toast.LENGTH_LONG);
        }
        else if(err==1) {//1 if username doesnt exist
            Toast tos = Toast.makeText(getApplicationContext(),"The username does not exist,create a new account", Toast.LENGTH_LONG);
        }
        else if(err==2){
            Toast tos = Toast.makeText(getApplicationContext(),"You are already logged in a different account", Toast.LENGTH_LONG);
        }
    }
}
