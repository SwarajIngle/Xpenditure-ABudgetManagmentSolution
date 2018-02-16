package com.xpenditure.www.xpenditure;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by Swaraj on 30-01-2018.
 */

public class RemoveRecycler {
    private String Title;
    private static ArrayList<String> DisplayTitle;

    public RemoveRecycler() {
    }

    public RemoveRecycler(View catgview) {
    }

    public RemoveRecycler(String title) {

        Title = title;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public static ArrayList<String> setDiaplayTitle() {

        return DisplayTitle;
    }


    public static void getDisplayTitle(ArrayList title) {
        DisplayTitle = title;
    }

}
