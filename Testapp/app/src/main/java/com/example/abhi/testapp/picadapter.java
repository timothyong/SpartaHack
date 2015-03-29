package com.example.abhi.testapp;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

/**
 * Created by Abhi on 3/28/2015.
 */

//this is the picaadapter class which extends the firebaseadapter class and populates the view again and again once a new image is added

public class picadapter extends FirebaseListAdapter<user> {

    private String username;

    public picadapter(Query ref,Activity activity,int layout,String mUsername){
        super(ref,user.class,layout,activity);
    }


    @Override
    protected void populateview(View v, user model) {

    }
}
