package com.example.blackfiguresquizjd;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Firebase {
    private DatabaseReference database;
    private FirebaseAuth auth;
    private String userId;

    public Firebase(Context context) {

        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(context);
        }


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("scores");
    }


    public void signIn(final Callback callback) {
        if (auth.getCurrentUser() != null) {
            userId = auth.getCurrentUser().getUid();
            callback.onComplete();
        } else {
            auth.signInAnonymously().addOnCompleteListener(task -> {
                if (task.isSuccessful() && auth.getCurrentUser() != null) {
                    userId = auth.getCurrentUser().getUid();
                    callback.onComplete();
                }
            });
        }
    }


    public void saveScore(int score) {
        if (userId != null) {
            database.child(userId).child("score").setValue(score);
        }
    }


    public void getScore(final ScoreCallback callback) {
        if (userId != null) {
            database.child(userId).child("score").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Integer score = snapshot.getValue(Integer.class);
                    callback.onScore(score != null ? score : 0);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    callback.onScore(0);
                }
            });
        }
    }

    public interface Callback {
        void onComplete();
    }

    public interface ScoreCallback {
        void onScore(int score);
    }
}