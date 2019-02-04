package com.bullseye.bidding.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bullseye.bidding.MainActivity;
import com.bullseye.bidding.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;

public class s_signup extends AppCompatActivity implements View.OnClickListener {
    EditText email1;
    EditText pass1;
    FirebaseAuth mAuth;
    ProgressBar prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_signup);
        mAuth=FirebaseAuth.getInstance();

        email1=(EditText)findViewById(R.id.email1);
        pass1=(EditText)findViewById(R.id.pass1);
        findViewById(R.id.signup1).setOnClickListener(this);
        findViewById(R.id.login1).setOnClickListener(this);
        prog=(ProgressBar)findViewById(R.id.prog);

    }

    private void registeruser() {
        prog.setVisibility(View.VISIBLE);
        String ema=email1.getText().toString().trim();
        String pas=pass1.getText().toString().trim();
        if(ema.isEmpty())
        {
            email1.setError("Email is required...");
            email1.requestFocus();
            return;   //returning to calling funtion
        }
        if(pas.isEmpty())
        {    prog.setVisibility(View.INVISIBLE);
            pass1.setError("Password is required...");
            pass1.requestFocus();
            return;   //returing to calling functoin
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(ema).matches())
        {
            prog.setVisibility(View.INVISIBLE);
            email1.setError("Enter a vadit email address");
            email1.requestFocus();
            return;
        }
        if(pass1.length()<6)
        {  prog.setVisibility(View.INVISIBLE);
            pass1.setError("Mini. required password length is 6");
            pass1.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(ema,pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                prog.setVisibility(View.INVISIBLE);
                if(task.isSuccessful())
                {
                    Toast.makeText(s_signup.this, "User register successfull", Toast.LENGTH_SHORT).show();

                }
                else{
                    if(task.getException() instanceof FirebaseAuthEmailException){
                        Toast.makeText(s_signup.this, "You are already register1", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"You are already register2",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signup1:
                registeruser();
                break;
            case R.id.login1:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
    }
}
