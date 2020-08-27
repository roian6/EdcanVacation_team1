package com.edcan.shareformproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.edcan.shareformproject.databinding.FragmentHomeBinding;
import com.edcan.shareformproject.databinding.FragmentMoreBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MoreFragment extends Fragment {

    public static MoreFragment newInstance(){
        return new MoreFragment();
    }

    private Context mContext;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FragmentMoreBinding binding;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container,false);

        if (user != null){
            Uri photoUrl = user.getPhotoUrl();
            binding.setProfileImgLink(photoUrl.toString());
        }else{
            binding.setProfileImgLink("");
        }
        binding.setName(UserCache.getUser(mContext).getNick());
        binding.setUid(UserCache.getUser(mContext).getUid());

        binding.btnMoreLogout.setOnClickListener(view1 -> signOut());
        binding.btnMoreRevoke.setOnClickListener(view1 -> deleteUser());
        return binding.getRoot();
    }

    private void signOut() {
        UserCache.clear(mContext);
        firebaseAuth.signOut();
        Toast.makeText(mContext, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(mContext, GoogleLoginActivity.class));
        getActivity().finish();
    }

    private void deleteUser(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("회원탈퇴");
        builder.setMessage("정말로 탈퇴 하시겠습니까?");
        builder.setPositiveButton("네", (dialogInterface, i)->{
            firebaseAuth.getCurrentUser().delete().addOnSuccessListener(runnable->{
                firebaseFirestore
                        .collection("userInfo")
                        .document(UserCache.getUser(mContext).getUid())
                        .delete()
                        .addOnSuccessListener(runnable1->{
                            Toast.makeText(mContext, "정상적으로 탈퇴 처리 되었습니다.", Toast.LENGTH_LONG).show();
                            signOut();
                        });
            });

        });
        builder.setNegativeButton("탈퇴 취소", (dialogInterface, i)->{});
        builder.show();
    }

}