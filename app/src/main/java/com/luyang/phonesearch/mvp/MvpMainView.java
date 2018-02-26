package com.luyang.phonesearch.mvp;

/**
 * Created by luyang on 2018/1/13.
 */

public interface MvpMainView extends MvpLoadingView {

    public void showToast(String msg);

    public void updateView();
}
