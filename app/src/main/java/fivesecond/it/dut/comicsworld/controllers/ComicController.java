package fivesecond.it.dut.comicsworld.controllers;

import java.io.Serializable;
import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.models.Comic;

public class ComicController implements Serializable{
    private static ComicController sInstance = null;

    public static ComicController getInstance() {
        if(sInstance == null) {
            sInstance = new ComicController();
        }
        return sInstance;
    }

    private ArrayList<Comic> mComicList;
    private ComicController() {
        mComicList = new ArrayList<>();
    }



    public void addComic() {

    }

    public void load()
    {
        fakeData();
    }

    private void fakeData() {
    }


}
