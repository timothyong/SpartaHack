package com.example.abhi.testapp;

/**
 * Created by Abhi on 3/28/2015.
 */
public class user {

    private String DisplayName;

    private String Username;

    private String Password;

    private boolean State;



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