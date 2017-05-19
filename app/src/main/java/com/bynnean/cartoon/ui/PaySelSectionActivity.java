package com.bynnean.cartoon.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bynnean.cartoon.R;

/**
 * ${desc}
 *
 * @author bin2.he@gmail.com
 * @date 16-10-24-上午9:13
 */
public class PaySelSectionActivity extends AppCompatActivity{

    TextView  payMoney;
    Button payBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_sel_layout);
        payMoney = (TextView)findViewById(R.id.pay_money);
        payBtn = (Button)findViewById(R.id.btn_login);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
