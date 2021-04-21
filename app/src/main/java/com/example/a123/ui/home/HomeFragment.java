package com.example.a123.ui.home;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.a123.R;

public class HomeFragment extends Fragment {

    private ConstraintLayout frg_home;
    private TextView text;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        frg_home = view.findViewById(R.id.fragment_home);
        text = view.findViewById(R.id.textView2);
        Load_setting();
        return view;
    }


    public void Load_setting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean chk_night = sp.getBoolean("NIGHT", false);
        if (chk_night) {
            frg_home.setBackgroundColor(Color.parseColor("#222222"));
            text.setTextColor(Color.parseColor("#ffffff"));
        } else {
            frg_home.setBackgroundColor(Color.parseColor("#ffffff"));
            text.setTextColor(Color.parseColor("#000000"));
        }
    }

}