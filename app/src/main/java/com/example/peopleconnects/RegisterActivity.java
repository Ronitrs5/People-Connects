package com.example.peopleconnects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    EditText email, name1, username,password;
    ProgressBar progressBar;
    FirebaseAuth auth;
    TextView textView;
    Button button;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email= findViewById(R.id.registeremail);
        name1= findViewById(R.id.registername);
        username= findViewById(R.id.registerusername);
        password= findViewById(R.id.registerpassword);
        progressBar= findViewById(R.id.registerprogressbar);
        textView= findViewById(R.id.registerhaveanaccount);
        button= findViewById(R.id.registerbutton);

        auth= FirebaseAuth.getInstance();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email=email.getEditableText().toString().trim();
                String Password= password.getText().toString();
                String Name= name1.getText().toString();
                String UserName= username.getText().toString();

                if (TextUtils.isEmpty(Email)){
                    email.setError("Cannot be empty");
                    return;
                }

                if (TextUtils.isEmpty(Name)){
                    name1.setError("Cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(UserName)){
                    username.setError("Cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(Password)||Password.length()<=8){
                    password.setError("Must be 8 digits or more");

                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    button.setVisibility(View.INVISIBLE);
                    register(Email, Password);
                }
            }
        });

    }
    private void register(String email1, String s){
        auth.createUserWithEmailAndPassword(email1,s)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser firebaseUser= auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid= firebaseUser.getUid();

                            reference= FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
                            HashMap<String, Object> hashMap= new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username.getText().toString().toLowerCase());
                            hashMap.put("name", name1.getText().toString());
                            hashMap.put("bio", "");
                            hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/people-connects.appspot.com/o/placeholder.png?alt=media&token=10f966b7-0baf-44d0-88c7-32b3adccb2b9");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        progressBar.setVisibility(View.VISIBLE);
                                        button.setVisibility(View.INVISIBLE);
                                        Intent intent= new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        } else {
                            progressBar.setVisibility(View.GONE);
                            button.setVisibility(View.VISIBLE);
                            Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}