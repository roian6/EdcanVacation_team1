package com.edcan.shareformproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.edcan.shareformproject.databinding.ActivityInfoeditBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class infodeditActivity extends AppCompatActivity {
    private ActivityInfoeditBinding binding;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoedit);
    }

}
