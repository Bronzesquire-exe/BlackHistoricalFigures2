package com.example.blackfiguresquizjd;

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

    public Firebase() {
        try {
            database = FirebaseDatabase.getInstance().getReference("scores");
            auth = FirebaseAuth.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sign in and get user ID
    public void signIn(final Callback callback) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Save score to Firebase
    public void saveScore(int score) {
        try {
            if (userId != null) {
                database.child(userId).child("score").setValue(score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get score from Firebase
    public void getScore(final ScoreCallback callback) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface Callback {
        void onComplete();
    }

    public interface ScoreCallback {
        void onScore(int score);
    }
}