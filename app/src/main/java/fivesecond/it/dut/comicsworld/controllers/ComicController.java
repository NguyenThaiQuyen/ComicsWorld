package fivesecond.it.dut.comicsworld.controllers;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.models.Comic;

public class ComicController {
    private static ComicController sInstance = null;

    private static ComicController getInstance() {
        if(sInstance == null) {
            sInstance = new ComicController();
        }
        return sInstance;
    }

    private ArrayList<Comic> mComicList;
    private ComicController() {
        mComicList = new ArrayList<>();
    }

    public ArrayList<Comic> getComicList(int idType) {
        ArrayList<Comic> list = new ArrayList<>();
        for(Comic c : mComicList) {
            if(c.getIdType() == idType) {
                list.add(c);
            }
        }
        return list;
    }

    public void addComic() {

    }
}
