package com.androidapp.votingsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nico on 4/17/2017.
 */

public class AdminActivity extends AppCompatActivity {

    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.linear1)
    LinearLayout linear1;
    //    @BindView(R.id.linear2)
//    LinearLayout linear2;
    @BindView(R.id.button_clear)
    Button buttonClear;
    private DatabaseReference mDatabase;
    private DatabaseReference myRef;
    private ValueEventListener ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);
        ButterKnife.bind(this);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("votes");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        buttonClear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(AdminActivity.this, "YOW", Toast.LENGTH_SHORT).show();
                myRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                            try {
                                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                                    User user = new User(datas.getKey(), datas.child("name").getValue(String.class), "false");
                                    myRef.child("users").child(datas.getKey()).setValue(user);

                                }
                            } catch (Exception ex) {
                                Log.e("RAWR", ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                linear1.removeAllViewsInLayout();
                return false;
            }
        });
        ref = myRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    try {
                        linear1.removeAllViewsInLayout();
                        TextView textView;
                        for (DataSnapshot datas : dataSnapshot.getChildren()) {
//                            if (datas.child("station").getValue(String.class).equals(selectedStation)) {
//                                ratingCount++;
//                                rating = datas.child("rating").getValue(float.class) + rating;
//                            }
                            if (datas.child("vote").getValue(String.class).equals("true")) {
                                textView = new TextView(getApplicationContext());
                                textView.setText(datas.child("name").getValue(String.class) + " wants you!");
                                textView.setTextSize(32);
                                textView.setGravity(Gravity.CENTER | Gravity.BOTTOM);
                                linear1.addView(textView);
                            }

                        }
                    } catch (Exception ex) {
                        Log.e("RAWR", ex.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
