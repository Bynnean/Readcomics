package com.bynnean.cartoon.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.ui.LoginActivity;
import com.bynnean.cartoon.ui.SetupActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndividualFragment extends Fragment {



    public IndividualFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_individual, container, false);
        //关注跳转
        view.findViewById(R.id.IndividualItemFollow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intentfollow = new Intent(getActivity(), MyFollowActivity.class);
//                startActivity(intentfollow);
                Toast.makeText(getContext(),"该功能暂未开放",Toast.LENGTH_SHORT).show();
            }
        });
        //收藏跳转
        view.findViewById(R.id.IndividualItemCollection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intentcollection = new Intent(getActivity(), MyCollectionActivity.class);
//                startActivity(intentcollection);
                Toast.makeText(getContext(),"该功能暂未开放",Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.individual_head_portrait).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        //设置跳转
        view.findViewById(R.id.IndividualItemSetup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsetup = new Intent(getActivity(), SetupActivity.class);
                startActivity(intentsetup);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
