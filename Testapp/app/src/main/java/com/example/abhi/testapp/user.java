package com.example.abhi.testapp;

import java.util.Map;

/**
 * Created by Abhi on 3/28/2015.
 */
public class user {

    private String DisplayName;

    private String Username;

    private String Password;

    private boolean State;

    private Map<String,String> eventidmap;//maps integer to event id

    //constructors
    public user(){

    }

    public user(String N, String u, String p){

        DisplayName = N;

        Username = u;

        Password = p;

        State = false;


    }

    // Setters

    public void setPassword(String p){

        Password = p;
    }

    public void setState(boolean s){

        State = s;

    }

    public void addtoeventidmap(String str){
        String id =  String.valueOf(eventidmap.size());
        eventidmap.put(str,id);//map with keys as event name and id as values
    }

    public Map<String,String> geteventidmap(){
        return eventidmap;
    }
    // Getters
    public String getDisplayName(){

        return DisplayName;

    }

    public String getUserName(){

        return Username;
    }

    public String getPassword(){

        return Password;
    }

    public Boolean getState(){

        return State;
    }


}