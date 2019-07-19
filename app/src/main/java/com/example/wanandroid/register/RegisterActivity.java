package com.example.wanandroid.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseActivity;
import com.example.wanandroid.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements RegisterContract.RegisterView {


    @BindView(R.id.edt_regi_account)
    EditText regiAccountEdt;
    @BindView(R.id.edt_regi_password)
    EditText regiPasswordEdt;
    @BindView(R.id.edt_regi_repassword)
    EditText regiRepasswordEdt;
    @BindView(R.id.btn_register)
    Button registerBtn;

    private RegisterContract.RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        registerPresenter = new RegisterPresenter();
        registerPresenter.attach(this);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }


    @Override
    public void showToast(String msg) {
        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getEdt(EditText edt) {
        return edt.getText().toString();
    }

    private void register(){
        String username = getEdt(regiAccountEdt);
        String password = getEdt(regiPasswordEdt);
        String repassword = getEdt(regiRepasswordEdt);
        registerPresenter.register(RegisterActivity.this, username, password, repassword );
    }
    @Override
    protected void onDestroy() {
        registerPresenter.detachView(this);
        super.onDestroy();
    }
}
