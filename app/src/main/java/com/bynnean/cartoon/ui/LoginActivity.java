package com.bynnean.cartoon.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.util.PayUtils;

/**
 * Created by Administrator on 2015/11/18.
 */
public class LoginActivity extends AppCompatActivity {

    EditText  phoneText,pwdText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phoneText = (EditText)findViewById(R.id.phone_login);
        pwdText = (EditText)findViewById(R.id.pwd_login);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(phoneText.getText().toString()) || phoneText.getText().length() != 11){
                    Toast.makeText(LoginActivity.this,"请输入11位以上的手机密码!",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(phoneText.getText().toString()) || phoneText.getText().length() != 11){
                    Toast.makeText(LoginActivity.this,"请输入6位以上的密码!",Toast.LENGTH_SHORT).show();
                } else {
                    PayUtils.updateLoginState(true);
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    LoginActivity.this.finish();
                }
            }
        });
    }
}
