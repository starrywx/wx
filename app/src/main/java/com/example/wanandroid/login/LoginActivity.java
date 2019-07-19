package com.example.wanandroid.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseActivity;
import com.example.wanandroid.homepage.MainActivity;
import com.example.wanandroid.register.RegisterActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginContract.LoginView {

    @BindView(R.id.edt_login_account)
    EditText loginAccountEdt;
    @BindView(R.id.edt_login_password)
    EditText loginPasswordEdt;
    @BindView(R.id.ckb_rememberpass)
    CheckBox rememberPassCkb;
    @BindView(R.id.ckb_autologin)
    CheckBox autoLoginCkb;
    @BindView(R.id.btn_login)
    Button loginBtn;
    @BindView(R.id.btn_to_register)
    Button RegisterBtn;

    private LoginContract.LoginPresenter loginPresenter;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter=new LoginPresenter();
        loginPresenter.attach(this);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        //点击事件处理
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            //点击跳转至注册界面
            public void onClick(View v) {
                if (v.getId() == R.id.btn_to_register) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            //将输入数据于文件中数据比较，相同则登陆成功
            public void onClick(View v) {
                if (v.getId() == R.id.btn_login) {
                    login();
                }
            }
        });
        boolean isRemember = pref.getBoolean("remember_password", false);
        boolean isAutoLogin = pref.getBoolean("auto_login", false);
        //是否选择记住密码
        if (isRemember) {
            setEdt(loginPresenter.username(), loginPresenter.password());
            rememberPassCkb.setChecked(true);
            if (isAutoLogin) {
                setEdt(loginPresenter.username(), loginPresenter.password());
                autoLoginCkb.setChecked(true);
                login();
            }
        }
    }

    //登录
    private void login(){
        String username = getUsername();    //获得输入的账号
        String password = getPassword();  //获得输入的密码
        loginPresenter.login(LoginActivity.this,username,password,autoLoginCkb.isChecked(),rememberPassCkb.isChecked());
    }

    //通知用户登录情况
    @Override
    public void showToast( final String msg) {
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getUsername() {
        String content = loginAccountEdt.getText().toString();
        if (TextUtils.isEmpty(content)) {
            showToast("输入不能为空,请输入账号。");
        }
        return content;
    }

    @Override
    public String getPassword() {
        String content = loginPasswordEdt.getText().toString();
        if (TextUtils.isEmpty(content)) {
            showToast("输入不能为空,请输入密码。");
        }
        return content;
    }

    @Override
    public void setEdt(String username, String password) {
        loginAccountEdt.setText(username);
        loginPasswordEdt.setText(password);
    }

    @Override
    public void onDestroy(){
        loginPresenter.detachView(this);
        super.onDestroy();
    }
}
