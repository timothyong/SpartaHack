package com.example.abhi.testapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;


public class newuser extends ActionBarActivity {

    EditText username;
    EditText password;
    EditText name;
    Button create;
    private static final String users = "https://groupicuser.firebaseio.com/#-JlT7Z5wK02qu3uB4LPd|415764419e416d4626e2c2fd87e8d361";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);

        username = (EditText) findViewById(R.id.newusername);
        password = (EditText) findViewById(R.id.newpassword);
        name = (EditText) findViewById(R.id.enternewname);

        user person = new user(name.toString(),username.toString(),password.toString());

        //person.setUserName(username.toString());
        //person.setpassword(password.toString());
        //person.setname(name.toString());
        //person.setState(false);

        Firebase ref = new Firebase(users);
        Map<String,user> enter = new HashMap<String,user>();
        enter.put(username.toString(),person);
        ref.setValue(username.toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_newuser, menu);
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
