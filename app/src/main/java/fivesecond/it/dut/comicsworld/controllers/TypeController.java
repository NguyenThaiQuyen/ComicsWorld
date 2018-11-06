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

    public String[] getTypeName(){
        String [] names = new String[mTypeList.size()];
        for(int i = 0; i < names.length; i++){
            names[i] = mTypeList.get(i).getName();
        }
        return names;
    }

    public ArrayList<Type> getTypeList() {
        return mTypeList;
    }

    public void addType() {

    }

    public void load()
    {
        fakeData();
    }

    public void fakeData()
    {
        mTypeList.add(new Type(1, "Action"));
        mTypeList.add(new Type(2, "Adventure"));
        mTypeList.add(new Type(3, "Love Story"));
    }
}
