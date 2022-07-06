package com.vrishabh.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Signup extends AppCompatActivity {

    private TextInputEditText msignupemail, msignuppassword;
    private Button msignup;
    private TextView mgotologin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        Objects.requireNonNull(getSupportActionBar()).hide();

        msignupemail = findViewById(R.id.signupemail);
        msignuppassword = findViewById(R.id.signuppassword);
        msignup = findViewById(R.id.signup);
        mgotologin = findViewById(R.id.gotologin);
        firebaseAuth = FirebaseAuth.getInstance();


        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
            }
        });

        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = Objects.requireNonNull(msignupemail.getText()).toString().trim();
                String pass = Objects.requireNonNull(msignuppassword.getText()).toString().trim();

                if(mail.isEmpty() || pass.isEmpty()){

                    Toast.makeText(getApplicationContext(), "All fields are requited", Toast.LENGTH_LONG).show();

                } else if(pass.length() < 7) {

                    Toast.makeText(getApplicationContext(), "Password length must be greater than 7", Toast.LENGTH_LONG).show();

                } else {

                    firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();
                                sendEmailVerification();
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration FAILED", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }



            }
        });



    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "Verification email sent, Verify and Login again", Toast.LENGTH_LONG).show();
                    firebaseAuth.signOut();;
                    finish();
                    startActivity(new Intent(Signup.this,MainActivity.class));
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "Failed to send Verification Email", Toast.LENGTH_LONG).show();
        }
    }
}