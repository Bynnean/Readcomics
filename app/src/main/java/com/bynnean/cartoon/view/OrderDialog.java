package com.bynnean.cartoon.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bynnean.cartoon.R;


/**
 * ${desc}
 *
 * @author bin2.he@gmail.com
 * @date 16-10-8-上午10:29
 */
public class OrderDialog extends Dialog{

    static  TextView orderWay1,orderWay2,orderWay3;
    static  TextView  desTextView;
    public static  int index = 0;

    public OrderDialog(Context context) {
        super(context);
    }

    public OrderDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Activity context;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        public Builder(Activity context) {
            this.context = context;
        }

        public Builder setPositiveButton(
                DialogInterface.OnClickListener listener) {
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(
                DialogInterface.OnClickListener listener) {
            this.negativeButtonClickListener = listener;
            return this;
        }

        public OrderDialog create(String titleText, String posText, String negText) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final OrderDialog dialog = new OrderDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_normal_layout, null);

            orderWay1 = (TextView)layout.findViewById(R.id.order_way1);
            orderWay2 = (TextView)layout.findViewById(R.id.order_way2);
            orderWay3 = (TextView)layout.findViewById(R.id.order_way3);
            desTextView = (TextView)layout.findViewById(R.id.order_des);
            initWay();
            orderWay1.setSelected(true);
            orderWay1.setOnClickListener(listener);
            orderWay2.setOnClickListener(listener);
            orderWay3.setOnClickListener(listener);

            dialog.setContentView(layout);
            if (positiveButtonClickListener != null) {
                ((Button) layout.findViewById(R.id.order_btn))
                        .setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                positiveButtonClickListener.onClick(dialog,
                                        DialogInterface.BUTTON_POSITIVE);
                            }
                        });
            }
            if (negativeButtonClickListener != null) {
                ((Button) layout.findViewById(R.id.cancle_btn))
                        .setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                negativeButtonClickListener.onClick(dialog,
                                        DialogInterface.BUTTON_NEGATIVE);
                            }
                        });
            }
            WindowManager windowManager = context.getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = (int) (display.getWidth() * 0.75);
            lp.height=  (int) (display.getHeight() * 0.32);
            lp.gravity = Gravity.CENTER;
            dialogWindow.setGravity(Gravity.CENTER);
            dialog.getWindow().setAttributes(lp);
            dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
            return dialog;
        }
    }

    private static  void initWay(){
        orderWay1.setSelected(false);
        orderWay3.setSelected(false);
        orderWay2.setSelected(false);
    }


    static  View.OnClickListener  listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
             initWay();
             view.setSelected(true);
             String text = view.getTag().toString();
            if(text.contains("0.1")) index = 0;
            else if(text.contains("10")) index = 1;
            else if(text.contains("20")) index = 2;
             desTextView.setText(text);
        }
    };

}
