package com.example.quantumscan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ProfileFragment extends Fragment {

    TextView userName;
    TextView userPronoun;
    TextView userPhoneNumb;
    TextView userEmail;
    TextView userInfo;

    String name;
    String pronoun;
    String phoneNumb;
    String email;
    String info;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        userName = view.findViewById(R.id.userNameText);
        userPronoun = view.findViewById(R.id.userPronounText);
        userPhoneNumb = view.findViewById(R.id.userPhoneNumbText);
        userEmail = view.findViewById(R.id.userEmailText);
        userInfo = view.findViewById(R.id.userInfoText);



        return view;
    }
}