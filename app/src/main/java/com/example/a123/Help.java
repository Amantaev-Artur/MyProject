package com.example.a123;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a123.ui.chat.Message;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Help extends AppCompatActivity {

    private String email;
    private LinearLayout activity_help;
    private TextView text;
    private EditText review;
    private FloatingActionButton sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        activity_help = findViewById(R.id.action_help);
        text = findViewById(R.id.text);
        review = findViewById(R.id.review);
        sendBtn = findViewById(R.id.btnSendReview);
        email = EMAIL.YourEMAIL;

        Load_setting();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review = findViewById(R.id.review);
                if(review.getText().length()==0) {
                    return;
                }
                FirebaseDatabase.getInstance().getReference("Review").push().setValue(
                        new TextReview(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                review.getText().toString()
                        )
                );
                review.setText("");
                onBackPressed();
            }
        });
    }


    public void Load_setting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean chk_night = sp.getBoolean("NIGHT", false);
        if (chk_night) {
            activity_help.setBackgroundColor(Color.parseColor("#222222"));
            text.setTextColor(Color.parseColor("#ffffff"));
            review.setHintTextColor(Color.parseColor("#b0b0b0"));
            review.setTextColor(Color.parseColor("#ffffff"));
        } else {
            activity_help.setBackgroundColor(Color.parseColor("#ffffff"));
            text.setTextColor(Color.parseColor("#222222"));
            review.setHintTextColor(Color.parseColor("#222222"));
            review.setTextColor(Color.parseColor("#222222"));
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Help.this, Activity.class);
        startActivity(intent);
        finish();
    }
}