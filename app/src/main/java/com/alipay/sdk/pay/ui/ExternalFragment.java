package com.alipay.sdk.pay.ui;

import com.bynnean.cartoon.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExternalFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pay_external, container, false);
		((TextView)(view.findViewById(R.id.product_subject))).setText(PayDemoActivity.title);
		((TextView)(view.findViewById(R.id.detail))).setText(PayDemoActivity.title);
		((TextView)(view.findViewById(R.id.product_price))).setText(PayDemoActivity.money+"元");
		
		return view;
	}
}
