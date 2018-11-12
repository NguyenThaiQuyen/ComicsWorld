package fivesecond.it.dut.comicsworld.controllers;

import java.io.Serializable;
import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.models.Comic;

public class ComicController implements Serializable {
    private ArrayList<Comic> mComicList;

    private static ComicController sInstance = null;
    private ComicController() {
        mComicList = new ArrayList<>();

    }

    public static ComicController getInstance() {
        if(sInstance == null) {
            sInstance = new ComicController();
        }
        return sInstance;
    }


    public ArrayList<Comic> getComicList()
    {
        return mComicList;
    }

    public void addComicToList(Comic comic)
    {
        mComicList.add(comic);
    }

    public ArrayList<Comic> getComicByIdType(String id)
    {
        ArrayList<Comic> result = new ArrayList<>();

        for(Comic comic: mComicList)
        {
            if(comic.getIdType().equals(id))
            {
                result.add(comic);
            }
        }
        return result;
    }
}
