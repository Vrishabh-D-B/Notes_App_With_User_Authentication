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
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Forgotpassword extends AppCompatActivity {

    private TextInputEditText mforgotpassword;
    private Button mrecoverbutton;
    private TextView mgobacktologin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        Objects.requireNonNull(getSupportActionBar()).hide();

        mforgotpassword = findViewById(R.id.forgotpassword);
        mrecoverbutton = findViewById(R.id.recoverbutton);
        mgobacktologin = findViewById(R.id.gobacktologin);
        firebaseAuth = FirebaseAuth.getInstance();

        mgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forgotpassword.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mrecoverbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = Objects.requireNonNull(mforgotpassword.getText()).toString().trim();

                if(mail.isEmpty()){

                    Toast.makeText(getApplicationContext(), "Enter your mail first", Toast.LENGTH_LONG).show();

                } else {

                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Recovery Email Sent", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(Forgotpassword.this, MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Email is Wrong or Account doesn't exist", Toast.LENGTH_LONG).show();
                            }


                        }
                    });

                }

            }
        });


    }
}