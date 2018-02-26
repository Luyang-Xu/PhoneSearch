package com.luyang.phonesearch;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.luyang.phonesearch.model.Phone;
import com.luyang.phonesearch.mvp.MvpMainView;
import com.luyang.phonesearch.mvp.impl.MainViewPrsenter;

public class MainActivity extends AppCompatActivity implements MvpMainView {

    EditText mPhoneNumber;
    Button mSearch;
    LinearLayout res_layout;
    TextView mTextPhoneNumber;
    TextView mProvince;
    TextView mCarrier;
    TextView mBelongingCarrier;
    MainViewPrsenter mMainViewPrsenter;
    Phone mPhone;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initView() {
        mPhoneNumber = findViewById(R.id.id_phonenumber);
        mSearch = findViewById(R.id.id_search);
        res_layout = findViewById(R.id.id_result_layout);

        mTextPhoneNumber = res_layout.findViewById(R.id.id_res_phonenumber);
        mProvince = res_layout.findViewById(R.id.id_res_province);
        mCarrier = res_layout.findViewById(R.id.id_res_carrier);
        mBelongingCarrier = res_layout.findViewById(R.id.id_res_belong_carrier);

        mMainViewPrsenter = new MainViewPrsenter(this);
        mMainViewPrsenter.attach(this);
    }

    /**
     * 初始化搜索按钮监听事件
     */
    private void initEvent() {
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                String num = mPhoneNumber.getText().toString().trim();
                mMainViewPrsenter.searchPhoneInfo(num);
            }
        });
    }

    @Override
    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, "", "正在加载", true, false);
        } else if (progressDialog.isShowing()) {
            progressDialog.setTitle("");
            progressDialog.setMessage("正在加载");
        }
        progressDialog.show();
    }

    @Override
    public void cancelLoading() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateView() {

        mPhone = mMainViewPrsenter.getPhoneInfo();
        mTextPhoneNumber.setText(mPhone.getPhoneNumber());
        mProvince.setText(mPhone.getProvince());
        mCarrier.setText(mPhone.getCarrier());
        mBelongingCarrier.setText(mPhone.getLocalCarrier());
    }
}
