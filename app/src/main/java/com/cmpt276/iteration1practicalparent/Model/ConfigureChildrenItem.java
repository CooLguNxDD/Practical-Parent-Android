package com.cmpt276.iteration1practicalparent.Model;

//Stores information of each child


import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;


import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.UI.ConfigureChildren.ConfigureChildren;

import java.io.InputStream;
import java.net.URI;

public class ConfigureChildrenItem {
    private String mImageResource;
    private String mText1;
    private String mText2;
    private int idOfChild;

    public ConfigureChildrenItem(String imageResource, String text1, String text2){
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        idOfChild = (int)(Math.random() * (2147483647 - 0));
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }

    public void setmImageResource(String imageResource) {
        this.mImageResource = imageResource;
    }

    public String getImageResource(){
        return mImageResource;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public int getIdOfChild() {
        return idOfChild;
    }
}
