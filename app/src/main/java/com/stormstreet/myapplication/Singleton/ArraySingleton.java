package com.stormstreet.myapplication.Singleton;

import com.stormstreet.myapplication.entity.Response.Chalist;

import java.util.ArrayList;

public class ArraySingleton {
    private static ArraySingleton mInstance;
    private ArrayList<Chalist> list = null;

    public static ArraySingleton getInstance() {
        if(mInstance == null)
            mInstance = new ArraySingleton();

        return mInstance;
    }

    private ArraySingleton() {
        list = new ArrayList<Chalist>();
    }
    // retrieve array from anywhere
    public ArrayList<Chalist> getArray() {
        return this.list;
    }
    public void addToArray(ArrayList<Chalist> listdata) {
    //Add element to array
        for(int i=0;i<listdata.size();i++)
        {
            list.add(listdata.get(i));
        }

    }
}
