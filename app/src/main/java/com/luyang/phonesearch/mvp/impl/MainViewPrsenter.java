package com.luyang.phonesearch.mvp.impl;

import android.util.Log;

import com.luyang.phonesearch.model.Phone;
import com.luyang.phonesearch.mvp.MvpMainView;
import com.luyang.phonesearch.utils.HttpUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by luyang on 2018/1/13.
 */

public class MainViewPrsenter extends BasePresenter {


    MvpMainView mvpMainView;
    final static String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?";
    Phone mPhone;

    public MainViewPrsenter(MvpMainView mvpMainView) {
        this.mvpMainView = mvpMainView;
    }

    public void searchPhoneInfo(String phoneNumber) {
        if (phoneNumber.length() != 11) {
            mvpMainView.showToast("错误号码格式");
            return;
        }

        mvpMainView.showLoading();

        Map<String, String> params = new HashMap<>();
        params.put("tel", phoneNumber);
        /**
         * 此时的回调为：MainViewPrsenter为主动调用者A，HttpUtil为被调用者B，A-->B(sendGetRequest),B方法异步执行
         * 执行完之后通过handler子线程告诉UI线程结果，
         */
        HttpUtil httpUtil = new HttpUtil(new HttpUtil.HttpResponse() {
            @Override
            public void onSucess(Object object) {
//                __GetZoneResult_ = {
//                        mts:'1560008',
//                        province:'北京',
//                        catName:'中国联通',
//                        telString:'15600082886',
//                        areaVid:'29400',
//                        ispVid:'137815084',
//                        carrier:'北京联通'

                String jsondata = (String) object;

                int index = jsondata.indexOf("{");
                jsondata = jsondata.substring(index, jsondata.length());
                try {
                    mPhone = new Phone();
                    JSONObject data = new JSONObject(jsondata);
                    String phoneNumber = (String) data.get("telString");
                    String phoneProvince = (String) data.get("province");
                    String phoneCarrier = (String) data.get("catName");
                    String phonelocalCarrier = (String) data.get("carrier");

                    mPhone.setPhoneNumber(phoneNumber);
                    mPhone.setProvince(phoneProvince);
                    mPhone.setCarrier(phoneCarrier);
                    mPhone.setLocalCarrier(phonelocalCarrier);

                    mvpMainView.cancelLoading();
                    mvpMainView.updateView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String error) {
                mvpMainView.cancelLoading();
                mvpMainView.showToast(error);
            }
        });


        httpUtil.sendGetRequest(url, params);

    }

//外界获取查询信息方法
    public Phone getPhoneInfo(){
        return mPhone;
    }
}
