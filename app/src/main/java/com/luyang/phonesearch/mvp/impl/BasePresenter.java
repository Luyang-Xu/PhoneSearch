package com.luyang.phonesearch.mvp.impl;

import android.content.Context;

/**
 * Created by luyang on 2018/1/13.
 */

public class BasePresenter {

    Context mContext;
    public void attach(Context context){
        mContext = context;
    }

    public void onPause(){}

    public void onResume(){}

    public void onDestroy(){
        mContext = null;
    }
}
