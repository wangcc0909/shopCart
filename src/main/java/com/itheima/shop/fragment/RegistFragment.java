package com.itheima.shop.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.itheima.shop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static android.R.attr.country;
import static cn.smssdk.SMSSDK.getVerificationCode;
import static cn.smssdk.SMSSDK.submitVerificationCode;

/**
 * Created by mwqi on 2017/3/15.
 */

public class RegistFragment extends BaseFragment {

    @BindView(R.id.iv_back_icon)
    ImageView mIvBackIcon;
    @BindView(R.id.et_tel_phone)
    EditText mEtTelPhone;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.bt_send_code)
    Button mBtSendCode;
    @BindView(R.id.butt_submit)
    Button mButtSubmit;
    private EventHandler mEh;
    private Object mSMSCode;
    private Handler mSendSmsHandler;

    @Override
    protected void free() {
        SMSSDK.unregisterEventHandler(mEh);
    }

    @Override
    public void initData() {
        SMSSDK.initSDK(mContext, "1c15b9ef42b50", "2e36f7a906255f98e9bfc6b94b880492");
        //回调完成
//提交验证码成功
//获取验证码成功
//返回支持发送验证码的国家列表
        mEh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        mSendSmsHandler.sendEmptyMessageDelayed(0, 0);
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    Throwable mThrowable = (Throwable) data;
//                    {"status":603,"detail":"请填写正确的手机号码"}
                }
            }
        };
        SMSSDK.registerEventHandler(mEh); //注册短信回调
    }

    int mSecond = 60;
    @Override
    public View initView() {

        View view = View.inflate(mContext, R.layout.fragment_register, null);

        mSendSmsHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                if(mSecond > 0){
                    mSecond--;
                    mBtSendCode.setText(String.valueOf(mSecond));
                    mBtSendCode.setEnabled(false);
                    sendEmptyMessageDelayed(0,1000);

                }else{
                    mSecond = 60;
                    mBtSendCode.setText("发送验证码");
                    mBtSendCode.setEnabled(true);
                }

            }
        };

        return view;
    }


    @OnClick({R.id.bt_send_code,R.id.butt_submit})
    public void onClick(View view) {
        getSMSCode();
//        switch (view.getId()){
//            case R.id.bt_send_code:
//                getSMSCode();
//                break;
//
//            case R.id.butt_submit:
//                submitVerificationCode("+86", "15220258917", "2398");
//                break;
//        }


    }

    public Object getSMSCode() {
        String phone = mEtTelPhone.getText().toString().trim();

        getVerificationCode("+86", phone);

        return mSMSCode;
    }
}
