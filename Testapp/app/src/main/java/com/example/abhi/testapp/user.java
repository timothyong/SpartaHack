package com.example.abhi.testapp;
import java.util.HashMap;
import java.util.Map;


public class user {

    private String DisplayName;

    private String Username;

    private String Password;

    private boolean State;

    private Map<String,String> eventidmap;//maps eventid = eventname
                                          //        key      value

    //constructors

    public user(String N, String u, String p){

        DisplayName = N;

        Username = u;

        Password = p;

        State = true;
    }

    // Setters

    public void setPassword(String p){

        Password = p;
    }

    public void setState(boolean s){

        State = s;

    }


    // Getters

    public Map<String,String> getMap(){
        Map<String,String> ret = new HashMap<String,String>();
        ret.put("username", getDisplayName());
        ret.put("password", getPassword());
        //ret.put("", value)
        return ret;

    }

    public Map<String,Map<String,String>> getindex(){
        Map<String,Map<String,String>> ret = new HashMap<String,Map<String,String>>();
        ret.put("eventidmap",eventidmap);
        return ret;
    }

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
	