package com.example.a123.ui.chat;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.a123.EMAIL;
import com.example.a123.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatFragment extends Fragment {

    private ChatViewModel chatViewModel;
    private static int SIGN_IN_CODE = 1;
    private RelativeLayout activity_map;
    private FirebaseListAdapter<Message> adapter;
    private FloatingActionButton sendBtn;
    private DatabaseReference messages;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);

        displayAllMessages();



        String email = EMAIL.svar1;
        sendBtn = (FloatingActionButton)view.findViewById(R.id.btnSend);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textField = (EditText)view.findViewById(R.id.messageField);
                if(textField.getText().length()==0) {
                    return;
                }
                FirebaseDatabase.getInstance().getReference("Messages").push().setValue(
                        new Message(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                textField.getText().toString()
                        )
                );
                textField.setText("");
            }
        });
        return view;
    }

    private void displayAllMessages() {
        ListView listOfMessages = (ListView)view.findViewById(R.id.list_of_message);
        FirebaseListOptions<Message> options =
                new FirebaseListOptions.Builder<Message>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Messages"), Message.class)
                        .setLayout(R.layout.list_item)
                        .setLifecycleOwner(this)
                        .build();
        adapter = new FirebaseListAdapter<Message>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Message model, int position) {
                TextView mess_user, mess_time;
                TextView mess_text;

                mess_user = v.findViewById(R.id.message_user);
                mess_time = v.findViewById(R.id.message_time);
                mess_text = v.findViewById(R.id.message_text);
                mess_user.setText(model.getUserName());
                mess_text.setText(model.getTextMessage());
                mess_time.setText(DateFormat.format("dd-MM-yyyy HH:mm:ss", model.getMessageTime()));

            }
        };
        listOfMessages.setAdapter(adapter);
    }
}