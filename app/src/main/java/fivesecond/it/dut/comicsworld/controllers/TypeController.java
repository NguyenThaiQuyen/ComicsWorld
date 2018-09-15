package fivesecond.it.dut.comicsworld.controllers;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.models.Type;

public class TypeController {

    private static TypeController sInstance = null;

    private static TypeController getInstance() {
        if(sInstance == null) {
            sInstance = new TypeController();
        }
        return sInstance;
    }

    private ArrayList<Type> mTypeList;

    private TypeController() {

        mTypeList = new ArrayList<>();
    }

    public ArrayList<Type> getTypeList() {
        return mTypeList;
    }

    public void addType() {

    }
}
