package com.example.wufan.wufan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.wufan.wufan.R;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.example.wufan.wufan.config.Constants;
import com.example.wufan.wufan.http.callback.HttpRequestCallback;
import com.example.wufan.wufan.http.HttpRequestFactory;
import com.example.wufan.wufan.http.exception.ApiException;
import com.example.wufan.wufan.model.request.User;
import com.example.wufan.wufan.model.response.OAuthToken;
import com.example.wufan.wufan.util.UIUtils;

import static com.example.wufan.wufan.util.XAuthUtils.parseToken;

public class Loginactivity extends AppCompatActivity {

    @BindView(R.id.username) EditText mEditUserName;
    @BindView(R.id.password) EditText mEditPassword;
    @BindView(R.id.loginButton) Button mButtonLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        ButterKnife.bind(this);
        initButtonStatus();
    }


    protected boolean initButtonStatus(){
        final String username = mEditUserName.getText().toString().trim();
        final String password = mEditPassword.getText().toString().trim();
        boolean buttonStatus = false;

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            mButtonLogin.setEnabled(true);
            buttonStatus = true;
        }

        return buttonStatus;
    }


    @OnClick(R.id.loginButton)
    public void submit(View view) {

        final String username = mEditUserName.getText().toString().trim();
        final String password = mEditPassword.getText().toString().trim();

        Constants.FanFou.USERNAME = username;
        Constants.FanFou.PASSWORD = password;

        HttpRequestFactory.getInstance()
                .getAccessToken(username, password, new HttpRequestCallback<String>() {
                    @Override
                    public void onStart() {
                        mButtonLogin.setText(getString(R.string.logining_text));
                    }

                    @Override
                    public void onSuccess(String responseData) {
                        mButtonLogin.setText(getString(R.string.login_text));

                        OAuthToken oAuthToken = parseToken(responseData);
                        User user = new User(username, password, oAuthToken);
                        //UserHolder.getInstance().saveUser(user);
                        Logger.json(new Gson().toJson(user));

                        startActivity(new Intent(getApplication(),mainActivity.class)); // 显示第2屏
                        Loginactivity.this.finish();   // 结束第1屏
                        finish();
                    }

                    @Override
                    public void onFail(ApiException apiException) {
                        mButtonLogin.setText(getString(R.string.login_text));
                        UIUtils.showToast(Loginactivity.this, apiException.getErrorMessage());
                    }
                });
    }

}
