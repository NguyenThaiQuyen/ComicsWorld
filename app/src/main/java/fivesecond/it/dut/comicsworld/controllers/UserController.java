package fivesecond.it.dut.comicsworld.controllers;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.models.Type;

public class UserController {
    private static UserController sInstance = null;

    private static UserController getInstance() {
        if(sInstance == null) {
            sInstance = new UserController();
        }
        return sInstance;
    }

    private ArrayList<Type> mUserList;

    private UserController() {

        mUserList = new ArrayList<>();
    }

    public ArrayList<Type> getUserList() {
        return mUserList;
    }

    public void addUser() {

    }
}
