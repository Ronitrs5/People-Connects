package com.example.peopleconnects;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.Objects;

public class CreatePostActivity extends AppCompatActivity {
    Uri imageUri;
    String myUrl="";
    StorageTask uploadTask;
    StorageReference storageReference;

    ImageView close, image_added;
    TextView post;
    EditText description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        Objects.requireNonNull(getSupportActionBar()).hide();

        close= findViewById(R.id.close);
        image_added= findViewById(R.id.image_added);
        post= findViewById(R.id.post);
        description= findViewById(R.id.description);


        storageReference= FirebaseStorage.getInstance().getReference("posts");
    }
}