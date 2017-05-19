package com.bynnean.cartoon.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.ui.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttentionFragment extends Fragment {


    public AttentionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attention, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView login= (ImageView) view.findViewById(R.id.login_attention);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setSelected(true);
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }
}
