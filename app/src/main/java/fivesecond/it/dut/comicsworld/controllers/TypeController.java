package fivesecond.it.dut.comicsworld.controllers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public void addType() {

    }

    public void load()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();
        ChildEventListener types = databaseReference.child("types").addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Type type = new Type(dataSnapshot.getKey(), String.valueOf(dataSnapshot.getValue()));
//                mTypeList.add(type);
                Type type = dataSnapshot.getValue(Type.class);
                //Intent intent = new Intent(TypeController.this, HomeScreenActivity.class);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
