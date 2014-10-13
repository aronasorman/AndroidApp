package org.learningequality.androidapp.views;

import java.util.Observable;

/**
 * Created by Eli on 10/12/14.
 */
public class MyObservable {
    //create an object of SingleObject
    private static MyObservable id_tracker = new MyObservable();
    private int tracked_id;

    public MyObservable(){ }

    public static MyObservable get_id_tracker(){
        return id_tracker;
    }

    public void set_tracked_id(int id){
        tracked_id = id;
    }

    public int get_tracked_id(){
        return tracked_id;
    }
}
