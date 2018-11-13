package fivesecond.it.dut.comicsworld.controllers;

import java.io.Serializable;
import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.models.Type;

public class TypeController implements Serializable{

    private static TypeController sInstance = null;

    public static TypeController getInstance() {
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

    public void addTypeToList(Type type) {
        if(!mTypeList.contains(type)) mTypeList.add(type);
    }

}
