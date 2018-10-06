package fivesecond.it.dut.comicsworld.controllers;

import java.io.Serializable;
import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.models.Comic;
import fivesecond.it.dut.comicsworld.models.Type;

public class ComicController {
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

    public ArrayList<Comic> getComicList(Type type) {
        ArrayList<Comic> list = new ArrayList<>();
        for(Comic c : mComicList) {
            if(c.getIdType() == type.getId()) {
                list.add(c);
            }
        }
        return list;
    }

    public void addComic() {

    }

    public void load()
    {
        fakeData();
    }

    public void fakeData()
    {
        mComicList.add(new Comic(1, "N11", 1, "@drawable/conan.jpg", 5, "You are the Apple of my eye", 0, 4,5, "Sonvo"));
        mComicList.add(new Comic(2, "N12", 1, "@drawable/conan.jpg", 5, "You are the Apple of my eye", 0, 4,5, "Sonvo"));
        mComicList.add(new Comic(3, "N13", 1, "@drawable/conan.jpg", 5, "You are the Apple of my eye", 0, 4,5, "Sonvo"));
        mComicList.add(new Comic(4, "N14", 1, "@drawable/conan.jpg", 5, "You are the Apple of my eye", 0, 4,5, "Sonvo"));
        mComicList.add(new Comic(5, "N21", 2, "@drawable/conan.jpg", 5, "You are the Apple of my eye", 0, 4,5, "Sonvo"));
        mComicList.add(new Comic(6, "N22", 2, "@drawable/conan.jpg", 5, "You are the Apple of my eye", 0, 4,5, "Sonvo"));
        mComicList.add(new Comic(7, "N23", 2, "@drawable/conan.jpg", 5, "You are the Apple of my eye", 0, 4,5, "Sonvo"));
        mComicList.add(new Comic(8, "N14", 1, "@drawable/conan.jpg", 5, "You are the Apple of my eye", 0, 4,5, "Sonvo"));
        mComicList.add(new Comic(9, "N31", 3, "@drawable/conan.jpg", 5, "You are the Apple of my eye", 0, 4,5, "Sonvo"));
        mComicList.add(new Comic(10, "N32", 3, "@drawable/conan.jpg", 5, "You are the Apple of my eye", 0, 4,5, "Sonvo"));
        mComicList.add(new Comic(11, "N33", 3, "@drawable/conan.jpg", 5, "You are the Apple of my eye", 0, 4,5, "Sonvo"));
        mComicList.add(new Comic(12, "N24", 2, "@drawable/conan.jpg", 5, "You are the Apple of my eye", 0, 4,5, "Sonvo"));
        mComicList.add(new Comic(13, "N34", 3, "@drawable/conan.jpg", 5, "You are the Apple of my eye", 0, 4,5, "Sonvo"));

    }


}
