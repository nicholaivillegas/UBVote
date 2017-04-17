package com.androidapp.votingsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.button_vote)
    Button buttonVote;
    private DatabaseReference mDatabase;
    private DatabaseReference myRef;
    private String mId;
    private String mName = "";
    private ValueEventListener ref;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("votes");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        textName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
                return false;
            }
        });

        ref = myRef.child("users").child(mId).child("vote").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    if (dataSnapshot.getValue(String.class).equals("true")) {
                        buttonVote.setBackgroundColor(Color.GREEN);
                    } else {
                        buttonVote.setBackgroundColor(Color.RED);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your name: ");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mName = input.getText().toString();
                textName.setText(mName);
                User user = new User(mId, mName, "false");
                myRef.child("users").child(mId).setValue(user);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @OnClick(R.id.button_vote)
    public void onViewClicked() {

        if (i == 0) {
            myRef.child("users").child(mId).child("vote").setValue("true");
            i = 1;
        } else {
            myRef.child("users").child(mId).child("vote").setValue("false");
            i = 0;
        }
    }
}
